package me.boops;

import java.util.List;
import java.util.Random;

import org.json.JSONObject;

import me.boops.cache.Cache;
import me.boops.config.Config;
import me.boops.functions.AppendTags;
import me.boops.functions.CheckBlackTags;
import me.boops.functions.CheckForPastReblog;
import me.boops.functions.CheckPostType;
import me.boops.functions.CheckWhiteTags;
import me.boops.functions.GetAllTags;
import me.boops.functions.GetRandomPost;
import me.boops.functions.QueueHash;
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
		Random rand = new Random();
		Logger log = new Logger();
		
		// Get a post to check
		GetRandomPost postList = new GetRandomPost();
		List<JSONObject> posts = postList.getPosts();
		
		// Save the post in it's raw JSON form
		JSONObject rawPost = posts.get(rand.nextInt(posts.size()));
		
		// Decode the post
		DecodePost post = new DecodePost();
		post.decode(rawPost.toString());
		
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
			return;
		}
		
		// Check if we should check the queue
		// Check if post is already in queue
		if(config.getCheckQueue()) {
			if(Cache.hashList.contains(new QueueHash().hashPost(rawPost))) {
				log.Log("Post already in queue", false);
				return;
			} else {
				log.Log("Post is not yet in queue", false);
			}
		}
		
		// Check to see if we should ...
		// Check to see if we've already reblogged this once
		
		if(config.getBlogName().equalsIgnoreCase(post.getBlogName())) {
			log.Log("You reblogged this", false);
			return;
		}
		
		if(config.getScanNotes()) {	
			new GetAllNotes(post.getBlogName(), post.getPostID());
			CheckForPastReblog reblog = new CheckForPastReblog();
			if(reblog.scan(Cache.reblogUsers)) {
				log.Log("You reblogged this", false);
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
					System.exit(0);
				} else {
					log.Log("Not enough white tags", false);
					return;
				}
			} else {
				log.Log("Found a blacklisted tag", false);
				return;
			}
		}
	}
}
