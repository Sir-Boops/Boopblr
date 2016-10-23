package me.boops.functions;

import java.util.ArrayList;
import java.util.List;

import me.boops.cache.Config;
import me.boops.crypto.MD5Sum;
import me.boops.logger.Logger;
import pw.frgl.jumblr.BlogInfo;
import pw.frgl.jumblr.BlogQueue;
import pw.frgl.jumblr.DecodePost;

public class UpdateQueueCache {
	
	public List<String> getQueueCache(){
		
		//Define needed classes
		Logger logger = new Logger();
		Config Conf = new Config();
		BlogInfo blog = new BlogInfo(Conf.getCustomerKey(), Conf.getCustomerSecret(), Conf.getToken(), Conf.getTokenSecret());
		BlogQueue queue = new BlogQueue(Conf.getCustomerKey(), Conf.getCustomerSecret(), Conf.getToken(), Conf.getTokenSecret());
		DecodePost decode = new DecodePost();
		
		//Get Current Queue Count
		blog.getBlog(Conf.getBlogName());
		
		List<String> QueueSums = new ArrayList<String>();
		
		//List the post
		for(int i=0; blog.getQueueCount()>i;){
			
			// Get The Posts To Scan
			queue.setOffset(i);
			queue.getQueue(Conf.getBlogName());
			
			//Scan The Post Itself
			for(int i2=0; queue.getResPostCount()>i2; i2++){
				
				//Decode the post
				decode.decode(queue.getPost(i2));
				
				//What to hash if it's an answer
				if(decode.getPostType().equals("answer")){
					QueueSums.add(new MD5Sum().hash(decode.getReblogComment()));
					logger.Log("Hashed a post: " + new MD5Sum().hash(decode.getReblogComment()), 0, false);
				}
				
				//If Audio Post
				if(decode.getPostType().equals("audio")){
					QueueSums.add(new MD5Sum().hash(decode.getAudioURL()));
					logger.Log("Hashed a post: " + new MD5Sum().hash(decode.getAudioURL()), 0, false);
				}
				
				//If Chat post
				if(decode.getPostType().equals("chat")){
					QueueSums.add(new MD5Sum().hash(decode.getChatBody()));
					logger.Log("Hashed a post: " + new MD5Sum().hash(decode.getChatBody()), 0, false);
				}
				
				//If Link Post
				if(decode.getPostType().equals("link")){
					QueueSums.add(new MD5Sum().hash(decode.getPostLinkURL()));
					logger.Log("Hashed a post: " + new MD5Sum().hash(decode.getPostLinkURL()), 0, false);
				}
				
				//If Photo Post
				if(decode.getPostType().equals("photo")){
					//Check if URL has a hash
					if(decode.getOrginalPhotoURL().toLowerCase().contains("tumblr")){
						QueueSums.add(new MD5Sum().hash(decode.getOrginalPhotoURL().split("/")[3]));
						logger.Log("Hashed a post: " + new MD5Sum().hash(decode.getOrginalPhotoURL().split("/")[3]), 0, false);
					} else {
						QueueSums.add(new MD5Sum().hash(decode.getOrginalPhotoURL()));
						logger.Log("Hashed a post: " + new MD5Sum().hash(decode.getOrginalPhotoURL()), 0, false);
					}
				}
				
				//If Quote Post
				if(decode.getPostType().equals("quote")){
					QueueSums.add(new MD5Sum().hash(decode.getPostQuoteText()));
					logger.Log("Hashed a post: " + new MD5Sum().hash(decode.getPostQuoteText()), 0, false);
				}
				
				//If text post
				if(decode.getPostType().equals("text")){
					QueueSums.add(new MD5Sum().hash(decode.getPostTextBody()));
					logger.Log("Hashed a post: " + new MD5Sum().hash(decode.getPostTextBody()), 0, false);
				}
				
				//if video post
				if(decode.getPostType().equals("video")){
					QueueSums.add(new MD5Sum().hash(decode.getReblogComment()));
					logger.Log("Hashed a post: " + new MD5Sum().hash(decode.getReblogComment()), 0, false);
				}
				
			}
			
			i = (i + queue.getResPostCount());
		}
		
		return QueueSums;
		
	}
}
