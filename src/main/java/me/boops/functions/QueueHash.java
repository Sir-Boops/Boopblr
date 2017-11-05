package me.boops.functions;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import me.boops.cache.Cache;
import me.boops.config.Config;
import me.boops.crypto.MD5Sum;
import me.boops.jumblr.BlogInfo;
import me.boops.jumblr.BlogQueue;
import me.boops.jumblr.DecodePost;
import me.boops.logger.Logger;

public class QueueHash {
	
	public void updateCache() {
		
		// Load the config and logger
		Config config = new Config();
		Logger log = new Logger();
		
		// Temp Cache
		List<JSONObject> queuePosts = new ArrayList<JSONObject>();
		
		// Check the age of the cache
		if((System.currentTimeMillis() / 1000) >= (Cache.hashCacheAge + 3600)) {
			
			log.Log("Rebuilding Queue hash list", false);
			
			// Rebuild the cache!
			BlogQueue queue = new BlogQueue(config.getCustomerKey(), config.getCustomerSecret(), config.getToken(), config.getTokenSecret());
			BlogInfo info = new BlogInfo(config.getCustomerKey(), config.getCustomerSecret(), config.getToken(), config.getTokenSecret());
			info.getBlog(config.getBlogName());
			
			int wi = 0;
			while(wi < info.getQueueCount()) {
				
				queue.setOffset(wi);
				queue.getQueue(config.getBlogName());
				log.Log("Getting posts from queue " + wi + " - " + (queue.getResPostCount() + wi), false);
				
				for (int i2 = 0; i2 < queue.getResPostCount(); i2++) {
					queuePosts.add(new JSONObject(queue.getPost(i2)));
				}
				
				wi = (wi + queue.getResPostCount());
				
			}
			
			// Hash the URLs for the posts
			
			for(int i=0; i < queuePosts.size(); i++) {
				String hash = hashPost(queuePosts.get(i));
				Cache.hashList.add(hash);
				log.Log("Got hash: " + hash, true);
			}
			
			// Finally set the new queue hash time
			Cache.hashCacheAge = (System.currentTimeMillis() / 1000);
			
		}
	}
	
	public String hashPost(JSONObject postSrc) {
		
		// Decode the post
		DecodePost post = new DecodePost();
		post.decode(postSrc.toString());
		
		String ans = null;
		
		if(post.getPostType().equals("photo")) {
			ans = new MD5Sum().hash(post.getOrginalPhotoURL());
		}
		
		if(post.getPostType().equals("video")) {
			ans = new MD5Sum().hash(post.getSourceURL());
		}
		
		if(post.getPostType().equals("text")) {
			ans = new MD5Sum().hash(post.getPostTextBody());
		}
		
		if(post.getPostType().equals("quote")) {
			ans = new MD5Sum().hash(post.getPostQuoteText());
		}
		
		if(post.getPostType().equals("link")) {
			ans = new MD5Sum().hash(post.getPostLinkURL());
		}
		
		if(post.getPostType().equals("chat")) {
			ans = new MD5Sum().hash(post.getPostTextBody());
		}
		
		if(post.getPostType().equals("audio")) {
			ans = new MD5Sum().hash(post.getSourceURL());
		}
		
		if(post.getPostType().equals("answer")) {
			ans = new MD5Sum().hash(post.getShortSourceURL());
		}
		
		return ans;
		
	}
}
