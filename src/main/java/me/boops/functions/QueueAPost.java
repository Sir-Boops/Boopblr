package me.boops.functions;

import me.boops.base.PostURLOAuth;
import me.boops.config.Config;

public class QueueAPost {
	
	public QueueAPost(String reblogKey, long postID) throws Exception {
		
		String[] titles = {"state", "id", "reblog_key"};
		String[] args = {"queue", String.valueOf(postID), reblogKey};
		
		new PostURLOAuth().connect("https://api.tumblr.com/v2/blog/" + Config.blogName + "/post/reblog", titles, args);
		
	}
}
