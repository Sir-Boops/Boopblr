package me.boops.functions;

import me.boops.base.PostURLOAuth;
import me.boops.cache.Cache;
import me.boops.config.Config;

public class QueueAPost {
	
	public QueueAPost(String reblogKey, long postID) throws Exception {
		
		if(Cache.tagToAppend.size() > 0) {
			
			String tags = "";
			
			for(int i = 0; i < Cache.tagToAppend.size(); i++) {
				if(i == 0) {
					tags += Cache.tagToAppend.get(i);
				} else {
					tags += "," + Cache.tagToAppend.get(i);
				}
			}
			
			String[] titles = {"state", "id", "reblog_key", "tags"};
			String[] args = {"queue", String.valueOf(postID), reblogKey, tags};
			
			new PostURLOAuth().connect("https://api.tumblr.com/v2/blog/" + Config.blogName + "/post/reblog", titles, args);
			
		} else {
			
			String[] titles = {"state", "id", "reblog_key"};
			String[] args = {"queue", String.valueOf(postID), reblogKey};
			
			new PostURLOAuth().connect("https://api.tumblr.com/v2/blog/" + Config.blogName + "/post/reblog", titles, args);
			
		}
		
	}
}
