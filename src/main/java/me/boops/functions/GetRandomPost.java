package me.boops.functions;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import me.boops.cache.Cache;
import me.boops.config.Config;
import me.boops.jumblr.BlogDash;

public class GetRandomPost {
	
	private List<JSONObject> postList = new ArrayList<JSONObject>();
	
	public List<JSONObject> getPosts(){
		
		// Load the config
		Config config = new Config();
		
		System.out.println(Cache.lastPost);
		postList.clear();
		
		if(Cache.lastPost >= config.getDashLength()) {
			Cache.lastPost = 0;
		}
		
		// Get some posts from the dash
		BlogDash dash = new BlogDash(config.getCustomerKey(), config.getCustomerSecret(), config.getToken(), config.getTokenSecret());
		dash.setRequestOffset(Cache.lastPost);
		dash.setRequestLimit(1);
		dash.getPosts();
		postList.add(dash.getTumblrPosts().getJSONObject(0));
		
		Cache.lastPost++;
		return this.postList;
		
	}
}
