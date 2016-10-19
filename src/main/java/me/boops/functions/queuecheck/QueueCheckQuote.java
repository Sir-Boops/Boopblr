package me.boops.functions.queuecheck;

import me.boops.cache.Config;
import me.boops.crypto.MD5Sum;
import me.boops.logger.Logger;
import pw.frgl.jumblr.BlogInfo;
import pw.frgl.jumblr.BlogPosts;
import pw.frgl.jumblr.BlogQueue;
import pw.frgl.jumblr.DecodePost;

public class QueueCheckQuote {

	public boolean found;

	public QueueCheckQuote(long id, String blog_name) throws Exception {
		
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
		
		String post_hash = new MD5Sum().hash(decode.getPostQuoteText());

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
				if (decode.getPostType().equals("quote")) {

					// Calcucate The Post Hash And CHeck it!
					if (post_hash.equals(new MD5Sum().hash(decode.getPostQuoteText()))
							&& !found) {

						// Found A Duplicate!
						found = true;
					} else {

						// Nope Keep Chacking
						logger.Log(
								post_hash + " : " + new MD5Sum().hash(decode.getPostQuoteText()),
								0, true);
						sub_runs++;
					}
				}

				// Non Photo Post So Skip In This Check
				sub_runs++;
			}
			scanned = (scanned + queue.getResPostCount());
		}
	}

}
