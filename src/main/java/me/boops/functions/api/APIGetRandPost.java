package me.boops.functions.api;

import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import me.boops.cache.Cache;
import me.boops.cache.Config;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

public class APIGetRandPost {

	// Class Gobal Settings
	OAuthConsumer consumer = new CommonsHttpOAuthConsumer(Config.customer_key, Config.customer_secret);
	Random randgenerator = new Random();

	public APIGetRandPost() throws Exception {

		// Setup The Client
		consumer.setTokenWithSecret(Config.token, Config.token_secret);

		// Find The Post
		String url = "https://api.tumblr.com/v2/user/dashboard?offset=" + randgenerator.nextInt(500);
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
		HttpGet get = new HttpGet(url);

		// Sign And Send The Request
		consumer.sign(get);
		HttpResponse res = client.execute(get);

		// Check The Reponce For Errors
		if (res.getStatusLine().getStatusCode() >= 200 && res.getStatusLine().getStatusCode() < 300) {

			// Parse The Response
			String res_string = new BasicResponseHandler().handleResponse(res);
			JSONObject json_res = new JSONObject(res_string);
			JSONArray posts = json_res.getJSONObject("response").getJSONArray("posts");

			// Now Get A Random Post From That Offset
			JSONObject post = posts.getJSONObject(randgenerator.nextInt(20));

			// Get What We Need From The Post
			Cache.rand_post_user = post.getString("blog_name");
			Cache.rand_post_type = post.getString("type");
			Cache.rand_post_key = post.getString("reblog_key");
			Cache.rand_post_url = post.getString("short_url");
			Cache.rand_post_id = post.getLong("id");
			
			//Put The Tags Into A List
			if(!post.isNull("tags")){
				
				//Empty The Cache
				Cache.rand_post_tags.clear();
				
				int runs = 0;
				while(runs < post.getJSONArray("tags").length()){
					
					Cache.rand_post_tags.add(post.getJSONArray("tags").getString(runs));
					runs++;
				}
			}
		}
	}
}
