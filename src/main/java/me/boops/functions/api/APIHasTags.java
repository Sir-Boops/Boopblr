package me.boops.functions.api;

import me.boops.cache.Cache;

public class APIHasTags {

	public boolean hasTags = false;

	public APIHasTags(long id, String blog_name) throws Exception {

		new APIGetPost(id, blog_name);

		if (!Cache.get_post_post.getJSONArray("posts").getJSONObject(0).isNull("tags")) {
			if (Cache.get_post_post.getJSONArray("posts").getJSONObject(0).getJSONArray("tags").length() > 0) {
				hasTags = true;
			} else {
				hasTags = false;
			}
		} else {
			hasTags = false;
		}
	}
}
