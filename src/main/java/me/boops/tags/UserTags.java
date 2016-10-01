package me.boops.tags;

import me.boops.cache.Cache;
import me.boops.cache.Config;

public class UserTags {
	
	public void add(String tags){
		
		for(int runs=0; Config.user_tags.size()>runs; runs++){
			if((runs + 1) < Config.user_tags.size()){
				Cache.gend_tags += (Config.user_tags.get(runs).replace("[", "").replace("]", "") + ",");
			} else {
				Cache.gend_tags += (Config.user_tags.get(runs).replace("[", "").replace("]", ""));
			}
		}
		
	}
}
