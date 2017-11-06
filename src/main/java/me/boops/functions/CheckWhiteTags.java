package me.boops.functions;

import me.boops.cache.Cache;
import me.boops.config.Config;

public class CheckWhiteTags {
	
	public boolean check() {
		Config config = new Config();
		
		int whiteTagCount = 0;
		
		for(int i = 0; i < Cache.tags.size(); i++) {
			
			// Check if it has spaces in it
			if(Cache.tags.get(i).contains(" ")) {
				// Split and run again
				String[] splitTag = Cache.tags.get(i).split(" ");
				for(int i2 = 0; i2 < splitTag.length; i2++) {
					for(int i3 = 0; i3 < config.getWhitelistedTags().size(); i3++) {
						if(splitTag[i2].equalsIgnoreCase(config.getWhitelistedTags().get(i3))) {
							whiteTagCount++;
						}
					}
				}
			} else {
				// Just run normally
				for(int i2 = 0; i2 < config.getWhitelistedTags().size(); i2++) {
					if(Cache.tags.get(i).equalsIgnoreCase(config.getWhitelistedTags().get(i2))) {
						whiteTagCount++;
					}
				}
			}
		}
		
		double ans = ((whiteTagCount / Cache.tags.size()) * 100);
		
		if(whiteTagCount > 0) {
			System.out.println(ans);
			System.exit(0);
		}
		return false;
	}
}
