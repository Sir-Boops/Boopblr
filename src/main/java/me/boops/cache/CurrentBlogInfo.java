package me.boops.cache;

import org.json.JSONArray;
import org.json.JSONObject;

import me.boops.base.GetURLOAuth;
import me.boops.config.Config;

public class CurrentBlogInfo {

	public static String blogTitle;
	public static int queueCount;

	public CurrentBlogInfo() throws Exception {
		
		String rawAns = new GetURLOAuth().connect("https://api.tumblr.com/v2/user/info", null, null);
		
		// Parse the rawAns
		JSONObject json = new JSONObject(rawAns);
		
		JSONArray arr = json.getJSONObject("response").getJSONObject("user").getJSONArray("blogs");
		
		for( int i = 0; i < arr.length(); i++) {
			
			if(arr.getJSONObject(i).getString("name").equalsIgnoreCase(Config.blogName)) {
				// Get All the blog info
				CurrentBlogInfo.blogTitle = arr.getJSONObject(i).getString("title");
				CurrentBlogInfo.queueCount = arr.getJSONObject(i).getInt("queue");
			}
		}
	}
}
