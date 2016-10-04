package me.boops.tags;

import java.util.Collections;

import me.boops.cache.Cache;
import me.boops.cache.Config;

public class CopyTags {

	public CopyTags() throws Exception {
		
		//Define needed classes
		Config Conf = new Config();

		// Clean Out The Old Tags
		Cache.tag_usage.clear();
		Cache.tag_list.clear();
		Cache.gend_tags = "";
		
		
		// Count all the tags and add them to a list
		for(int runs=0; Cache.post_tags.size()>runs; runs++){
			
			Cache.tag_usage.put(Cache.post_tags.get(runs).toLowerCase(), Collections.frequency(Cache.post_tags, Cache.post_tags.get(runs)));
			
			//If it's not already in the list add it
			if(!Cache.tag_list.contains(Cache.post_tags.get(runs).toLowerCase())){
				
				Cache.tag_list.add(Cache.post_tags.get(runs).toLowerCase());
				
			}
		}
		
		//Order the tags by usage
		int tag_size = Cache.tag_list.size();
		
		for(int runs=0; tag_size > runs && Conf.getAddTagsDepth() > runs; runs++){
			new FindTop();
			
			//Check if a tag was added or not
			if(Cache.gend_tags.length() <= runs){
				runs--;
			}
		}
		
		//Add user defined tags if they are defined else just remove the final ,
		if(!Conf.getUserTags().isEmpty()){
			new UserTags().add(Cache.gend_tags);
		} else {
			Cache.gend_tags = Cache.gend_tags.substring(0, (Cache.gend_tags.length() - 1));
		}
	}
}