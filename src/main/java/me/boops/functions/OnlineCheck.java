package me.boops.functions;

import me.boops.cache.Config;
import me.boops.jumblr.BlogInfo;

public class OnlineCheck {
	
	private String getFancyName;
	private boolean isOnline;
	
	public String getFancyName(){
		return this.getFancyName;
	}
	
	public boolean isOnline(){
		return this.isOnline;
	}
	
	public void Check() {
		
		//Define needed classes
		Config Conf = new Config();
		BlogInfo blog = new BlogInfo(Conf.getCustomerKey(), Conf.getCustomerSecret(), Conf.getToken(), Conf.getTokenSecret());
		
		//Get Blog Info
		blog.getBlog(Conf.getBlogName());
		
		//Check HTTP code
		if(blog.getHTTPCode() == 200){
			//We Got Tumblr
			this.getFancyName = blog.getTitle();
			this.isOnline = true;
		} else {
			this.isOnline = false;
		}
	}

}
