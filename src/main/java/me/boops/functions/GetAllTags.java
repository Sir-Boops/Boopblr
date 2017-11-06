package me.boops.functions;

import me.boops.cache.Cache;
import me.boops.config.Config;
import me.boops.jumblr.BlogPosts;
import me.boops.jumblr.DecodePost;

public class GetAllTags {
	
	public GetAllTags() throws Exception {
		
		// Load the config
		Config config = new Config();
		
		ThreadGroup checkGroup = new ThreadGroup("checkGroup");
		boolean done = false;
		int checked = 0;
		
		while(!done) {
			if(checked < Cache.reblogUsers.size()) {
				
				int numToStart = checked;
				
				// Make sure we are only running 10 threads
				if(checkGroup.activeCount() < 30) {
					checked++;
					// Start a new thread
					new Thread(checkGroup, new Runnable() {
						public void run() {
							
							BlogPosts post = new BlogPosts(config.getCustomerKey(), config.getCustomerSecret(), config.getToken(), config.getTokenSecret());
							post.setPostID(Cache.reblogIDs.get(numToStart));
							post.getPosts(Cache.reblogUsers.get(numToStart));
							
							DecodePost decodePost = new DecodePost();
							decodePost.decode(post.getPost(0));
							
							System.out.println("Found tags " +  decodePost.getPostTags() + " From user " + Cache.reblogUsers.get(numToStart));
							
							new AppendTags(decodePost.getPostTags());
							return;
						}
					}).start();
					
				}
				
			} else {
				while(checkGroup.activeCount() > 0) {
					Thread.sleep(1 * 1000);
				}
				done = true;
			}
		}
	}
	
}
