package me.boops;

import org.json.JSONObject;

import me.boops.cache.Cache;
import me.boops.config.Config;
import me.boops.functions.BlackListCheck;
import me.boops.functions.LoadNotes;
import me.boops.functions.LoadTags;
import me.boops.functions.PostFromDash;

public class FindNewPost {

	public FindNewPost() throws Exception {
		
		System.out.println("===============================================================");
		System.out.println("Searching for new post");

		// The post
		JSONObject post = new JSONObject();

		// Try and find a new post
		// Check to see if we should use the dash
		// Or just pull based off a tag
		if (Config.useDash) {
			// Get the post using the dash
			post = new PostFromDash().post();
		} else {
			// Using global tags!
		}
		
		System.out.println(post.getString("post_url"));

		// Check the post ID to see if the post
		// Is known to be a bad post
		for (int i = 0; i < Cache.badPostIDs.size(); i++) {
			if (Cache.badPostIDs.get(i).equals(post.getLong("id"))) {
				// Found a known bad post!
				System.out.println("Found a known bad post!");
				return;
			}
		}

		// Check if we can like/reblog the post
		if (!post.getBoolean("can_reblog") || !post.getBoolean("can_like")) {
			System.out.println("Cannot like or reblog this post!");
			Cache.badPostIDs.add(post.getLong("id"));
			return;
		}

		// Check to make sure it's a proper post type
		for (int i = 0; i < Config.postTypes.size(); i++) {
			if (!post.getString("type").equals(Config.postTypes.get(i))) {
				if ((i + 1) >= Config.postTypes.size()) {
					System.out.println("Not an allowed post type!");
					Cache.badPostIDs.add(post.getLong("id"));
					return;
				}
			}
		}
		
		// We've made it this far now we need to get notes before
		// moving on anymore
		new LoadNotes(post.getString("blog_name"), post.getLong("id"));
		
		// Check if we've liked the post
		for(int i = 0; i < Cache.likeUsers.size(); i++) {
			if(Config.blogName.equalsIgnoreCase(Cache.likeUsers.get(i))) {
				System.out.println("Already liked this post!");
				Cache.badPostIDs.add(post.getLong("id"));
				return;
			}
		}
		
		// Check if we've reblogged the post
		for(int i = 0; i < Cache.reblogUsers.size(); i++) {
			if(Config.blogName.equalsIgnoreCase(Cache.reblogUsers.get(i))) {
				System.out.println("Already reblogged this post!");
				Cache.badPostIDs.add(post.getLong("id"));
				return;
			}
		}
		
		// Before going beyond this point we need to
		// Grab all the tags on the post
		new LoadTags();
		
		// Make sure we have enough tags
		if(Cache.tags.size() < Config.minTags) {
			System.out.println("Not enough tags!");
			Cache.badPostIDs.add(post.getLong("id"));
			return;
		}
		
		// Check for blacklisted tags
		if(new BlackListCheck().Check()) {
			System.out.println("Found a blacklisted tag!");
			System.out.println(Cache.tags);
			System.exit(0);
			Cache.badPostIDs.add(post.getLong("id"));
			return;
		}
		
		System.out.println(Cache.tags);
		System.out.println(Cache.tags.size());
		System.exit(0);
		
	}
}
