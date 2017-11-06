package me.boops.functions;

import me.boops.cache.Cache;
import me.boops.config.Config;
import me.boops.jumblr.BlogReblogPost;
import me.boops.logger.Logger;

public class ReblogPost {
	public ReblogPost(long ID, String Key) {
		Config config = new Config();
		Logger log = new Logger();
		BlogReblogPost reblog = new BlogReblogPost(config.getCustomerKey(), config.getCustomerSecret(), config.getToken(), config.getTokenSecret());
		String tags = "";
		for (int i = 0; i < Cache.tagToAppend.size(); i++) {
			if(tags.isEmpty()) {
				tags += (Cache.tagToAppend.get(i) + ",");
			} else {
				if((i + 1) == Cache.tagToAppend.size()) {
					tags += Cache.tagToAppend.get(i);
				} else {
					tags += (Cache.tagToAppend.get(i) + ",");
				}
			}
		}
		reblog.setPostTags(tags);
		reblog.Reblog(ID, Key, config.getBlogName());
		
		if(reblog.getHTTPCode() == 201) {
			log.Log("Queued the post", false);
		} else {
			log.Log("Error queuing the post", false);
		}
	}
}
