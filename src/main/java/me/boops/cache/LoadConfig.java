package me.boops.cache;

import java.io.BufferedReader;
import java.io.FileReader;

import org.json.JSONObject;


public class LoadConfig {

	public LoadConfig() throws Exception {

		// Read The Config File
		BufferedReader br = new BufferedReader(new FileReader("config.json"));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			line = br.readLine();
		}
		br.close();

		// Parse The Config File
		JSONObject config = new JSONObject(sb.toString());

		// Write options to strings File if they are set

		if (config.get("customer_key") != null) {
			Config.customer_key = config.getString("customer_key");
		}

		if (config.get("customer_secret") != null) {
			Config.customer_secret = config.getString("customer_secret");
		}

		if (config.get("token") != null) {
			Config.token = config.getString("token");
		}

		if (config.get("token_secret") != null) {
			Config.token_secret = config.getString("token_secret");
		}

		if (config.get("blog_name") != null) {
			Config.blog_name = config.getString("blog_name");
		}

		if (config.get("post_speed") != null) {
			Config.post_speed = config.getInt("post_speed");
		}

		if (config.get("queue_size") != null) {
			Config.queue_size = config.getInt("queue_size");
		}

		if (config.get("check_tags") != null) {
			Config.check_tags = config.getBoolean("check_tags");
		}

		if (config.get("force_tags") != null) {
			Config.force_tags = config.getBoolean("force_tags");
		}

		if (config.get("queue_check") != null) {
			Config.check_queue = config.getBoolean("queue_check");
		}
		
		if (config.get("force_sum") != null) {
			Config.force_sum = config.getBoolean("force_sum");
		}
		
		if (config.get("add_tags") != null) {
			Config.add_tags = config.getBoolean("add_tags");
		}

		if (config.get("spider_tags") != null) {
			Config.spider_tags = config.getBoolean("spider_tags");
		}
		
		if (config.get("debug_output") != null) {
			Config.debug_output = config.getBoolean("debug_output");
		}

		if (config.get("black_depth") != null) {
			Config.black_depth = config.getInt("black_depth");
		}

		if (config.get("white_depth") != null) {
			Config.white_depth = config.getInt("white_depth");
		}
		
		if (config.get("note_depth") != null) {
			Config.note_depth = config.getInt("note_depth");
		}
		
		if (config.get("min_tags") != null) {
			Config.min_tags = config.getInt("min_tags");
		}
		
		if (config.get("add_tags_depth") != null) {
			Config.add_tags_depth = config.getInt("add_tags_depth");
		}
		
		if (config.get("allowed_post_types") != null) {
			for(int runs=0; config.getJSONArray("allowed_post_types").length()>runs; runs++){
				Config.post_types.add(config.getJSONArray("allowed_post_types").getString(runs));
			}
		}

		if (config.get("user_tags") != null) {
			for(int runs=0; config.getJSONArray("user_tags").length()>runs; runs++){
				Config.user_tags.add(config.getJSONArray("user_tags").getString(runs));
			}
		}

		if (config.getJSONArray("blacklisted_tags") != null) {
			for(int runs=0; config.getJSONArray("blacklisted_tags").length()>runs; runs++){
				Config.blacklisted_tags.add(config.getJSONArray("blacklisted_tags").getString(runs));
			}
		}

		if (config.getJSONArray("whitelisted_tags") != null) {
			for(int runs=0; config.getJSONArray("whitelisted_tags").length()>runs; runs++){
				Config.whitelisted_tags.add(config.getJSONArray("whitelisted_tags").getString(runs));
			}
		}
		
		if (config.getJSONArray("tag_add_blacklist") != null) {
			for(int runs=0; config.getJSONArray("tag_add_blacklist").length()>runs; runs++){
				Config.tag_add_blacklist.add(config.getJSONArray("tag_add_blacklist").getString(runs));
			}
		}
	}
}
