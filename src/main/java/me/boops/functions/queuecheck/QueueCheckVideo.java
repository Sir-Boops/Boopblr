package me.boops.functions.queuecheck;

import org.json.JSONArray;
import org.json.JSONObject;

import me.boops.crypto.MD5Sum;
import me.boops.functions.api.APIGetPost;
import me.boops.functions.api.APIQueueCount;
import me.boops.functions.api.APIQueueGet;
import me.boops.logger.Logger;

public class QueueCheckVideo {

	public boolean found;

	public QueueCheckVideo(Long id, String blog_name) throws Exception {
		
		//Define needed classes
		APIQueueCount APIQueue = new APIQueueCount();
		Logger logger = new Logger();
		
		//Count Posts In Queue
		APIQueue.Count();

		// Scan The Posts In Queue
		int scanned = 0;

		// Get Check Post Hash
		APIGetPost GetPost = new APIGetPost(id, blog_name);
		String post_hash = new MD5Sum()
				.hash(((JSONObject) GetPost.getPost().getJSONArray("posts").get(0))
						.getJSONArray("player").toString());

		while (APIQueue.getQueueCount() > scanned && !found) {

			// Get The Posts To Scan
			JSONArray posts = new APIQueueGet(scanned, 20).posts;

			// Scan The Posts
			int sub_runs = 0;
			while (sub_runs < posts.length()) {

				// Check If Post Type Is Photo
				if (((JSONObject) posts.get(sub_runs)).get("type").equals("video")) {

					// Calcucate The Post Hash And CHeck it!
					if (post_hash
							.equals(new MD5Sum().hash(posts.getJSONObject(sub_runs).getJSONObject("player").toString()))
							&& !found) {

						// Found A Duplicate!
						found = true;
					} else {

						// Nope Keep Chacking
						logger.Log(
								post_hash + " : "
										+ new MD5Sum()
												.hash(posts.getJSONObject(sub_runs).getJSONObject("player").toString()),
								0, true);
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
