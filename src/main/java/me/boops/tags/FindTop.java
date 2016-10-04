package me.boops.tags;

import java.util.ArrayList;
import java.util.List;

import me.boops.cache.Cache;
import me.boops.cache.Config;

public class FindTop {
	
	FindTop() throws Exception{
		
		//Define needed classes
		Config Conf = new Config();
		
		//Set Needed Ints
		int top = 0;
		int top_id = 0;
		
		//Roll though and find the largest number
		for(int runs=0; Cache.tag_list.size()>runs; runs++){
			
			if(Cache.tag_usage.get(Cache.tag_list.get(runs)).intValue() > top){
				top = Cache.tag_usage.get(Cache.tag_list.get(runs));
				top_id = runs;
			}
		}
		
		// Check if the tags to add are blacklisted
		List<String> temp_list = new ArrayList<String>();
		temp_list.clear();
		temp_list.add(Cache.tag_list.get(top_id));
		if(!new BlackTags().Check(temp_list, Conf.getBlacklistedTags())){
			
			// Tag is not in the blacklist!
			// So add it
			Cache.gend_tags += (Cache.tag_list.get(top_id) + ",");
		}
		
		//Now remove the new top tag from the list
		Cache.tag_usage.remove(Cache.tag_list.get(top_id));
		Cache.tag_list.remove(top_id);
	}
}
