package me.boops.functions.nonapi;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
		
		char[] chars = blog_name.toCharArray();
		int check_runs = 0;
		
		while(check_runs < chars.length && !error){
			if(chars[check_runs] > 127){
				
				//Non ISO-8859-1 Char
				System.out.println("Non en_US Char");
				error = true;
				return;
			}
			check_runs++;
		}

		// Setup The Request
		String url = "http://" + new String(blog_name.getBytes("UTF-8")) + ".tumblr.com/" + id;
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