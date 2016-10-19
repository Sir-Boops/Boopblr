package me.boops.functions;

import org.json.JSONObject;

import me.boops.cache.Config;
import me.boops.functions.api.APIGetBlog;

public class OnlineCheck {
	
	private String getFancyName;
	private boolean isOnline;
	
	public String getFancyName(){
		return this.getFancyName;
	}
	
	public boolean isOnline(){
		return this.isOnline;
	}
	
	public void Check() throws Exception{
		
		//Define needed classes
		Config Conf = new Config();
		
		//Send A Request To Tumblr
		JSONObject blog_info = new APIGetBlog().Blog(Conf.getBlogName());
		
		//Check If I Can Reach Tumblr
		if(blog_info != null){
			
			//We Got Tumblr
			this.getFancyName = blog_info.getJSONObject("blog").getString("title");
			this.isOnline = true;
		} else {
			this.isOnline = false;
		}
	}

}
