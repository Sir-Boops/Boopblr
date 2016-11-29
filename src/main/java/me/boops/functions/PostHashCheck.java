package me.boops.functions;

import me.boops.cache.Config;
import me.boops.jumblr.BlogPosts;
import me.boops.jumblr.DecodePost;
import me.boops.logger.Logger;

public class PostHashCheck {
	
	public boolean Check(String blog_name, long id){
		
		Config Conf = new Config();
		BlogPosts blog = new BlogPosts(Conf.getCustomerKey(), Conf.getCustomerSecret(), Conf.getToken(), Conf.getTokenSecret());
		DecodePost post = new DecodePost();
		Logger logger = new Logger();
		
		blog.setPostID(id);
		
		//Get the post
		blog.getPosts(blog_name);
		
		post.decode(blog.getPost(0));
		
		if(post.getShortSourceURL() != null){
			if(post.getOrginalPhotoURL() != null){
				
				String MD5 = post.getOrginalPhotoURL().split("/")[3];
				
				if(!MD5.toLowerCase().contains("tumblr")){
					
					//We have a sum we can use!
					
					for(int i=0; Conf.getBannedPhotoSums().size()>i; i++){
						logger.Log(MD5 + " : " + Conf.getBannedPhotoSums().get(i), 0, false);
						if(MD5.equals(Conf.getBannedPhotoSums().get(i))){
							return true;
						}
					}
					
				}
			}
		}
		return false;
	}
}
