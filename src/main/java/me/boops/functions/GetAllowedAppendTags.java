package me.boops.functions;

import me.boops.cache.Cache;
import me.boops.config.Config;

public class GetAllowedAppendTags {

	public GetAllowedAppendTags() {

		for(int i = 0; i < Cache.tags.size(); i++) {
			boolean ans = false;
			for(int i2 = 0; i2 < Config.tagAddBlacklist.size(); i2++) {
				if(Cache.tags.get(i).contains(" ")) {
					// Run again for the space split
					for(int i3 = 0; i3 < Cache.tags.get(i).split(" ").length; i3++) {
						for(int i4 = 0; i4 < Config.tagAddBlacklist.size(); i4++) {
							if(Config.tagAddBlacklist.get(i4).equalsIgnoreCase(Cache.tags.get(i).split(" ")[i3])) {
								ans = true;
							}
						}
					}
				} else {
					if(Config.tagAddBlacklist.get(i2).equalsIgnoreCase(Cache.tags.get(i))) {
						ans = true;
					}
				}
			}
			if(!ans) {
				Cache.allowedTags.add(Cache.tags.get(i));
			}
		}
	}
}
