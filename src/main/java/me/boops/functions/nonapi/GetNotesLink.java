package me.boops.functions.nonapi;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import me.boops.cache.Config;
import me.boops.logger.Logger;
import pw.frgl.jumblr.BlogPosts;
import pw.frgl.jumblr.DecodePost;

public class GetNotesLink {

	private boolean error;
	private String all_notes_url;
	
	public boolean ifError(){
		return this.error;
	}
	
	public String getAllNotesURL(){
		return this.all_notes_url;
	}

	public GetNotesLink(long id, String blog_name) throws Exception {
		
		//Check if their is a slug
		Config Conf = new Config();
		BlogPosts post = new BlogPosts(Conf.getCustomerKey(), Conf.getCustomerSecret(), Conf.getToken(), Conf.getTokenSecret());
		DecodePost decode = new DecodePost();
		
		post.setPostID(id);
		post.getPosts(blog_name);
		
		decode.decode(post.getPost(0));
		
		if(!decode.getPostSlug().isEmpty()){
			
			//Scan the slug and check for non en_US chars
			char[] slug_chars = decode.getPostSlug().toCharArray();
			for(int i=0; slug_chars.length>i; i++){
				if(slug_chars[i] > 127){
					
					//Found a non en_US char
					new Logger().Log("Found a non en_US char", 0, false);
					this.error = true;
					return;
				}
			}
		}
		

		// Setup The Request
		String url = "http://" + blog_name + ".tumblr.com/" + id;
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
		HttpGet get = new HttpGet(url);

		// Send The Request
		HttpResponse res = client.execute(get);

		// Parse It
		Document doc = Jsoup.parse(new BasicResponseHandler().handleResponse(res));

		// Get Notes Key
		boolean done = false;
		String scan_item = doc.getElementsByClass("more_notes_link").toString();
		String notes_url = "http://" + blog_name + ".tumblr.com/notes/" + id + "/";
		int extra = ("/notes/" + id + "/").length();
		int pos = scan_item.indexOf("/notes/") + extra;
		int runs = 0;
		while (!done) {

			if ((pos + runs) < scan_item.length()) {

				// Convert Char To A String
				String letter = "" + scan_item.charAt(pos + runs);

				// Check For the ?
				if (!letter.equals("?")) {

					// Still Need More
					notes_url = (notes_url + scan_item.charAt(pos + runs));
					// runs++;
					runs++;
				} else {

					// Done
					done = true;
				}
			} else {
				done = true;
			}
		}
		this.all_notes_url = notes_url;
	}

}