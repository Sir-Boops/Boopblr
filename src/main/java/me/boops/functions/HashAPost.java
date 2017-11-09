package me.boops.functions;

import org.json.JSONObject;

import me.boops.crypto.MD5Sum;

public class HashAPost {
	
	public String post(JSONObject post) {
		
		String ans = "";
		
		// if Photo
		if(post.getString("type").equals("photo")) {
			ans = new MD5Sum().hash(post.getJSONArray("photos").getJSONObject(0).getJSONObject("original_size").getString("url"));
		}
		
		// If Video
		if(post.getString("type").equals("video")) {
			ans = new MD5Sum().hash(post.getString("source_url"));
		}
		
		return ans;
		
	}
	
}
