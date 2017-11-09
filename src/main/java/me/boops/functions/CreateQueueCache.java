package me.boops.functions;

import org.json.JSONArray;
import org.json.JSONObject;

import me.boops.base.GetURLOAuth;
import me.boops.cache.Cache;
import me.boops.cache.CurrentBlogInfo;
import me.boops.config.Config;

public class CreateQueueCache {

	public CreateQueueCache() throws Exception {
		
		JSONArray queueList = new JSONArray();
		
		int found = 0;
		while( found < CurrentBlogInfo.queueCount) {
			
			String[] titles = {"offset"};
			String[] args = {String.valueOf(found)};
			
			String meta = new GetURLOAuth().connect("https://api.tumblr.com/v2/blog/" + Config.blogName + "/posts/queue", titles, args);
			
			JSONArray posts = new JSONObject(meta).getJSONObject("response").getJSONArray("posts");
			for(int i = 0; i < posts.length(); i++) {
				queueList.put(posts.get(i));
			}
			found = (found + posts.length());
		}
		
		
		for(int i = 0; i < queueList.length(); i++) {
			Cache.hashList.add(new HashAPost().post(queueList.getJSONObject(i)));
		}
	}
}
