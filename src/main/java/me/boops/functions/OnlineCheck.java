package me.boops.functions;

import me.boops.cache.Cache;
import me.boops.config.Config;
import me.boops.jumblr.BlogInfo;
import me.boops.logger.Logger;

public class OnlineCheck {
	
	public void amOnline() {
		
		Config config = new Config();
		
		try {
			
			BlogInfo info = new BlogInfo(config.getCustomerKey(), config.getCustomerSecret(), config.getToken(), config.getTokenSecret());
			info.getBlog(config.getBlogName());
			
			new Logger().Log("Hello, " + info.getTitle(), false);
			
			// Set blog URL
			Cache.blogURL = info.getBlogURL().split("/")[2];
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
	}
}
