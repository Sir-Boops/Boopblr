package me.boops.functions;

import java.util.List;

import me.boops.cache.Config;
import me.boops.crypto.MD5Sum;
import me.boops.logger.Logger;
import pw.frgl.jumblr.BlogPosts;
import pw.frgl.jumblr.DecodePost;

public class QueueCheck {

	// If Post Is Found
	public boolean found = false;

	public QueueCheck(long id, String blog_name) {
		
		//Define needed classes
		Logger logger = new Logger();
		Config Conf = new Config();
		BlogPosts post = new BlogPosts(Conf.getCustomerKey(), Conf.getCustomerSecret(), Conf.getToken(), Conf.getTokenSecret());
		DecodePost decode = new DecodePost();

		//Get And Decode the post
		post.setPostID(id);
		post.getPosts(blog_name);
		decode.decode(post.getPost(0));
		
		List<String> QueueSumsList = new UpdateQueueCache().getQueueCache();
		
		for(int i=0; QueueSumsList.size()>i; i++){
			
			//What to hash if it's an answer
			if(decode.getPostType().equals("answer")){
				if(new MD5Sum().hash(decode.getReblogComment()).equals(QueueSumsList.get(i))){
					this.found = true;
				}
				logger.Log("Scanning post: " + new MD5Sum().hash(decode.getReblogComment()) + ":" + QueueSumsList.get(i), 0, false);
			}
			
			//If Audio Post
			if(decode.getPostType().equals("audio")){
				if(new MD5Sum().hash(decode.getAudioURL()).equals(QueueSumsList.get(i))){
					this.found = true;
				}
				logger.Log("Scanning post: " + new MD5Sum().hash(decode.getAudioURL()) + ":" + QueueSumsList.get(i), 0, false);
			}
			
			//If Chat post
			if(decode.getPostType().equals("chat")){
				if(new MD5Sum().hash(decode.getChatBody()).equals(QueueSumsList.get(i))){
					this.found = true;
				}
				logger.Log("Scanning post: " + new MD5Sum().hash(decode.getChatBody()) + ":" + QueueSumsList.get(i), 0, false);
			}
			
			//If Link Post
			if(decode.getPostType().equals("link")){
				if(new MD5Sum().hash(decode.getPostLinkURL()).equals(QueueSumsList.get(i))){
					this.found = true;
				}
				logger.Log("Scanning post: " + new MD5Sum().hash(decode.getPostLinkURL()) + ":" + QueueSumsList.get(i), 0, false);
			}
			
			//If Photo Post
			if(decode.getPostType().equals("photo")){
				//Check if URL has a hash
				if(decode.getOrginalPhotoURL().toLowerCase().contains("tumblr")){
					if(new MD5Sum().hash(decode.getOrginalPhotoURL().split("/")[3]).equals(QueueSumsList.get(i))){
						this.found = true;
					}
					logger.Log("Scanning post: " + new MD5Sum().hash(decode.getOrginalPhotoURL().split("/")[3]) + ":" + QueueSumsList.get(i), 0, false);
				} else {
					if(new MD5Sum().hash(decode.getOrginalPhotoURL()).equals(QueueSumsList.get(i))){
						this.found = true;
					}
					logger.Log("Scanning post: " + new MD5Sum().hash(decode.getOrginalPhotoURL()) + ":" + QueueSumsList.get(i), 0, false);
				}
			}
			
			//If Quote Post
			if(decode.getPostType().equals("quote")){
				if(new MD5Sum().hash(decode.getPostQuoteText()).equals(QueueSumsList.get(i))){
					this.found = true;
				}
				logger.Log("Scanning post: " + new MD5Sum().hash(decode.getPostQuoteText()) + ":" + QueueSumsList.get(i), 0, false);
			}
			
			//If text post
			if(decode.getPostType().equals("text")){
				if(new MD5Sum().hash(decode.getPostTextBody()).equals(QueueSumsList.get(i))){
					this.found = true;
				}
				logger.Log("Scanning post: " + new MD5Sum().hash(decode.getPostTextBody()) + ":" + QueueSumsList.get(i), 0, false);
			}
			
			//if video post
			if(decode.getPostType().equals("video")){
				if(new MD5Sum().hash(decode.getReblogComment()).equals(QueueSumsList.get(i))){
					this.found = true;
				}
				logger.Log("Scanning post: " + new MD5Sum().hash(decode.getReblogComment()) + ":" + QueueSumsList.get(i), 0, false);
			}
			
		}
	}
}
