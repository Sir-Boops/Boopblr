package me.boops.functions;

import me.boops.base.PostURLOAuth;

public class LikeAPost {
	
	public LikeAPost(String reblogKey, long postID) throws Exception {
		String[] titles = {"id", "reblog_key"};
		String[] args = {String.valueOf(postID), reblogKey};
		new PostURLOAuth().connect("https://api.tumblr.com/v2/user/like", titles, args);
	}
}
