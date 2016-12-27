package me.boops.cache;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import me.boops.logger.Logger;


public class Config {
	
	//Define Strings
	private String version = "3.6.4";
	private String customer_key;
	private String customer_secret;
	private String token;
	private String token_secret;
	private String blog_name;
	private int post_speed;
	private int queue_size;
	private boolean check_tags;
	private boolean force_tags;
	private boolean check_queue;
	private boolean spider_tags;
	private int black_depth;
	private int white_depth;
	private List<String> post_types = new ArrayList<String>();
	private List<String> blacklisted_tags = new ArrayList<String>();
	private List<String> whitelisted_tags = new ArrayList<String>();
	private List<String> tag_add_blacklist = new ArrayList<String>();
	private List<String> user_tags = new ArrayList<String>();
	private List<String> banned_photo_sums = new ArrayList<String>();
	private int note_depth;
	private int min_tags;
	private int hash_life;
	private boolean add_tags;
	private int add_tags_depth;
	private boolean debug_output;
	private boolean clean_bad_post_ids;
	private int clean_bad_post_ids_time;
	
	//Set the vars to public
	
	public String getVersion(){
		return this.version;
	}
	
	public String getCustomerKey(){
		return this.customer_key;
	}
	
	public String getCustomerSecret(){
		return this.customer_secret;
	}
	
	public String getToken(){
		return this.token;
	}
	
	public String getTokenSecret(){
		return this.token_secret;
	}
	
	public String getBlogName(){
		return this.blog_name;
	}
	
	public int getPostSpeed(){
		return this.post_speed;
	}
	
	public int getQueueSize(){
		return this.queue_size;
	}
	
	public boolean getCheckTags(){
		return this.check_tags;
	}
	
	public boolean getForceTags(){
		return this.force_tags;
	}
	
	public boolean getCheckQueue(){
		return this.check_queue;
	}
	
	public boolean getSpiderTags(){
		return this.spider_tags;
	}
	
	public int getBlackDepth(){
		return this.black_depth;
	}
	
	public int getWhiteDepth(){
		return this.white_depth;
	}
	
	public List<String> getPostTypes(){
		return this.post_types;
	}
	
	public List<String> getBlacklistedTags(){
		return this.blacklisted_tags;
	}
	
	public List<String> getWhitelistedTags(){
		return this.whitelisted_tags;
	}
	
	public List<String> getTagAddBlacklist(){
		return this.tag_add_blacklist;
	}
	
	public List<String> getBannedPhotoSums(){
		return this.banned_photo_sums;
	}
	
	public List<String> getUserTags(){
		return this.user_tags;
	}
	
	public int getNoteDepth(){
		return this.note_depth;
	}
	
	public int getMinTags(){
		return this.min_tags;
	}
	
	public int getHashLife(){
		return this.hash_life;
	}
	
	public boolean getAddTags(){
		return this.add_tags;
	}
	
	public int getAddTagsDepth(){
		return this.add_tags_depth;
	}
	
	public boolean getDebugOutput(){
		return this.debug_output;
	}
	
	public boolean getShouldCleanBadIDs(){
		return this.clean_bad_post_ids;
	}
	
	public int getBadIDCleanTime(){
		return this.clean_bad_post_ids_time;
	}
	
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
			new Logger().Log("Error Reading Config File", 1, false);
		}

		// Parse The Config File
		JSONObject config = new JSONObject(sb.toString());

		// Write options to strings File if they are set
		this.customer_key = config.getString("customer_key");
		this.customer_secret = config.getString("customer_secret");
		this.token = config.getString("token");
		this.token_secret = config.getString("token_secret");
		this.blog_name = config.getString("blog_name");
		this.post_speed = config.getInt("post_speed");
		this.queue_size = config.getInt("queue_size");
		this.check_tags = config.getBoolean("check_tags");
		this.force_tags = config.getBoolean("force_tags");
		this.check_queue = config.getBoolean("queue_check");
		this.hash_life = config.getInt("hash_life");
		this.add_tags = config.getBoolean("add_tags");
		this.spider_tags = config.getBoolean("spider_tags");
		this.debug_output = config.getBoolean("debug_output");
		this.black_depth = config.getInt("black_depth");
		this.white_depth = config.getInt("white_depth");
		this.note_depth = config.getInt("note_depth");
		this.min_tags = config.getInt("min_tags");
		this.add_tags_depth = config.getInt("add_tags_depth");
		this.clean_bad_post_ids = config.getBoolean("clean_bad_post_ids");
		this.clean_bad_post_ids_time = config.getInt("clean_bad_post_ids_time");
		for(int runs=0; config.getJSONArray("allowed_post_types").length()>runs; runs++){
			this.post_types.add(config.getJSONArray("allowed_post_types").getString(runs));
		}
		for(int runs=0; config.getJSONArray("user_tags").length()>runs; runs++){
			this.user_tags.add(config.getJSONArray("user_tags").getString(runs));
		}
		for(int runs=0; config.getJSONArray("blacklisted_tags").length()>runs; runs++){
			this.blacklisted_tags.add(config.getJSONArray("blacklisted_tags").getString(runs));
		}
		for(int runs=0; config.getJSONArray("whitelisted_tags").length()>runs; runs++){
			this.whitelisted_tags.add(config.getJSONArray("whitelisted_tags").getString(runs));
		}
		for(int runs=0; config.getJSONArray("tag_add_blacklist").length()>runs; runs++){
			this.tag_add_blacklist.add(config.getJSONArray("tag_add_blacklist").getString(runs));
		}
		for(int runs=0; config.getJSONArray("banned_photo_sums").length()>runs; runs++){
			this.banned_photo_sums.add(config.getJSONArray("banned_photo_sums").getString(runs));
		}
	}
}
