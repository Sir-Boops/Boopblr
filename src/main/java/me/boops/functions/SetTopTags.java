package me.boops.functions;

import me.boops.cache.Cache;

public class SetTopTags {
	
	public SetTopTags() {
		
		for(int i = 0; i < 3; i++) {
			
			int currentHighest = -1;
			int currentIndex = -1;
			
			for(int i2 = 0; i2 < Cache.tagList.size(); i2++) {
				if(Cache.tagCount.get(i2) > currentHighest) {
					currentHighest = Cache.tagCount.get(i2);
					currentIndex = i2;
				}
			}
			
			if(currentIndex >= 0) {
				Cache.tagToAppend.add(Cache.tagList.get(currentIndex));
				Cache.tagList.remove(currentIndex);
				Cache.tagCount.remove(currentIndex);
			}
		}
	}
}
