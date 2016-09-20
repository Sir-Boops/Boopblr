package me.boops.functions.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import me.boops.cache.Config;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

public class APIQueuePost {

	// Pre Defined Things
	OAuthConsumer consumer = new CommonsHttpOAuthConsumer(Config.customer_key, Config.customer_secret);

	public APIQueuePost(Long id, String key) throws Exception {
		consumer.setTokenWithSecret(Config.token, Config.token_secret);

		// Setup The Request
		String url = "https://api.tumblr.com/v2/blog/" + Config.blog_name + "/post/reblog";
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
		HttpPost post_meth = new HttpPost(url);

		// Build The Post Data
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("id", id.toString()));
		urlParameters.add(new BasicNameValuePair("reblog_key", key));
		urlParameters.add(new BasicNameValuePair("state", "queue"));
		urlParameters.add(new BasicNameValuePair("tags", Config.user_tags));
		post_meth.setEntity(new UrlEncodedFormEntity(urlParameters));

		// Send The Request
		consumer.sign(post_meth);
		client.execute(post_meth);
		Config.queue_count = (Config.queue_count + 1);
	}
}
