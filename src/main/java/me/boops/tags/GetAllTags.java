package me.boops.tags;

import java.util.ArrayList;
import java.util.List;

import me.boops.cache.Config;
import me.boops.functions.nonapi.GetAllNotes;
import me.boops.jumblr.BlogPosts;
import me.boops.jumblr.DecodePost;
import me.boops.logger.Logger;

public class GetAllTags {
	
	private boolean error;
	private List<String> post_tags;
	
	public boolean ifError(){
		return this.error;
	}
	
	public List<String> getTags(){
		return this.post_tags;
	}
	
	public void getAllTags(String blog_name, long post_id) {
		
		//Define needed classes
		Config Conf = new Config();
		GetAllNotes getAllNotes = new GetAllNotes();
		DecodePost decode = new DecodePost();
		BlogPosts post = new BlogPosts(Conf.getCustomerKey(), Conf.getCustomerSecret(), Conf.getToken(), Conf.getTokenSecret());
		
		//Get all the notes
		try {
			getAllNotes.Get(post_id, blog_name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Update The List For This Check
		if(getAllNotes.ifError()){
			error = true;
			return;
		}

		int tagged_users = 0;
		List<String> post_tags = new ArrayList<String>();
		
		for(int i=0; getAllNotes.getPublicNames().size()>i && tagged_users < Conf.getMinTags(); i++){
			
			//Find the post
			post.setPostID(getAllNotes.GetPublicIDs().get(i));
			post.getPosts(getAllNotes.getPublicNames().get(i));
			
			new Logger().Log("Getting Tags From: " + getAllNotes.getPublicNames().get(i), 0, false);
			
			//Check HTTP Code
			if(post.getHTTPCode() == 200){
				
				//Decode the post
				decode.decode(post.getPost(0));
				
				//Check if we have tags
				if(!decode.getPostTags().isEmpty()){
					
					//We Have Tags To Add!
					for(int i2=0; decode.getPostTags().size()>i2; i2++){
						post_tags.add(decode.getPostTags().get(i2));
					}
				}
			}
		}
		
		this.post_tags = post_tags;
		new Logger().Log("Tags Found: " + this.post_tags, 0, false);
	}
}
