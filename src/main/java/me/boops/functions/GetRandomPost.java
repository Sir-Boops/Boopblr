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
		
		if(Cache.lastPost >= config.getDashLength()) {
			Cache.lastPost = 0;
		}
		
		// Get some posts from the dash
		BlogDash dash = new BlogDash(config.getCustomerKey(), config.getCustomerSecret(), config.getToken(), config.getTokenSecret());
		dash.setRequestOffset(Cache.lastPost);
		
		if(config.getPostTypes().size() > 1) {
			
			for(int i=0; i <= config.getPostTypes().size(); i++) {
				
				dash.setRequestPostType(config.getPostTypes().get(i));
				dash.getPosts();
				
				for (int i2=0; i2 < dash.getTumblrPosts().length(); i2++) {
					
					postList.add(dash.getTumblrPosts().getJSONObject(i2));
					
				}
				
			}
			
		} else {
			
			dash.setRequestPostType(config.getPostTypes().get(0));
			dash.getPosts();
			
			for (int i=0; i < dash.getTumblrPosts().length(); i++) {
				
				postList.add(dash.getTumblrPosts().getJSONObject(i));
				
			}
		}
		
		Cache.lastPost++;
		return this.postList;
		
	}
}
