package me.boops.tags;

import me.boops.cache.Config;

public class UserTags {
	
	private String tags;
	
	public String getFinalTags(){
		return this.tags;
	}
	
	public void add(String tags) {
		
		//Define needed classes
		Config Conf = new Config();
		String final_tags = tags;
		
		for(int runs=0; Conf.getUserTags().size()>runs; runs++){
			if((runs + 1) < Conf.getUserTags().size()){
				final_tags += (Conf.getUserTags().get(runs).replace("[", "").replace("]", "") + ",");
			} else {
				final_tags += (Conf.getUserTags().get(runs).replace("[", "").replace("]", ""));
			}
		}
		
		this.tags = final_tags;
		
	}
}
