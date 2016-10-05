package me.boops.functions.queuecheck;

import org.json.JSONArray;
import org.json.JSONObject;

import me.boops.crypto.MD5Sum;
import me.boops.functions.api.APIGetPost;
import me.boops.functions.api.APIQueueCount;
import me.boops.functions.api.APIQueueGet;
import me.boops.logger.Logger;

public class QueueCheckText {

	public boolean found;

	public QueueCheckText(long id, String blog_name) throws Exception {
		
		//Define needed classes
		APIQueueCount APIQueue = new APIQueueCount();
		Logger logger = new Logger();
		
		//Count Posts In Queue
		APIQueue.Count();

		// Hash The Text Post
		APIGetPost GetPost = new APIGetPost();
		GetPost.Get(id, blog_name);
		String post_hash = new MD5Sum().hash(GetPost.getPost().getJSONArray("posts").getJSONObject(0)
				.getJSONObject("reblog").get("tree_html").toString());

		// Scan The Posts In Queue
		int scanned = 0;

		while (APIQueue.getQueueCount() > scanned && !found) {

			// Get The Posts To Scan
			JSONArray posts = new APIQueueGet(scanned, 20).posts;

			// Scan The Posts
			int sub_runs = 0;
			while (sub_runs < posts.length()) {

				// Check If Post Type Is text
				if (((JSONObject) posts.get(sub_runs)).get("type").equals("text")) {

					// Calcucate The Post Hash And CHeck it!
					if (post_hash.equals(new MD5Sum().hash(posts.getJSONObject(sub_runs).getJSONObject("reblog")
							.getJSONObject("tree_html").toString())) && !found) {

						// Found A Duplicate!
						found = true;
					} else {

						// Nope Keep Chacking
						logger.Log(post_hash + " : " + new MD5Sum().hash(posts.getJSONObject(sub_runs)
								.getJSONObject("reblog").getJSONObject("tree_html").toString()), 0, true);
						sub_runs++;
					}
				}

				// Non Text Post So Skip In This Check
				sub_runs++;
			}
			scanned = (scanned + posts.length());
		}
	}
}
