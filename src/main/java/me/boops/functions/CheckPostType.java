package me.boops.functions;

import me.boops.config.Config;

public class CheckPostType {
	
	public boolean check(String postType) {
		
		Config config = new Config();
		
		for(int i = 0; i < config.getPostTypes().size(); i++) {
			if(postType.equalsIgnoreCase(config.getPostTypes().get(i))) {
				return true;
			}
		}
		return false;
	}
}
