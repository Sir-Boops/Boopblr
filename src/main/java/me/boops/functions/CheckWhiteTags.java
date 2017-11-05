package me.boops.functions;

import me.boops.cache.Cache;
import me.boops.config.Config;

public class CheckWhiteTags {
	
	public boolean check() {
		Config config = new Config();
		
		int whiteTagCount = 0;
		
		for(int i = 0; i < Cache.tags.size(); i++) {
			
			// Check said tags
			for(int i2 = 0; i2 < config.getWhitelistedTags().size(); i2++) {
				if(Cache.tags.get(i).toLowerCase().contains(config.getWhitelistedTags().get(i2).toLowerCase())) {
					whiteTagCount++;
				}
			}
		}
		
		double ans = (whiteTagCount / Cache.tags.size());
		
		System.out.println(ans);
		System.exit(0);
		return false;
	}
}
