package me.boops.functions;

import me.boops.cache.Cache;
import me.boops.cache.Config;
import me.boops.functions.api.APIGetRandPost;
import me.boops.logger.Logger;
import me.boops.tags.BlackTags;
import me.boops.tags.CopyTags;
import me.boops.tags.GetAllTags;
import me.boops.tags.WhiteTags;

public class FindPost {

	public FindPost() {

		// Define needed classes
		Config Conf = new Config();
		Logger logger = new Logger();
		APIGetRandPost RandPost = new APIGetRandPost();
		GetAllTags getAllTags = new GetAllTags();
		CopyTags TagCopy = new CopyTags();
		QueueCheck Queue = new QueueCheck();

		// Check To Make Sure We Got A Post
		if (RandPost.getID() > 0) {
			if (!Cache.BadPostIDs.contains(RandPost.getID())) {

				// Make Sure It Is An Allowed Post Type
				if (!Conf.getPostTypes().contains(RandPost.getPostType())) {
					logger.Log("Not Allowed To Post This Type", 0, false);
					Cache.BadPostIDs.add(RandPost.getID());
					return;
				}

				// Check To See If It's A Post From Us
				if (RandPost.getBlogName().toLowerCase().equals(Conf.getBlogName())) {
					logger.Log("Dam Found My Own Post", 0, false);
					Cache.BadPostIDs.add(RandPost.getID());
					return;
				}

				// Check To See If we should check the queue
				if (Conf.getCheckQueue()) {
					Queue.Check(RandPost.getID(), RandPost.getBlogName());
					if (Queue.isInQueue()) {
						logger.Log("Dam Post Is Already In Queue", 0, false);
						return;
					}
					logger.Log("Post was not found in queue", 0, false);
				}

				// Check If We Should Check Black Tags
				if (Conf.getCheckTags()) {
					// Now Check For BlackListed Tags
					if (new BlackTags().Check(RandPost.getTags(), Conf.getBlacklistedTags())) {
						logger.Log("Found A BlackListed Tag", 0, false);
						Cache.BadPostIDs.add(RandPost.getID());
						return;
					}
					// Check If We Should Spider Black Tags
					if (Conf.getSpiderTags()) {
						// Try and Get All Tags Now
						getAllTags.getAllTags(RandPost.getBlogName(), RandPost.getID());
						if (getAllTags.ifError()) {
							logger.Log("Note Error", 2, true);
							Cache.BadPostIDs.add(RandPost.getID());
							return;
						}
						// Now Check All The Notes
						if (new BlackTags().Check(getAllTags.getTags(), Conf.getBlacklistedTags())) {
							logger.Log("Found A BlackListed Tag", 0, false);
							Cache.BadPostIDs.add(RandPost.getID());
							return;
						}
					}

					logger.Log("Could Not Find Any Black Listed Tags!", 0, false);
				}

				// Check If We Should Check White Tags
				if (Conf.getCheckTags() && Conf.getForceTags()) {
					// Now Check For WhiteListed Tags
					if (!new WhiteTags().Check(RandPost.getTags())) {
						if (Conf.getSpiderTags()) {
							if (!new WhiteTags().Check(getAllTags.getTags())) {
								logger.Log("Could Not Find WhiteListed Tags", 0, false);
								Cache.BadPostIDs.add(RandPost.getID());
								return;
							}
						}
					}
				}

				// Check If We Should Apped More Tags
				if (Conf.getAddTags()) {
					logger.Log("Adding More Tags!", 0, true);
					TagCopy.Copy(getAllTags.getTags());
					logger.Log("Added Tags: " + TagCopy.getTags(), 0, false);
				} else {

				}

				// Since All THe Above Have Passed We Can Now Post It!
				logger.Log("Queuing " + RandPost.getShortURL() + " From: " + RandPost.getBlogName(), 0, false);
				new QueuePost(RandPost.getID(), RandPost.getReblogKey(), TagCopy.getTags(), "queue");
				Cache.QueueHashes.add(Cache.CurrentPostHash);
				// Done This Loop!
				return;
			} else {
				logger.Log("Post is a known bad one trying again!", 0, false);
			}
		} else {
			logger.Log("Error Getting Random Post", 2, true);
		}
		return;
	}
}
