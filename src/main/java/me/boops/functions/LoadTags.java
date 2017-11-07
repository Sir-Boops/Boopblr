package me.boops.functions;

import org.json.JSONObject;

import me.boops.base.GetURLKey;
import me.boops.cache.Cache;
import me.boops.config.Config;

public class LoadTags {
	
	
	public LoadTags() throws Exception {
		
		ThreadGroup loadGroup = new ThreadGroup("loadGroup");
		int checkedCount = 0;
		
		while((Cache.tags.size() < Config.minTags) && (checkedCount < Cache.reblogIDs.size())) {
			
			if(loadGroup.activeCount() < 10) {
				int numToStart = checkedCount;
				checkedCount++;
				new Thread(loadGroup, new Runnable() {
					public void run() {
						try {
							String[] titles = {"id"};
							String[] args = {String.valueOf(Cache.reblogIDs.get(numToStart))};
							System.out.println("Getting tags from " + Cache.reblogUsers.get(numToStart));
							JSONObject postRaw = new JSONObject(new GetURLKey().connect("https://api.tumblr.com/v2/blog/" + Cache.reblogUsers.get(numToStart) + "/posts", titles, args));
							JSONObject post = postRaw.getJSONObject("response").getJSONArray("posts").getJSONObject(0);
							
							// Check for tags
							if(post.getJSONArray("tags").length() > 0) {
								for(int i = 0; i < post.getJSONArray("tags").length(); i++) {
									Cache.tags.add(post.getJSONArray("tags").getString(i));
								}
							}
							return;
						} catch (Exception e) {
						} 
					}
				}).start();
			} else {
				Thread.sleep(1 * 1000);
			}
		}
		while(loadGroup.activeCount() > 0) {
			Thread.sleep(1 * 1000);
		}
	}
}
