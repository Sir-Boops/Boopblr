package me.boops.functions.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import me.boops.cache.Config;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

public class APIGetRandPost {
	
	private String blog_name;
	private String type;
	private String reblog_key;
	private String short_url;
	private long id;
	private List<String> tags;
	
	public String getBlogName(){
		return this.blog_name;
	}
	
	public String getPostType(){
		return this.type;
	}
	
	public String getReblogKey(){
		return this.reblog_key;
	}
	
	public String getShortURL(){
		return this.short_url;
	}
	
	public long getID(){
		return this.id;
	}
	
	public List<String> getTags(){
		return this.tags;
	}

	// Class Gobal Settings
	Random randgenerator = new Random();

	public APIGetRandPost() {

		//Define needed classes
		Config Conf = new Config();
		
		// Setup OAuth
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(Conf.getCustomerKey(), Conf.getCustomerSecret());

		// Setup Client
		consumer.setTokenWithSecret(Conf.getToken(), Conf.getTokenSecret());

		// Find The Post
		String url = "https://api.tumblr.com/v2/user/dashboard?offset=" + randgenerator.nextInt(500);
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
		HttpGet get = new HttpGet(url);
		HttpResponse res = null;

		// Sign And Send The Request
		try {
			consumer.sign(get);
			res = client.execute(get);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Check The Reponce For Errors
		if (res.getStatusLine().getStatusCode() >= 200 && res.getStatusLine().getStatusCode() < 300) {

			// Parse The Response
			String res_string = null;
			try {
				res_string = new BasicResponseHandler().handleResponse(res);
			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONObject json_res = new JSONObject(res_string);
			JSONArray posts = json_res.getJSONObject("response").getJSONArray("posts");

			// Now Get A Random Post From That Offset
			JSONObject post = posts.getJSONObject(randgenerator.nextInt(20));

			// Get What We Need From The Post
			this.blog_name = post.getString("blog_name");
			this.type = post.getString("type");
			this.reblog_key = post.getString("reblog_key");
			this.short_url = post.getString("short_url");
			this.id = post.getLong("id");
			
			List<String> temp_tags = new ArrayList<String>();
			
			//Put The Tags Into A List
			if(!post.isNull("tags")){
				for(int runs=0; runs<post.getJSONArray("tags").length(); runs++){
					temp_tags.add(post.getJSONArray("tags").getString(runs));
				}
			}
			
			//Set This Tags
			this.tags = temp_tags;
		}
	}
}
