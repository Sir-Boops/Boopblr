package me.boops;

import java.util.List;

import org.json.JSONObject;

import me.boops.cache.Cache;
import me.boops.config.Config;
import me.boops.functions.AppendTags;
import me.boops.functions.CheckBlackTags;
import me.boops.functions.CheckForPastReblog;
import me.boops.functions.CheckPostType;
import me.boops.functions.CheckWhiteTags;
import me.boops.functions.FindPopularTags;
import me.boops.functions.GetAllTags;
import me.boops.functions.GetRandomPost;
import me.boops.functions.LikeThePost;
import me.boops.functions.QueueHash;
import me.boops.functions.ReblogPost;
import me.boops.jumblr.DecodePost;
import me.boops.logger.Logger;
import me.boops.nonapi.functions.GetAllNotes;

public class FindNewPost {
	
	public FindNewPost() throws Exception {
		
		// Clean the cache
		Cache.reblogUsers.clear();
		Cache.reblogIDs.clear();
		Cache.tags.clear();
		
		// Load the config and random
		// And the logger
		Config config = new Config();
		Logger log = new Logger();
		
		// Check the bad post cache age
		if(Cache.badPostIDsAge == 0) {
			// First run!
			Cache.badPostIDsAge = (System.currentTimeMillis() / 1000);
		}
		
		if((Cache.badPostIDsAge + 3600) <= (System.currentTimeMillis() / 1000)) {
			// Clean the cache!
			Cache.badPostIDs.clear();
			Cache.badPostIDsAge = (System.currentTimeMillis() / 1000);
		}
		
		// Get a post to check
		GetRandomPost postList = new GetRandomPost();
		List<JSONObject> posts = postList.getPosts();
		
		// Save the post in it's raw JSON form
		JSONObject rawPost = posts.get(0);
		
		// Decode the post
		DecodePost post = new DecodePost();
		post.decode(rawPost.toString());
		
		// Check if the postID is in the bad cache
		for(int i = 0; i < Cache.badPostIDs.size(); i++) {
			if(Cache.badPostIDs.get(i).equals(post.getPostID())) {
				log.Log("Known bad post", false);
				return;
			}
		}
		
		// Add this posts tags to the tag list
		// If it has any
		new AppendTags(post.getPostTags());
		
		System.out.println(post.getShortSourceURL());
		
		// Check if we should see if we should
		// Check to see if the post is liked
		// Then check to see if we've liked the post
		if(config.getCheckLikes()) {
			log.Log("Checking if post is liked", false);
			if(post.isPostLiked()) {
				log.Log("Already liked the post, Trying for a new one", false);
				Cache.badPostIDs.add(post.getPostID());
				return;
			} else {
				log.Log("Post has not been liked", false);
			}
		}
		
		// Check to make sure it's a proper
		// post type
		if(new CheckPostType().check(post.getPostType())) {
			log.Log("Proper Post type!", false);
		} else {
			log.Log("Incorrect post type", false);
			Cache.badPostIDs.add(post.getPostID());
			return;
		}
		
		// Check if we should check the queue
		// Check if post is already in queue
		if(config.getCheckQueue()) {
			if(Cache.hashList.contains(new QueueHash().hashPost(rawPost))) {
				log.Log("Post already in queue", false);
				Cache.badPostIDs.add(post.getPostID());
				return;
			} else {
				log.Log("Post is not yet in queue", false);
			}
		}
		
		// Check to see if we should ...
		// Check to see if we've already reblogged this once
		
		if(config.getBlogName().equalsIgnoreCase(post.getBlogName())) {
			log.Log("You reblogged this", false);
			Cache.badPostIDs.add(post.getPostID());
			return;
		}
		
		if(config.getScanNotes()) {	
			new GetAllNotes(post.getBlogName(), post.getPostID());
			CheckForPastReblog reblog = new CheckForPastReblog();
			if(reblog.scan(Cache.reblogUsers)) {
				log.Log("You reblogged this", false);
				Cache.badPostIDs.add(post.getPostID());
				return;
			} else {
				log.Log("You have not yet reblogged this", false);
			}
		}
		
		// See if we should spider the tags
		if(config.getCheckTags() && config.getSpiderTags()) {
			new GetAllTags();
			if(Cache.tags.size() < config.getMinTags()) {
				log.Log("Not enough tags", false);
				Cache.badPostIDs.add(post.getPostID());
				return;
			}
			System.out.println(Cache.tags);
		}
		
		// Check to see if we should check tags
		// Then check the blacklisted tags
		if(config.getCheckTags()) {
			if(!new CheckBlackTags().check()) {
				// We are good
				// Now check whitelist
				if(new CheckWhiteTags().check()) {
					// Found a post to queue!
					System.out.println(post.getShortSourceURL());
				} else {
					log.Log("Not enough white tags", false);
					Cache.badPostIDs.add(post.getPostID());
					return;
				}
			} else {
				log.Log("Found a blacklisted tag", false);
				Cache.badPostIDs.add(post.getPostID());
				return;
			}
		}
		
		// Get the tags to use
		if(config.getAddTags()) {
			new FindPopularTags();
		}
		
		// Like the post if we should
		if(config.getAutoLike()) {
			new LikeThePost(post.getPostID(), post.getReblogKey());
		}
		
		// Reblog the post
		new ReblogPost(post.getPostID(), post.getReblogKey());
	}
}
