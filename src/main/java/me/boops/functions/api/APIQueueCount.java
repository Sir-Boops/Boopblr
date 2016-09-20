package me.boops.functions.api;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import me.boops.cache.Config;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

public class APIQueueCount {
	
	public int queue_count = 0;
	
	public APIQueueCount() throws Exception {
		
		//OAuth Setup
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(Config.customer_key, Config.customer_secret);
		consumer.setTokenWithSecret(Config.token, Config.token_secret);
		
		//Queue The Post
		String url = "https://api.tumblr.com/v2/blog/" + Config.blog_name + "/info";
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
		HttpGet get = new HttpGet(url);
		
		consumer.sign(get);
		HttpResponse res = client.execute(get);
		
		String res_string = new BasicResponseHandler().handleResponse(res);
		JSONObject json_res = new JSONObject(res_string);
		queue_count = (int) (((JSONObject) ((JSONObject) json_res.get("response")).get("blog")).get("queue"));
		Config.queue_count = (int) (((JSONObject) ((JSONObject) json_res.get("response")).get("blog")).get("queue"));
	}
	
}
