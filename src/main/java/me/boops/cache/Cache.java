package me.boops.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Cache {

	//Main
	//public static String name_fancy;
	
	//Tag Scanning
	public static List<String> pub_names = new ArrayList<String>();
	public static List<Long> pub_ids = new ArrayList<Long>();
	
	//Tags
	public static List<String> post_tags = new ArrayList<String>();
	public static Map <String, Integer> tag_usage = new HashMap<String, Integer>();
	public static List<String> tag_list = new ArrayList<String>();
	public static List<String> add_tags_list = new ArrayList<String>();
	public static String gend_tags = "";
	
	//Random Post Cache
	public static String rand_post_user;
	public static String rand_post_type;
	public static String rand_post_key;
	public static String rand_post_url;
	public static Long rand_post_id;
	public static List<String> rand_post_tags = new ArrayList<String>();
	
	//All Notes
	public static String all_notes_url;
	
	//API Get Post
	public static JSONObject get_post_post;

}
