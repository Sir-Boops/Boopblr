package me.boops.tags;

import me.boops.cache.Cache;
import me.boops.cache.Config;

public class UserTags {
	
	public void add(String tags) throws Exception{
		
		//Define needed classes
		Config Conf = new Config();
		
		for(int runs=0; Conf.getUserTags().size()>runs; runs++){
			if((runs + 1) < Conf.getUserTags().size()){
				Cache.gend_tags += (Conf.getUserTags().get(runs).replace("[", "").replace("]", "") + ",");
			} else {
				Cache.gend_tags += (Conf.getUserTags().get(runs).replace("[", "").replace("]", ""));
			}
		}
		
	}
}
