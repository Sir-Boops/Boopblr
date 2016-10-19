package me.boops.functions.queuecheck;

import org.json.JSONArray;

import me.boops.cache.Config;
import me.boops.functions.api.APIGetPost;
import me.boops.functions.api.APIQueueCount;
import me.boops.functions.api.APIQueueGet;
import me.boops.logger.Logger;

public class QueueCheckPhoto {

	public boolean found;

	public QueueCheckPhoto(Long id, String blog_name) throws Exception {
		
		//Define needed classes
		APIQueueCount APIQueue = new APIQueueCount();
		Config Conf = new Config();
		Logger logger = new Logger();
		
		//Count Posts In Queue
		APIQueue.Count();

		// Scan The Posts In Queue
		int scanned = 0;

		// Get The Post In Question And Define The Postdata String
		APIGetPost GetPost = new APIGetPost();
		GetPost.Get(id, blog_name);

		// Setup The Request
		String url = (GetPost.getPost().getJSONArray("posts")
				.getJSONObject(0).getJSONArray("photos").getJSONObject(0)
				.getJSONObject("original_size").getString("url"));

		// Get The Inital Post Hash
		String post_hash = url.split("/")[3];

		// Check If Post Sum Is A Sum
		if (post_hash.toLowerCase().contains("tumblr") && Conf.getForceSum()) {
			logger.Log("Post Sum Is Not A Sum!", 0, true);
			return;
		}

		while (APIQueue.getQueueCount() > scanned && !found) {

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
						logger.Log(post_hash
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
						logger.Log(post_hash
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
