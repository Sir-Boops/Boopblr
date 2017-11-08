package me.boops;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.boops.cache.Cache;
import me.boops.cache.CurrentBlogInfo;
import me.boops.config.Config;

public class Main {
	
	private static ExecutorService executor = Executors.newSingleThreadExecutor();

	public static void main(String[] args) throws Exception {
		
		// Load the config
		new Config();
		
		// Init the Cache with base vars
		Cache.hashCacheAge = 0;
		Cache.badPostIDsAge = 0;
		Cache.lastPost = 0;
		Cache.lastTimeStamp = 0;
		Cache.isInitalRun = true;
		
		// Start a thread for the master loop
		executor.submit(() -> {
			while(true){
				try {
					MasterLoop();
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		});
	}

	private static void MasterLoop() throws Exception {
		
		// Load current blog info
		new CurrentBlogInfo();
		
		// Clean the cache
		Cache.reblogUsers.clear();
		Cache.likeUsers.clear();
		Cache.reblogIDs.clear();
		Cache.tags.clear();
		Cache.tagList.clear();
		Cache.tagCount.clear();
		Cache.tagToAppend.clear();
		
		// Say Hello
		if(Cache.isInitalRun) {
			System.out.println("Hello, " + CurrentBlogInfo.blogTitle);
			Cache.isInitalRun = false;
		}
		
		if((System.currentTimeMillis() / 1000) >= Cache.badPostIDsAge) {
			Cache.badPostIDs.clear();
			Cache.badPostIDsAge = ((System.currentTimeMillis() / 1000) + 3600);
		}
		
		System.out.println("Current Queue count: " +  CurrentBlogInfo.queueCount);
		
		if(CurrentBlogInfo.queueCount < 200) {
			
			// Try and queue a new post
			if((System.currentTimeMillis() / 1000) > Cache.lastRunTime) {
				Cache.lastRunTime = (System.currentTimeMillis() / 1000) + 60;
				new FindNewPost();
			}
			
		} else {
			Thread.sleep(10 * 1000);
		}
	}
}
