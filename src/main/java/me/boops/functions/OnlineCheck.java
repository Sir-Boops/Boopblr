package me.boops.functions;

import org.json.JSONObject;

import me.boops.cache.Config;
import me.boops.cache.Cache;
import me.boops.functions.api.APIGetBlog;

public class OnlineCheck {
	
	public static boolean isOnline;
	
	public OnlineCheck() throws Exception{
		
		//Send A Request To Tumblr
		JSONObject blog_info = new APIGetBlog(Config.blog_name).blog;
		
		//Check If I Can Reach Tumblr
		if(blog_info != null){
			
			//We Got Tumblr
			Cache.name_fancy = ((JSONObject) blog_info.get("blog")).get("title").toString();
			isOnline = true;
		}
	}

}
