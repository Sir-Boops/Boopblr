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
	
	public JSONObject Blog(String name) throws Exception {
		
		//Define needed classes
		Config Conf = new Config();
		
		//Setup OAuth
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(Conf.getCustomerKey(), Conf.getCustomerSecret());
		
		// Setup Client
		consumer.setTokenWithSecret(Conf.getToken(), Conf.getTokenSecret());
		
		// Setup The Request
		String url = "https://api.tumblr.com/v2/blog/" + name + "/info";
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
		HttpGet get = new HttpGet(url);
		
		// Send The Request
		consumer.sign(get);
		HttpResponse res = client.execute(get);
		
		if (res.getStatusLine().getStatusCode() == 200) {

			// Parse The Response
			String res_string = new BasicResponseHandler().handleResponse(res);
			JSONObject json_res = new JSONObject(res_string);
			return json_res.getJSONObject("response");
		}
		return null;
	}
}
