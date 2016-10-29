package me.boops.functions;

import me.boops.cache.Cache;
import me.boops.cache.Config;
import me.boops.crypto.MD5Sum;
import me.boops.logger.Logger;
import pw.frgl.jumblr.BlogPosts;
import pw.frgl.jumblr.DecodePost;

public class QueueCheck {

	// If Post Is Found
	private boolean found;
	
	public boolean isInQueue(){
		return this.found;
	}

	public void Check(long id, String blog_name) {
		
		//Define needed classes
		Logger logger = new Logger();
		Config Conf = new Config();
		BlogPosts post = new BlogPosts(Conf.getCustomerKey(), Conf.getCustomerSecret(), Conf.getToken(), Conf.getTokenSecret());
		DecodePost decode = new DecodePost();
		new Cache();

		//Get And Decode the post
		post.setPostID(id);
		post.getPosts(blog_name);
		decode.decode(post.getPost(0));
		
		for(int i=0; Cache.QueueHashes.size()>i; i++){
			
			//What to hash if it's an answer
			if(decode.getPostType().equals("answer")){
				if(new MD5Sum().hash(decode.getReblogComment()).equals(Cache.QueueHashes.get(i))){
					this.found = true;
				}
				logger.Log("Scanning post: " + new MD5Sum().hash(decode.getReblogComment()) + ":" + Cache.QueueHashes.get(i), 0, false);
				Cache.CurrentPostHash = new MD5Sum().hash(decode.getReblogComment());
			}
			
			//If Audio Post
			if(decode.getPostType().equals("audio")){
				if(new MD5Sum().hash(decode.getAudioURL()).equals(Cache.QueueHashes.get(i))){
					this.found = true;
				}
				logger.Log("Scanning post: " + new MD5Sum().hash(decode.getAudioURL()) + ":" + Cache.QueueHashes.get(i), 0, false);
				Cache.CurrentPostHash = new MD5Sum().hash(decode.getAudioURL());
			}
			
			//If Chat post
			if(decode.getPostType().equals("chat")){
				if(new MD5Sum().hash(decode.getChatBody()).equals(Cache.QueueHashes.get(i))){
					this.found = true;
				}
				logger.Log("Scanning post: " + new MD5Sum().hash(decode.getChatBody()) + ":" + Cache.QueueHashes.get(i), 0, false);
				Cache.CurrentPostHash = new MD5Sum().hash(decode.getChatBody());
			}
			
			//If Link Post
			if(decode.getPostType().equals("link")){
				if(new MD5Sum().hash(decode.getPostLinkURL()).equals(Cache.QueueHashes.get(i))){
					this.found = true;
				}
				logger.Log("Scanning post: " + new MD5Sum().hash(decode.getPostLinkURL()) + ":" + Cache.QueueHashes.get(i), 0, false);
				Cache.CurrentPostHash = new MD5Sum().hash(decode.getPostLinkURL());
			}
			
			//If Photo Post
			if(decode.getPostType().equals("photo")){
				//Check if URL has a hash
				if(decode.getOrginalPhotoURL().toLowerCase().contains("tumblr")){
					if(Cache.QueueHashes.get(i).equalsIgnoreCase(decode.getOrginalPhotoURL().split("/")[3])){
						this.found = true;
						return;
					}
					logger.Log("Scanning post: " + decode.getOrginalPhotoURL().split("/")[3] + " : " + Cache.QueueHashes.get(i), 0, false);
					Cache.CurrentPostHash = decode.getOrginalPhotoURL().split("/")[3];
				} else {
					this.found = true;
					return;
				}
			}
			
			//If Quote Post
			if(decode.getPostType().equals("quote")){
				if(new MD5Sum().hash(decode.getPostQuoteText()).equals(Cache.QueueHashes.get(i))){
					this.found = true;
				}
				logger.Log("Scanning post: " + new MD5Sum().hash(decode.getPostQuoteText()) + ":" + Cache.QueueHashes.get(i), 0, false);
				Cache.CurrentPostHash = new MD5Sum().hash(decode.getPostQuoteText());
			}
			
			//If text post
			if(decode.getPostType().equals("text")){
				if(new MD5Sum().hash(decode.getPostTextBody()).equals(Cache.QueueHashes.get(i))){
					this.found = true;
				}
				logger.Log("Scanning post: " + new MD5Sum().hash(decode.getPostTextBody()) + ":" + Cache.QueueHashes.get(i), 0, false);
				Cache.CurrentPostHash = new MD5Sum().hash(decode.getPostTextBody());
			}
			
			//if video post
			if(decode.getPostType().equals("video")){
				if(new MD5Sum().hash(decode.getReblogComment()).equals(Cache.QueueHashes.get(i))){
					this.found = true;
				}
				logger.Log("Scanning post: " + new MD5Sum().hash(decode.getReblogComment()) + ":" + Cache.QueueHashes.get(i), 0, false);
				Cache.CurrentPostHash = new MD5Sum().hash(decode.getReblogComment());
			}
			
		}
		
	}
}
