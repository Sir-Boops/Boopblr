package me.boops.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import me.boops.config.Config;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

public class PostURLOAuth {

	public String connect(String URL, String[] titles, String[] args) throws Exception {
		
		// Define oauth
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(Config.customerKey, Config.customerSecret);
		consumer.setTokenWithSecret(Config.token, Config.tokenSecret);
		
		HttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(URL);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if(titles != null && args != null) {
			for(int i = 0; i < titles.length; i++) {
				params.add(new BasicNameValuePair(titles[i], args[i]));
			}
		}
		post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		consumer.sign(post);
		
		// Send the request
		HttpResponse response = client.execute(post);
		System.out.println(response.getStatusLine().getStatusCode());
		String meta = new BasicResponseHandler().handleResponse(response);
		
		// Finally return the ans
		return meta;
		
	}
}
