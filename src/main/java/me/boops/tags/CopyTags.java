package me.boops.tags;

import me.boops.cache.Cache;
import me.boops.cache.Config;

public class CopyTags {

	public CopyTags() {

		// Get Length Of Found Tags
		int tags_length = Cache.post_tags.size();

		// Setup The Loop!
		int runs = 0;
		boolean done = false;
		while (runs <= Config.add_tags_depth && !done) {

			// Setup The Add And Final Add
			if (runs < tags_length && runs < Config.add_tags_depth) {

				// Add With A , On The End
				// Check if a tag is already in
				if (!Cache.gend_tags.toLowerCase().contains(
						Cache.post_tags.get(runs).toLowerCase())) {

					// Now We Add This Tag
					Cache.gend_tags += (Cache.post_tags.get(runs) + ",");

				}

				runs++;

			} else {

				// All That's Left Todo is add the User Defined Tags!
				// If the User Defined Any
				if (!Config.user_tags.isEmpty()) {
					Cache.gend_tags += (Config.user_tags);
					done = true;
				} else {

					Cache.gend_tags = (Cache.gend_tags.substring(0,
							(Cache.gend_tags.length() - 1)));
					done = true;

				}
			}
		}
	}
}
