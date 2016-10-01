package me.boops.functions;

import java.util.Arrays;

import me.boops.cache.Cache;
import me.boops.cache.Config;
import me.boops.functions.api.APIGetRandPost;
import me.boops.functions.api.APIQueuePost;
import me.boops.logger.Logger;
import me.boops.tags.BlackTags;
import me.boops.tags.CopyTags;
import me.boops.tags.GetAllTags;
import me.boops.tags.UserTags;
import me.boops.tags.WhiteTags;

public class FindPost {

	public FindPost() throws Exception {
		
		//Try And Find A New Post
		new APIGetRandPost();
		
		//Check To Make Sure We Got A Post
		if(Cache.rand_post_id != null){
			
			// Make Sure It Is An Allowed Post Type
			if (!Arrays.asList(Config.post_types).contains(Cache.rand_post_type)) {
				new Logger("Not Allowed To Post This Type", 0, false);
				return;
			}

			// Check To See If It's A Post From Us
			if (Cache.rand_post_user.toLowerCase().equals(Config.blog_name)) {
				new Logger("Dam Found My Own Post", 0, false);
				return;
			}

			// Check If We Should Check Black Tags
			if (Config.check_tags) {
				// Now Check For BlackListed Tags
				if (new BlackTags().Check(Cache.rand_post_tags, Config.blacklisted_tags)) {
					new Logger("Found A BlackListed Tag", 0, false);
					return;
				}
				// Check If We Should Spider Black Tags
				if (Config.spider_tags) {
					//Try and Get All Tags Now
					if(new GetAllTags(Cache.rand_post_user, Cache.rand_post_id).error){
						new Logger("Note Error", 2, true);
						return;
					}
					//Now Check All The Notes
					if (new BlackTags().Check(Cache.post_tags, Config.blacklisted_tags)) {
						new Logger("Found A BlackListed Tag", 0, false);
						return;
					}
				}

				new Logger("Could Not Find Any Black Listed Tags!", 0, false);
			}

			// Check If We Should Check White Tags
			if (Config.check_tags && Config.force_tags) {
				// Now Check For WhiteListed Tags
				if (!new WhiteTags().Check(Cache.rand_post_tags)) {
					if (Config.spider_tags) {
						if (!new WhiteTags().Check(Cache.post_tags)) {
							new Logger("Could Not Find WhiteListed Tags", 0, false);
							return;
						}
					}
				}
			}

			// Check To See If we should check the queue
			if (Config.check_queue) {
				if (new QueueCheck(Cache.rand_post_id, Cache.rand_post_user, Cache.rand_post_type).found) {
					new Logger("Dam Post Is Already In Queue", 0, false);
					return;
				}
			}
			
			//Check If We Should Apped More Tags
			if(Config.add_tags){
				new Logger("Adding More Tags!", 0, true);
				new CopyTags();
				new Logger("Added Tags: " + Cache.gend_tags, 0, false);
			} else {
				new UserTags().add(Cache.gend_tags);
			}
			
			// Since All THe Above Have Passed We Can Now Post It!
			new Logger("Queuing " + Cache.rand_post_url + " From: " + Cache.rand_post_user, 0, false);
			new APIQueuePost(Cache.rand_post_id, Cache.rand_post_key);
			//Done This Loop!
			return;
		} else {
			new Logger("Error Getting Random Post", 2, true);
		}
		return;
	}
}
