package me.boops.functions.queuecheck;

import me.boops.cache.Config;
import me.boops.logger.Logger;
import pw.frgl.jumblr.BlogInfo;
import pw.frgl.jumblr.BlogPosts;
import pw.frgl.jumblr.BlogQueue;
import pw.frgl.jumblr.DecodePost;

public class QueueCheckPhoto {

	public boolean found;

	public QueueCheckPhoto(long id, String blog_name) throws Exception {
		
		//Define needed classes
		Logger logger = new Logger();
		Config Conf = new Config();
		BlogPosts post = new BlogPosts(Conf.getCustomerKey(), Conf.getCustomerSecret(), Conf.getToken(), Conf.getTokenSecret());
		BlogInfo blog = new BlogInfo(Conf.getCustomerKey(), Conf.getCustomerSecret(), Conf.getToken(), Conf.getTokenSecret());
		BlogQueue queue = new BlogQueue(Conf.getCustomerKey(), Conf.getCustomerSecret(), Conf.getToken(), Conf.getTokenSecret());
		DecodePost decode = new DecodePost();
		
		//Count Posts In Queue
		blog.getBlog(Conf.getBlogName());

		// Scan The Posts In Queue
		int scanned = 0;

		//Find the post
		post.setPostID(id);
		post.getPosts(blog_name);
		
		//Decode the post
		decode.decode(post.getPost(0));

		// Setup The Request
		String url = (decode.getOrginalPhotoURL());
		
		System.out.println(url);

		// Get The Inital Post Hash
		String post_hash = url.split("/")[3];

		// Check If Post Sum Is A Sum
		if (post_hash.toLowerCase().contains("tumblr") && Conf.getForceSum()) {
			logger.Log("Post Sum Is Not A Sum!", 0, true);
			return;
		}

		while (blog.getQueueCount() > scanned && !found) {

			// Get The Posts To Scan
			queue.setOffset(scanned);
			queue.getQueue(Conf.getBlogName());

			// Scan The Posts
			int sub_runs = 0;
			while (sub_runs < queue.getResPostCount()) {
				
				//Decode the post
				decode.decode(queue.getPost(sub_runs));

				// Check If Post Type Is Photo
				if (decode.getPostType().equals("photo")) {

					// Check The Post Hash
					if (post_hash.equals(decode.getOrginalPhotoURL().split("/")[3])
							&& !found) {

						// Found A Duplicate!
						logger.Log(post_hash + " : "+ decode.getOrginalPhotoURL().split("/")[3], 0, true);
						found = true;
						return;
					} else {

						// Nope Keep Chacking
						logger.Log(post_hash + " : " + decode.getOrginalPhotoURL().split("/")[3], 0, true);
					}
				}

				sub_runs++;
			}
			scanned = (scanned + queue.getResPostCount());
		}
	}
}
