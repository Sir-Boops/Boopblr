package me.boops.tags;

import me.boops.cache.Cache;

public class FindTop {
	
	FindTop(){
		
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
		
		//Add the top tag to the list
		Cache.gend_tags += (Cache.tag_list.get(top_id) + ",");
		
		//Now remove the new top tag from the list
		Cache.tag_usage.remove(Cache.tag_list.get(top_id));
		Cache.tag_list.remove(top_id);
	}
}
