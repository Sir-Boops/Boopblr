package me.boops.base;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GetURLRaw {
	public String connect(String URL, String[] titles, String[] args) throws Exception {

		// Set get args
		if (titles != null && args != null) {
			for (int i = 0; i < titles.length; i++) {
				if(i == 0) {
					URL += ("?" + titles[i] + "=" + args[i]);
				} else {
					URL += ("&" + titles[i] + "=" + args[i]);
				}
			}
		}

		// Create the URL
		URL url = new URL(URL);

		// Create the connection and set basic settings
		// Then connect
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setReadTimeout(10 * 1000);
		conn.setConnectTimeout(10 * 1000);
		conn.setRequestMethod("GET");

		// Open the connection
		conn.connect();

		// Read/Parse the input
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		StringBuilder sb = new StringBuilder();
		String inByte;
		while ((inByte = in.readLine()) != null) {
			sb.append(inByte);
		}

		// Finally return the ans
		return sb.toString();

	}
}
