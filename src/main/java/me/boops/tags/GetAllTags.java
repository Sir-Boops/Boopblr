package me.boops.tags;

import org.json.JSONArray;

import me.boops.cache.Cache;
import me.boops.cache.Config;
import me.boops.functions.api.APIGetPost;
import me.boops.functions.nonapi.GetAllNotes;

public class GetAllTags {
	
	public boolean error = false;

	public GetAllTags(String blog_name, long post_id) throws Exception {
		
		//Clear The Cache
		Cache.post_tags.clear();
		
		//Update The List For This Check
		if(new GetAllNotes(post_id, blog_name).error){
			error = true;
			return;
		}

		int runs = 0;
		int tagged_users = 0;

		while (runs < Cache.pub_names.size() && tagged_users < Config.min_tags) {

			// Get Tags
			new APIGetPost(Cache.pub_ids.get(runs), Cache.pub_names.get(runs));
			
			//Get Tags
			JSONArray tags = Cache.get_post_post.getJSONArray("posts").getJSONObject(0).getJSONArray("tags");
			
			if(tags.toString() != ""){
				
				//We Have Tags To Add!
				int sub_runs = 0;
				while(sub_runs < tags.length()){
					
					//Add To The Cache List
					Cache.post_tags.add(tags.get(sub_runs).toString());
					sub_runs++;
				}
				
				//For Max Amount Of Tags
				tagged_users++;
			}
			
			//No Tags/Already Added For This Post
			runs++;
		}
	}
}
