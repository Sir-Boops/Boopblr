package me.boops.functions.queuecheck;

import org.json.JSONArray;
import org.json.JSONObject;

import me.boops.cache.Cache;
import me.boops.crypto.MD5Sum;
import me.boops.functions.api.APIGetPost;
import me.boops.functions.api.APIQueueCount;
import me.boops.functions.api.APIQueueGet;
import me.boops.logger.Logger;

public class QueueCheckLink {

	public boolean found;

	public QueueCheckLink(Long id, String blog_name) throws Exception {

		// Scan The Posts In Queue
		int scanned = 0;

		// Get Check Post Hash
		new APIGetPost(id, blog_name);
		String post_hash = new MD5Sum().hash(Cache.get_post_post.getJSONArray("posts").getJSONObject(0).getString("url"));

		while (new APIQueueCount().queue_count > scanned && !found) {

			// Get The Posts To Scan
			JSONArray posts = new APIQueueGet(scanned, 20).posts;

			// Scan The Posts
			int sub_runs = 0;
			while (sub_runs < posts.length()) {

				// Check If Post Type Is Photo
				if (((JSONObject) posts.get(sub_runs)).get("type").equals("link")) {

					// Calcucate The Post Hash And CHeck it!
					if (post_hash.equals(new MD5Sum().hash(posts.getJSONObject(sub_runs).getString("url"))) && !found) {

						// Found A Duplicate!
						found = true;
					} else {

						// Nope Keep Chacking
						new Logger(post_hash + " : " + new MD5Sum().hash(posts.getJSONObject(sub_runs).getString("url")), 0);
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
