package me.boops.tags;

import java.util.List;
import java.util.Map;

public class FindTop {
	
	private int top_id;
	
	public int getTopID(){
		return this.top_id;
	}
	
	public void Search(Map <String, Integer> tag_usage, List<String> tag_list) {
		
		//Set Needed Ints
		int top = 0;
		int top_id = 0;
		
		//Roll though and find the largest number
		for(int runs=0; tag_list.size()>runs; runs++){
			
			if(tag_usage.get(tag_list.get(runs)).intValue() > top){
				top = tag_usage.get(tag_list.get(runs));
				top_id = runs;
			}
		}
		
		this.top_id = top_id;
	}
}
