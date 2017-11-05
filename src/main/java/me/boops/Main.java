package me.boops;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.boops.cache.Cache;
import me.boops.config.Config;
import me.boops.functions.OnlineCheck;
import me.boops.functions.QueueInfo;
import me.boops.logger.Logger;

public class Main {
	
	private static ExecutorService executor = Executors.newSingleThreadExecutor();

	public static void main(String[] args) {
		Config Conf = new Config();
		Logger logger = new Logger();
		Cache.hashCacheAge = 0;
		Cache.isInitalRun = true;
		logger.Log("Starting Boopblr Version " + Conf.getVersion(), false);
		
		// Start a thread for the master loop
		
		executor.submit(() -> {
			while(true){
				try {
					MasterLoop();
				} catch (Exception e){
					//System.out.println(e);
					e.printStackTrace();
				}
			}
		});
	}

	private static void MasterLoop() throws Exception {
		
		// Load the config
		Config config = new Config();
		
		// Say Hello
		if(Cache.isInitalRun) {
			new OnlineCheck().amOnline();
			Cache.isInitalRun = false;
		}
		
		// Clear out the old tags
		if(!Cache.tags.isEmpty()) {
			Cache.tags.clear();
		}
		
		// Check the current queue count to see if we need to run or not
		int queueCount = new QueueInfo().getQueueCount();
		
		if(queueCount < config.getQueueSize()) {
			
			// First make sure the queue hash cache is still good
			//new QueueHash();
			
			// Try and queue a new post
			new FindNewPost();
			
		}
		
	}
}
