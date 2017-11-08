package me.boops.cache;

import java.util.ArrayList;
import java.util.List;

public class Cache {
	
	// Clean on every run
	public static List<String> reblogUsers = new ArrayList<String>();
	public static List<String> likeUsers = new ArrayList<String>();
	public static List<Long> reblogIDs = new ArrayList<Long>();
	public static List<String> tags  = new ArrayList<String>();
	public static List<String> tagList = new ArrayList<String>();
	public static List<Integer> tagCount = new ArrayList<Integer>();
	public static List<String> tagToAppend = new ArrayList<String>();
	public static List<String> allowedTags = new ArrayList<String>();
	
	// Manually clean
	public static boolean isInitalRun; // Used in main
	public static long lastTimeStamp; // Used on PostFromGlobal
	public static List<String> hashList = new ArrayList<String>();
	public static long hashCacheAge;
	public static List<Long> badPostIDs = new ArrayList<Long>(); // Used in FindNewPost
	public static long badPostIDsAge; // Used in main
	public static int lastPost; // Used in PostFromDash
	public static long lastRunTime;
}
