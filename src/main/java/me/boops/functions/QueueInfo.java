package me.boops.functions;

import me.boops.config.Config;
import me.boops.jumblr.BlogInfo;

public class QueueInfo {
	
	public int getQueueCount() {
		
		Config config = new Config();
		
		BlogInfo info = new BlogInfo(config.getCustomerKey(), config.getCustomerSecret(), config.getToken(), config.getTokenSecret());
		return info.getQueueCount();
		
	}
	
}
