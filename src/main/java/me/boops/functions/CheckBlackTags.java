package me.boops.functions;

import me.boops.cache.Cache;
import me.boops.config.Config;

public class CheckBlackTags {
	
	public boolean check() {
		
		Config config = new Config();
		
		for(int i = 0; i < Cache.tags.size(); i++) {
			
			// Check said tags
			for(int i2 = 0; i2 < config.getBlacklistedTags().size(); i2++) {
				if(Cache.tags.get(i).toLowerCase().contains(config.getBlacklistedTags().get(i2).toLowerCase())) {
					return true;
				}
			}
		}
		return false;
	}
}
