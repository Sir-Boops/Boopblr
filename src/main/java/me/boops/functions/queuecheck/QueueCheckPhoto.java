package me.boops.functions.queuecheck;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.json.JSONArray;
import org.json.JSONObject;

import me.boops.cache.Cache;
import me.boops.cache.Config;
import me.boops.functions.api.APIGetPost;
import me.boops.functions.api.APIQueueCount;
import me.boops.functions.api.APIQueueGet;
import me.boops.logger.Logger;

public class QueueCheckPhoto {

	public boolean found;
	OAuthConsumer consumer = new CommonsHttpOAuthConsumer(Config.customer_key,
			Config.customer_secret);

	public QueueCheckPhoto(Long id, String blog_name) throws Exception {

		// Setup Client
		consumer.setTokenWithSecret(Config.token, Config.token_secret);

		// Scan The Posts In Queue
		int scanned = 0;

		// Get The Post In Question And Define The Postdata String
		new APIGetPost(id, blog_name);

		// Setup The Request
		String url = (Cache.get_post_post.getJSONArray("posts")
				.getJSONObject(0).getJSONArray("photos").getJSONObject(0)
				.getJSONObject("original_size").getString("url"));

		String post_hash = url.split("/")[3];
		System.out.println(post_hash);

		while (new APIQueueCount().queue_count > scanned && !found) {

			// Get The Posts To Scan
			JSONArray posts = new APIQueueGet(scanned, 20).posts;

			// Scan The Posts
			int sub_runs = 0;
			while (sub_runs < posts.length()) {

				// Check If Post Type Is Photo
				if (((JSONObject) posts.get(sub_runs)).get("type").equals(
						"photo")) {

					// Calcucate The Post Hash And CHeck it!
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
										.getString("url").split("/")[3], 0);
						found = true;
					} else {

						// Nope Keep Chacking
						new Logger(post_hash
								+ " : "
								+ posts.getJSONObject(sub_runs)
										.getJSONArray("photos")
										.getJSONObject(0)
										.getJSONObject("original_size")
										.getString("url").split("/")[3], 0);
						sub_runs++;
					}
				}

				// Non Photo Post So Skip In This Check
				sub_runs++;
			}
			scanned = (scanned + posts.length());
		}
	}
}
