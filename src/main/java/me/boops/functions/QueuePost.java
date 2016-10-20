package me.boops.functions;

import me.boops.cache.Config;
import me.boops.logger.Logger;
import pw.frgl.jumblr.BlogReblogPost;

public class QueuePost {

	public QueuePost(long id, String key, String tags, String state) throws Exception {
		
		//Define needed classes
		Config Conf = new Config();
		BlogReblogPost post = new BlogReblogPost(Conf.getCustomerKey(), Conf.getCustomerSecret(), Conf.getToken(), Conf.getTokenSecret());
		
		//Set post settings
		post.setPostState(state);
		post.setPostTags(tags);
		post.Reblog(id, key, Conf.getBlogName());
		
		if(post.getHTTPCode() == 201){
			new Logger().Log("Post was posted", 0, false);
		} else {
			new Logger().Log("Error posting post", 0, false);
		}
	}
}
