package me.boops.functions;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import me.boops.cache.Cache;
import me.boops.config.Config;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

public class GetRandomPost {
	
	public JSONObject getPosts() throws Exception {
		
		// Load the config
		Config config = new Config();
		
		System.out.println(Cache.lastPost);
		
		if(Cache.lastPost >= config.getDashLength()) {
			Cache.lastPost = 0;
		}
		
		// Define oauth
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(config.getCustomerKey(), config.getCustomerSecret());
		consumer.setTokenWithSecret(config.getToken(), config.getTokenSecret());
		
		//Create The URL
		String url = "https://api.tumblr.com/v2/user/dashboard?limit=1&offset=" + Cache.lastPost;
		
		// Setup The Request
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new DefaultHostnameVerifier()).build();
		RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(10*1000).setConnectTimeout(10*1000).setConnectionRequestTimeout(10*1000).build();
		HttpGet get = new HttpGet(url);
		get.setConfig(reqConfig);
		
		consumer.sign(get);

		// Send the request
		HttpResponse res = client.execute(get);
		
		Cache.lastPost++;
		JSONObject json = new JSONObject(new BasicResponseHandler().handleResponse(res));
		return json.getJSONObject("response").getJSONArray("posts").getJSONObject(0);
		
	}
}
