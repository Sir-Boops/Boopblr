package me.boops.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;

import me.boops.config.Config;
import me.boops.jumblr.BlogDash;

public class GetRandomPost {
	
	private List<JSONObject> postList = new ArrayList<JSONObject>();
	
	public List<JSONObject> getPosts(){
		
		// Load random and config
		Random rand = new Random();
		Config config = new Config();
		
		// Get some posts from the dash
		BlogDash dash = new BlogDash(config.getCustomerKey(), config.getCustomerSecret(), config.getToken(), config.getTokenSecret());
		dash.setRequestOffset(rand.nextInt(config.getDashLength()));
		
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
		
		return this.postList;
		
	}
}
