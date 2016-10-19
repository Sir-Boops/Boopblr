package me.boops.tags;

import java.util.ArrayList;
import java.util.List;

import me.boops.cache.Config;
import me.boops.functions.nonapi.GetAllNotes;
import me.boops.logger.Logger;
import pw.frgl.jumblr.BlogPosts;
import pw.frgl.jumblr.DecodePost;

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
		DecodePost decode = new DecodePost();
		BlogPosts post = new BlogPosts(Conf.getCustomerKey(), Conf.getCustomerSecret(), Conf.getToken(), Conf.getTokenSecret());
		
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
			
			//Find the post
			post.setPostID(getAllNotes.GetPublicIDs().get(runs));
			post.getPosts(getAllNotes.getPublicNames().get(runs));
			
			new Logger().Log("Getting Tags From: " + getAllNotes.getPublicNames().get(runs), 0, false);
			
			//Check HTTP Code
			if(post.getHTTPCode() == 200){
				
				//Decode the post
				decode.decode(post.getPost(0));
				
				//Check if we have tags
				if(!decode.getPostTags().isEmpty()){
					
					//We Have Tags To Add!
					int sub_runs = 0;
					while(sub_runs < decode.getPostTags().size()){
						
						//Add To The Cache List
						post_tags.add(decode.getPostTags().get(sub_runs));
						sub_runs++;
					}
					
					//For Max Amount Of Tags
					tagged_users++;
				}
				
			}
			
			//No Tags/Already Added For This Post
			runs++;
		}
		
		this.post_tags = post_tags;
		System.out.println(this.post_tags);
	}
}
