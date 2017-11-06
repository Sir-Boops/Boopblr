package me.boops.functions;

import me.boops.config.Config;
import me.boops.jumblr.BlogLike;
import me.boops.logger.Logger;

public class LikeThePost {
	public LikeThePost(long ID, String key) {
		Config config = new Config();
		Logger log = new Logger();
		BlogLike like = new BlogLike(config.getCustomerKey(), config.getCustomerSecret(), config.getToken(), config.getTokenSecret());
		like.likePost(ID, key);
		if(like.getHTTPCode() == 200) {
			log.Log("Liked the post", false);
		} else {
			log.Log("Error liking post", false);
		}
	}
}
