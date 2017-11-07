package me.boops.functions;

import me.boops.cache.Cache;
import me.boops.config.Config;

public class WhiteListCheck {

	public boolean Check() {

		int foundTags = 0;

		for (int i = 0; i < Cache.tags.size(); i++) {
			for (int i2 = 0; i2 < Config.whitelistedTags.size(); i2++) {
				if (Cache.tags.get(i).contains(" ")) {
					// Run again for the space split
					for (int i3 = 0; i3 < Cache.tags.get(i).split(" ").length; i3++) {
						for (int i4 = 0; i4 < Config.whitelistedTags.size(); i4++) {
							if (Config.whitelistedTags.get(i4).equalsIgnoreCase(Cache.tags.get(i).split(" ")[i3])) {
								foundTags++;
							}
						}
					}
				} else {
					if (Config.whitelistedTags.get(i2).equalsIgnoreCase(Cache.tags.get(i))) {
						foundTags++;
					}
				}
			}
		}
		
		float percent = (Float.valueOf(foundTags) / Float.valueOf(Cache.tags.size())) * 100;
		
		if(Math.round(percent) >= Config.minWhitelist) {
			return true;
		} else {
			return false;
		}
	}
}
