package me.boops.functions;

import org.json.JSONObject;

import me.boops.base.GetURLKey;
import me.boops.cache.Cache;
import me.boops.config.Config;

public class PostFromGlobal {
	
	public JSONObject post() throws Exception {
		
		// Define the post
		JSONObject postRaw = new JSONObject();
		
		if((Cache.lastTimeStamp == 0) || (Cache.lastPost > Config.lengthOfPage)) {
			String[] titles = {"tag"};
			String[] args = {"furry"};
			postRaw = new JSONObject(new GetURLKey().connect("https://api.tumblr.com/v2/tagged", titles, args));
		} else {
			String[] titles = {"tag", "before"};
			String[] args = {"furry", String.valueOf(Cache.lastTimeStamp)};
			postRaw = new JSONObject(new GetURLKey().connect("https://api.tumblr.com/v2/tagged", titles, args));
		}
		
		JSONObject post = postRaw.getJSONArray("response").getJSONObject(0);
		Cache.lastPost++;
		Cache.lastTimeStamp = post.getLong("timestamp");
		
		return post;
		
	}
	
}
