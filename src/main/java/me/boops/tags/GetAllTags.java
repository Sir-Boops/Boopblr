package me.boops.tags;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import me.boops.cache.Config;
import me.boops.functions.api.APIGetPost;
import me.boops.functions.nonapi.GetAllNotes;

public class GetAllTags {
	
	private boolean error;
	private List<String> post_tags;
	
	public boolean ifError(){
		return this.error;
	}
	
	public List<String> getTags(){
		return this.post_tags;
	}
	
	public void getAllTags(String blog_name, long post_id) throws Exception {
		
		//Define needed classes
		Config Conf = new Config();
		GetAllNotes getAllNotes = new GetAllNotes();
		
		//Get all the notes
		getAllNotes.Get(post_id, blog_name);
		
		//Update The List For This Check
		if(getAllNotes.ifError()){
			error = true;
			return;
		}

		int runs = 0;
		int tagged_users = 0;
		List<String> post_tags = new ArrayList<String>();

		while (runs < getAllNotes.getPublicNames().size() && tagged_users < Conf.getMinTags()) {

			// Get Tags
			APIGetPost GetPost = new APIGetPost(getAllNotes.GetPublicIDs().get(runs), getAllNotes.getPublicNames().get(runs));
			
			//Get Tags
			JSONArray tags = GetPost.getPost().getJSONArray("posts").getJSONObject(0).getJSONArray("tags");
			
			if(tags.toString() != ""){
				
				//We Have Tags To Add!
				int sub_runs = 0;
				while(sub_runs < tags.length()){
					
					//Add To The Cache List
					post_tags.add(tags.getString(sub_runs));
					sub_runs++;
				}
				
				//For Max Amount Of Tags
				tagged_users++;
			}
			
			//No Tags/Already Added For This Post
			runs++;
		}
		
		this.post_tags = post_tags;
	}
}
