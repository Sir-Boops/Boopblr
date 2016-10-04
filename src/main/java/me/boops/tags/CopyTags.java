package me.boops.tags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.boops.cache.Config;

public class CopyTags {
	
	private String tags;
	
	public String getTags(){
		return this.tags;
	}

	public void Copy(List<String> tags) throws Exception {
		
		//Define needed classes
		Config Conf = new Config();
		FindTop TopTags = new FindTop();

		// Clean Out The Old Tags
		Map <String, Integer> tag_usage = new HashMap<String, Integer>();
		List<String> tag_list = new ArrayList<String>();
		String temp_gen_list = "";
		
		
		// Count all the tags and add them to a list
		for(int runs=0; tags.size()>runs; runs++){
			
			tag_usage.put(tags.get(runs).toLowerCase(), Collections.frequency(tags, tags.get(runs)));
			
			//If it's not already in the list add it
			if(!tag_list.contains(tags.get(runs).toLowerCase())){
				
				tag_list.add(tags.get(runs).toLowerCase());
				
			}
		}
		
		//Order the tags by usage
		int tag_size = tag_list.size();
		List<String> temp_list = new ArrayList<String>();
		
		for(int runs=0; tag_size > runs && Conf.getAddTagsDepth() > runs; runs++){
			TopTags.Search(tag_usage, tag_list);
			
			temp_list.clear();
			temp_list.add(tag_list.get(TopTags.getTopID()));
			
			//Check if the tag it found is on the blacklist
			if(!new BlackTags().Check(temp_list, Conf.getBlacklistedTags())){
				temp_gen_list += (tag_list.get(TopTags.getTopID()) + ",");
			}
		}
		
		//Add user defined tags if they are defined else just remove the final ,
		if(!Conf.getUserTags().isEmpty()){
			UserTags user = new UserTags();
			user.add(temp_gen_list);
			this.tags = user.getFinalTags();
		} else {
			temp_gen_list = temp_gen_list.substring(0, (temp_gen_list.length() - 1));
			this.tags = temp_gen_list;
		}
	}
}
