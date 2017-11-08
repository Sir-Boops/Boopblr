package me.boops.functions;

import me.boops.cache.Cache;
import me.boops.config.Config;

public class GetAllowedAppendTags {

	public GetAllowedAppendTags() {

		for(int i = 0; i < Cache.tags.size(); i++) {
			boolean ans = false;
			for(int i2 = 0; i2 < Config.tagAddBlacklist.size(); i2++) {
				if(Cache.tags.get(i).contains(" ")) {
					ans = true;
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
