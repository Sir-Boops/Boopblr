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
	
	private int queue_count = 0;
	
	public int getQueueCount(){
		return this.queue_count;
	}
	
	public void Count() throws Exception {
		
		//Define needed classes
		Config Conf = new Config();
		
		//OAuth Setup
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(Conf.getCustomerKey(), Conf.getCustomerSecret());
		consumer.setTokenWithSecret(Conf.getToken(), Conf.getTokenSecret());
		
		//Queue The Post
		String url = "https://api.tumblr.com/v2/blog/" + Conf.getBlogName() + "/info";
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
		HttpGet get = new HttpGet(url);
		
		consumer.sign(get);
		HttpResponse res = client.execute(get);
		
		String res_string = new BasicResponseHandler().handleResponse(res);
		JSONObject json_res = new JSONObject(res_string);
		this.queue_count = json_res.getJSONObject("response").getJSONObject("blog").getInt("queue");
	}
	
}
