package me.boops.tags;

import java.util.Collections;

import me.boops.cache.Cache;
import me.boops.cache.Config;

public class CopyTags {

	public CopyTags() {

		// Define All Needed
		
		// Count all the tags and add them to a list
		for(int runs=0; Cache.post_tags.size()>runs; runs++){
			Cache.tag_usage.put(Cache.post_tags.get(runs).toLowerCase(), Collections.frequency(Cache.post_tags, Cache.post_tags.get(runs)));
			
			
			//If it's not already in the list add it
			if(!Cache.tag_list.contains(Cache.post_tags.get(runs).toLowerCase())){
				
				Cache.tag_list.add(Cache.post_tags.get(runs).toLowerCase());
				
			}
		}
		
		//Order the tags by usage
		int runs = 0;
		int tag_size = Cache.tag_list.size();
		
		while(tag_size > runs && Config.add_tags_depth > runs){
			
			new FindTop();
			runs++;
		}
		
		//Add user defined tags if they are defined else just remove the final , and be done!
		if(!Config.user_tags.isEmpty()){
			Cache.gend_tags += Config.user_tags;
		} else {
			Cache.gend_tags = Cache.gend_tags.substring(0, (Cache.gend_tags.length() - 1));
		}
		
		System.out.println(Cache.gend_tags);
	}
}
