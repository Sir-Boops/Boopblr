package me.boops.nonapi.functions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class LoadAPage {
	
	public String load(URL url) throws Exception {
		
		URLConnection conn = url.openConnection();
		conn.setReadTimeout(10 * 1000);
		conn.setConnectTimeout(10 * 1000);
		conn.connect();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		
		StringBuilder sb = new StringBuilder();
		String inByte;
		while((inByte = in.readLine()) != null) {
			sb.append(inByte);
		}
		
		return sb.toString();
		
	}
}
