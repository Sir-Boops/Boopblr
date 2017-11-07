package me.boops.functions;

import me.boops.cache.Cache;
import me.boops.config.Config;

public class FindPopularTags {
	
	public FindPopularTags() {
		
		// Load the config
		Config config = new Config();
		
		// Rank on count
		Cache.tagList.clear();
		Cache.tagCount.clear();
		
		for(int i = 0; i < Cache.tags.size(); i++) {
			
			if(Cache.tagList.get(i).equalsIgnoreCase(Cache.tags.get(i))) {
				Cache.tagCount.set(Cache.tagCount.get(Cache.tagList.indexOf(Cache.tags.get(i))) + 1,
						Cache.tagList.indexOf(Cache.tags.get(i)));
			} else {
				Cache.tagList.add(Cache.tags.get(i));
				Cache.tagCount.add(1);
			}
		}
		
		// Find the top X
		for(int i = 0; i < config.getAddTagsDepth(); i++) {
			
			int highest = 0;
			int indexOf = 0;
			
			// Find the highest tags
			for(int i2 = 0; i2 < Cache.tagList.size(); i2++) {
				if(Cache.tagCount.get(i2) > highest) {
					highest = Cache.tagCount.get(i2);
					indexOf = Cache.tagCount.indexOf(highest);
				}
			}
			
			// Found a new high number
			Cache.tagToAppend.add(Cache.tagList.get(indexOf));
			Cache.tagList.remove(indexOf);
			Cache.tagCount.remove(indexOf);
			
		}
		System.out.println(Cache.tagToAppend);
	}
}
