package me.boops.functions;

import me.boops.cache.Cache;

public class GetTopTags {

	public GetTopTags() {

		for (int i = 0; i < Cache.allowedTags.size(); i++) {

			if (!Cache.tagList.contains(Cache.allowedTags.get(i).toLowerCase())) {
				Cache.tagList.add(Cache.allowedTags.get(i).toLowerCase());
				Cache.tagCount.add(1);
			} else {
				Cache.tagCount.set(Cache.tagList.indexOf(Cache.allowedTags.get(i).toLowerCase()),
						(Cache.tagCount.get(Cache.tagList.indexOf(Cache.allowedTags.get(i).toLowerCase())) + 1));
			}
		}
	}
}
