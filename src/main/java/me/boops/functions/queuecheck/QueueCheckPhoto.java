package me.boops.functions.queuecheck;

import org.json.JSONArray;

import me.boops.cache.Cache;
import me.boops.cache.Config;
import me.boops.functions.api.APIGetPost;
import me.boops.functions.api.APIQueueCount;
import me.boops.functions.api.APIQueueGet;
import me.boops.logger.Logger;

public class QueueCheckPhoto {

	public boolean found;

	public QueueCheckPhoto(Long id, String blog_name) throws Exception {

		// Scan The Posts In Queue
		int scanned = 0;

		// Get The Post In Question And Define The Postdata String
		new APIGetPost(id, blog_name);

		// Setup The Request
		String url = (Cache.get_post_post.getJSONArray("posts")
				.getJSONObject(0).getJSONArray("photos").getJSONObject(0)
				.getJSONObject("original_size").getString("url"));

		// Get The Inital Post Hash
		String post_hash = url.split("/")[3];

		// Check If Post Sum Is A Sum
		if (post_hash.toLowerCase().contains("tumblr") && Config.force_sum) {
			new Logger("Post Sum Is Not A Sum!", 0, true);
			return;
		}

		while (new APIQueueCount().queue_count > scanned && !found) {

			// Get The Posts To Scan
			JSONArray posts = new APIQueueGet(scanned, 20).posts;

			// Scan The Posts
			int sub_runs = 0;
			while (sub_runs < posts.length()) {

				// Check If Post Type Is Photo
				if (posts.getJSONObject(sub_runs).get("type").equals("photo")) {

					// Check The Post Hash
					if (post_hash.equals(posts.getJSONObject(sub_runs)
							.getJSONArray("photos").getJSONObject(0)
							.getJSONObject("original_size").getString("url")
							.split("/")[3])
							&& !found) {

						// Found A Duplicate!
						new Logger(post_hash
								+ " : "
								+ posts.getJSONObject(sub_runs)
										.getJSONArray("photos")
										.getJSONObject(0)
										.getJSONObject("original_size")
										.getString("url").split("/")[3], 0, true);
						found = true;
						return;
					} else {

						// Nope Keep Chacking
						new Logger(post_hash
								+ " : "
								+ posts.getJSONObject(sub_runs)
										.getJSONArray("photos")
										.getJSONObject(0)
										.getJSONObject("original_size")
										.getString("url").split("/")[3], 0, true);
					}
				}

				sub_runs++;
			}
			scanned = (scanned + posts.length());
		}
	}
}
