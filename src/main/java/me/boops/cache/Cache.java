package me.boops.cache;

import java.util.ArrayList;
import java.util.List;

public class Cache {
	
	//Queue Caching
	public static List<String> QueueHashes = new ArrayList<String>();
	public static String CurrentPostHash = "";
	
	//Bad Post ID Caching
	public static List<Long> BadPostIDs = new ArrayList<Long>();
	public static Long lastClean;
	
}
