package me.boops.functions.nonapi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import me.boops.cache.Cache;
import me.boops.cache.Config;
import me.boops.logger.Logger;
import me.boops.parsers.GetMoreNotesLink;
import me.boops.parsers.UrlToUsable;

public class GetAllNotes {
	
	public boolean error = false;

	public GetAllNotes(long id, String blog_name) throws Exception {
		
		//Check If We Can Get The URL
		if(new GetNotesLink(id, blog_name).error){
			//Can't Work With This URL
			error = true;
			return;
		}

		// Setup The Request
		String url = Cache.all_notes_url;
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
		HttpGet get = new HttpGet(url);

		// Send The Request
		HttpResponse res = client.execute(get);

		// Check It
		if (res.getStatusLine().getStatusCode() < 199 || res.getStatusLine().getStatusCode() > 300) {
			new Logger("Error Getting All Notes", 2);
			error = true;
			return;
		}

		// Parse It
		Document doc = Jsoup.parse(new BasicResponseHandler().handleResponse(res));

		// Strings For The Loop
		String next_link;
		List<String> names = new ArrayList<String>();
		List<Long> ids = new ArrayList<Long>();
		boolean done = false;
		// Loop To Get More Notes
		while (!done && names.size() < Config.note_depth) {

			// Parse Page
			int parse_runs = 0;
			while (parse_runs < doc.getElementsByClass("action").size()) {

				// Check If Reblog Or Like
				if (doc.getElementsByClass("action").get(parse_runs).hasAttr("data-post-url")) {

					// Got One!
					String user_name = new UrlToUsable(
							doc.getElementsByClass("action").get(parse_runs).attr("data-post-url")).user_name;
					long user_id = new UrlToUsable(
							doc.getElementsByClass("action").get(parse_runs).attr("data-post-url")).pub_id;

					// Check If User Is Already In The List
					if (!names.contains(user_name)) {

						// Ok It's Good To Add TO THe List!
						names.add(user_name);
						ids.add(user_id);
						parse_runs++;
					}
					parse_runs++;
				}

				// It's A Like
				parse_runs++;
			}

			// Finished Parsing IDs
			// Now Get Next URL If we can
			if (!doc.getElementsByClass("more_notes_link_container").isEmpty()) {

				// More Notes To Get!
				next_link = new GetMoreNotesLink(doc.getElementsByClass("more_notes_link_container").toString(),
						blog_name, id).new_url;

				// Ask Tumblr For More!
				// Setup The Request
				String sub_url = next_link;
				HttpClient sub_client = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
				HttpGet sub_get = new HttpGet(sub_url);

				HttpResponse sub_res = sub_client.execute(sub_get);

				doc = Jsoup.parse(new BasicResponseHandler().handleResponse(sub_res));
			} else {

				// We're Done!
				done = true;
				Cache.pub_names = names;
				Cache.pub_ids = ids;
			}
		}

	}

}
