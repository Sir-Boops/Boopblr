package me.boops.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;


public class Config {
	
	//Define Strings
	public static String version = "5.1.0";
	public static String customerKey;
	public static String customerSecret;
	public static String token;
	public static String tokenSecret;
	public static String blogName;
	public static boolean useDash;
	public static int lengthOfPage;
	public static int minTags;
	public static List<String> postTypes = new ArrayList<String>();
	public static List<String> blacklistedTags = new ArrayList<String>();
	public static List<String> whitelistedTags = new ArrayList<String>();
	public static List<String> tagAddBlacklist = new ArrayList<String>();
	public static List<String> userTags = new ArrayList<String>();
	
	public Config() {
		
		StringBuilder sb = new StringBuilder();
		
		try{
			
			// Read The Config File
			BufferedReader br = new BufferedReader(new FileReader("config.json"));
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			br.close();
			
		} catch (IOException err){
			System.out.println("Error reading config file");
			System.exit(0);
		}

		// Parse The Config File
		JSONObject config = new JSONObject(sb.toString());

		// Write options to strings File if they are set
		Config.customerKey = config.getString("customer_key");
		Config.customerSecret = config.getString("customer_secret");
		Config.token = config.getString("token");
		Config.tokenSecret = config.getString("token_secret");
		Config.blogName = config.getString("blog_name");
		Config.useDash = config.getBoolean("use_dash");
		Config.lengthOfPage = config.getInt("length_of_page");
		Config.minTags = config.getInt("min_tags");
		for(int runs=0; config.getJSONArray("allowed_post_types").length()>runs; runs++){
			Config.postTypes.add(config.getJSONArray("allowed_post_types").getString(runs));
		}
		for(int runs=0; config.getJSONArray("user_tags").length()>runs; runs++){
			Config.userTags.add(config.getJSONArray("user_tags").getString(runs));
		}
		for(int runs=0; config.getJSONArray("blacklisted_tags").length()>runs; runs++){
			Config.blacklistedTags.add(config.getJSONArray("blacklisted_tags").getString(runs));
		}
		for(int runs=0; config.getJSONArray("whitelisted_tags").length()>runs; runs++){
			Config.whitelistedTags.add(config.getJSONArray("whitelisted_tags").getString(runs));
		}
		for(int runs=0; config.getJSONArray("tag_add_blacklist").length()>runs; runs++){
			Config.tagAddBlacklist.add(config.getJSONArray("tag_add_blacklist").getString(runs));
		}
	}
}
