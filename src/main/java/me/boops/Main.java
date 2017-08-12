package me.boops;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.boops.cache.Cache;
import me.boops.cache.Config;
import me.boops.functions.FindPost;
import me.boops.functions.OnlineCheck;
import me.boops.functions.UpdateQueueCache;
import me.boops.jumblr.BlogInfo;
import me.boops.logger.Logger;

public class Main {
	
	private static ExecutorService executor = Executors.newSingleThreadExecutor();

	public static void main(String[] args) {
		Config Conf = new Config();
		Logger logger = new Logger();
		logger.Log("Starting Boopblr Version " + Conf.getVersion(), 0, false);
		
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

	private static void MasterLoop() {
		
		System.out.println(1/0);

		// Define needed classes
		OnlineCheck onlineCheck = new OnlineCheck();
		Config Conf = new Config();
		Logger logger = new Logger();
		BlogInfo blog = new BlogInfo(Conf.getCustomerKey(), Conf.getCustomerSecret(), Conf.getToken(),
				Conf.getTokenSecret());
		UpdateQueueCache cache = new UpdateQueueCache();

		// QueueCache Life Time
		int QueueCacheLife = 0;

		// EndLess Loop!
		while (true) {
			if (onlineCheck.getFancyName() == null) {
				// First Run
				// So Fill In Everything!
				onlineCheck.Check();
				if (onlineCheck.isOnline()) {
					// Update Queue Count
					blog.getBlog(Conf.getBlogName());
					logger.Log("Welcome " + onlineCheck.getFancyName() + "! Please Wait While I Get Everything Ready",
							0, false);
					logger.Log("Updating Queue Count!", 0, false);
					logger.Log("Currently " + blog.getQueueCount() + " Posts In Queue!", 0, false);
					logger.Log("Building queue hash cache", 0, false);
					cache.getQueueCache();
				} else {

					// Not Online
					logger.Log("You Are Not Online", 2, false);
					System.exit(1);
				}
			} else {
				// If i have less then 250 Posts In Queue Add a new one every X
				// amount of time
				blog.getBlog(Conf.getBlogName());

				if (blog.getQueueCount() < Conf.getQueueSize()) {
					logger.Log("Trying To Queue Another Post", 0, false);
					QueueCacheLife++;
					if (QueueCacheLife >= Conf.getHashLife()) {
						logger.Log("Rebuilding queue hash cache", 0, false);
						Cache.QueueHashes.clear();
						cache.getQueueCache();
						QueueCacheLife = 0;
					}
					CleanIDs();
					new FindPost();
				} else {
					logger.Log("Waiting " + Conf.getPostSpeed() + " Sec Before Trying Again", 0, false);
					Thread.currentThread();
					try {
						Thread.sleep(Conf.getPostSpeed() * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					logger.Log("Checking Queue Count", 0, false);
					blog.getBlog(Conf.getBlogName());
					logger.Log("Currently " + blog.getQueueCount() + " Posts In Queue!", 0, false);
				}
			}
		}
	}

	private static void CleanIDs() {

		Config Conf = new Config();
		Logger logger = new Logger();

		// Check if we should try and clear out the bad ID Cache

		if (Conf.getShouldCleanBadIDs()) {
			// Check if it's been cleaned yet
			if (Cache.lastClean != null) {

				// If it has been longer then X time clean the bad post ID cache
				if (System.currentTimeMillis() > (Cache.lastClean + (Conf.getBadIDCleanTime() * 1000))) {

					// It has been longer then the requested time so clear
					// The cache now
					Cache.BadPostIDs.clear();
					logger.Log("Cleared The Bad Post ID Cache!", 0, false);
				}

			} else {

				// Set the time as now and do nonthing else
				Cache.lastClean = System.currentTimeMillis();

			}
		}
	}
}
