package me.boops.functions.api;

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

public class APIQueueGet {
	
	//Setup OAuth
	OAuthConsumer consumer = new CommonsHttpOAuthConsumer(Config.customer_key, Config.customer_secret);
	
	//Public String
	public JSONArray posts = null;
	
	public APIQueueGet(int offset, int limit) throws Exception {
		
		//Set API User
		consumer.setTokenWithSecret(Config.token, Config.token_secret);
		
		// Setup The Request
		String url = "https://api.tumblr.com/v2/blog/" + Config.blog_name + "/posts/queue?offset=" + offset + "?limit=" + limit;
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
		HttpGet get = new HttpGet(url);
		
		//Send The Request
		consumer.sign(get);
		HttpResponse res = client.execute(get);
		
		// Parse The Response
		String res_string = new BasicResponseHandler().handleResponse(res);
		JSONObject json_res = new JSONObject(res_string);
		posts = (JSONArray) ((Object) ((JSONObject) json_res.get("response")).get("posts"));
		
	}
}
