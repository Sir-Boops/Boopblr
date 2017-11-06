package me.boops.cache;

import java.util.ArrayList;
import java.util.List;

public class Cache {
	
	public static boolean isInitalRun;
	public static String blogURL;
	
	public static List<String> reblogUsers = new ArrayList<String>();
	public static List<Long> reblogIDs = new ArrayList<Long>();
	
	public static List<String> tags  = new ArrayList<String>();
	
	public static List<String> tagList = new ArrayList<String>();
	public static List<Integer> tagCount = new ArrayList<Integer>();
	public static List<String> tagToAppend = new ArrayList<String>();
	
	public static List<String> hashList = new ArrayList<String>();
	public static long hashCacheAge;
	
	public static List<Long> badPostIDs = new ArrayList<Long>();
	public static long badPostIDsAge;
}
