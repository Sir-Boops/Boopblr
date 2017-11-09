package me.boops.functions;

import org.json.JSONObject;

import me.boops.base.GetURLOAuth;
import me.boops.cache.Cache;
import me.boops.config.Config;

public class PostFromDash {
	
	public JSONObject post() throws Exception {
		
		if(Cache.lastDashPost >= Config.lengthOfPage) {
			Cache.lastDashPost = 0;
		}
		
		// Setup the args
		String[] titles = {"limit", "offset"};
		String[] args = {"1", String.valueOf(Cache.lastDashPost)};
		
		Cache.lastDashPost++;
		Cache.dashOrGlobal = true;
		String rawPost = new GetURLOAuth().connect("https://api.tumblr.com/v2/user/dashboard", titles, args);
		return new JSONObject(rawPost).getJSONObject("response").getJSONArray("posts").getJSONObject(0);
	}
}
