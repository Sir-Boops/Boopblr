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

public class APIGetBlog {
	
	public JSONObject blog = null;
	OAuthConsumer consumer = new CommonsHttpOAuthConsumer(Config.customer_key, Config.customer_secret);
	
	public APIGetBlog(String name) throws Exception {
		
		// Setup Client
		consumer.setTokenWithSecret(Config.token, Config.token_secret);
		
		// Setup The Request
		String url = "https://api.tumblr.com/v2/blog/" + name + "/info";
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
		HttpGet get = new HttpGet(url);
		
		// Send The Request
		consumer.sign(get);
		HttpResponse res = client.execute(get);
		
		if (res.getStatusLine().getStatusCode() >= 200 && res.getStatusLine().getStatusCode() < 300) {

			// Parse The Response
			String res_string = new BasicResponseHandler().handleResponse(res);
			JSONObject json_res = new JSONObject(res_string);
			blog = (JSONObject) json_res.get("response");
		} else {
			
			//Tumblr Error Try Again
			System.out.println("Error Getting Blog Info Trying Again");
			new APIGetBlog(name);
		}
	}
}
