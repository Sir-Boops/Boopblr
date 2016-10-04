package me.boops.functions.queuecheck;

import org.json.JSONArray;

import me.boops.cache.Cache;
import me.boops.crypto.MD5Sum;
import me.boops.functions.api.APIGetPost;
import me.boops.functions.api.APIQueueCount;
import me.boops.functions.api.APIQueueGet;
import me.boops.logger.Logger;

public class QueueCheckAnswer {

	public boolean found;

	public QueueCheckAnswer(Long id, String blog_name) throws Exception {
		
		//Define needed classes
		APIQueueCount APIQueue = new APIQueueCount();
		Logger logger = new Logger();
		
		//Count Posts In Queue
		APIQueue.Count();
		
		// Scan The Posts In Queue
		int scanned = 0;

		// Get Check Post Hash
		new APIGetPost(id, blog_name);
		String post_hash = new MD5Sum().hash(Cache.get_post_post.getJSONArray("posts").getJSONObject(0).getJSONObject("reblog").getString("comment"));

		while (APIQueue.getQueueCount() > scanned && !found) {

			// Get The Posts To Scan
			JSONArray posts = new APIQueueGet(scanned, 20).posts;

			// Scan The Posts
			int sub_runs = 0;
			while (sub_runs < posts.length()) {

				// Check If Post Type Is Photo
				if (posts.getJSONObject(sub_runs).getString("type").equals("answer")) {

					// Calcucate The Post Hash And CHeck it!
					if (post_hash.equals(new MD5Sum().hash(posts.getJSONObject(sub_runs).getJSONObject("reblog").getString("comment"))) && !found) {

						// Found A Duplicate!
						found = true;
					} else {

						// Nope Keep Chacking
						logger.Log(post_hash + " : " + new MD5Sum().hash(posts.getJSONObject(sub_runs).getJSONObject("reblog").getString("comment")), 0, true);
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
