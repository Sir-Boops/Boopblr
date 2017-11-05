package me.boops.functions;

import java.util.List;

import me.boops.cache.Cache;

public class AppendTags {
	
	public AppendTags(List<String> tags) {
		
		if(tags.size() > 0) {
			
			for(int i = 0; i < tags.size(); i++) {
				Cache.tags.add(tags.get(i));
			}
		}
	}
}
