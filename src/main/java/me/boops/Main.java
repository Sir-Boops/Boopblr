package me.boops;

import me.boops.cache.Cache;
import me.boops.cache.Config;
import me.boops.functions.FindPost;
import me.boops.functions.OnlineCheck;
import me.boops.functions.UpdateQueueCache;
import me.boops.logger.Logger;
import pw.frgl.jumblr.BlogInfo;

public class Main {

	public static void main(String[] args) {
		Config Conf = new Config();
		Logger logger = new Logger();
		logger.Log("Starting Boopblr Version " + Conf.getVersion(), 0, false);
		MasterLoop();
	}

	private static void MasterLoop() {
		
		//Define needed classes
		OnlineCheck onlineCheck = new OnlineCheck();
		Config Conf = new Config();
		Logger logger = new Logger();
		BlogInfo blog = new BlogInfo(Conf.getCustomerKey(), Conf.getCustomerSecret(), Conf.getToken(), Conf.getTokenSecret());
		UpdateQueueCache cache = new UpdateQueueCache();
		
		//QueueCache Life Time
		int QueueCacheLife = 0;
		
		// EndLess Loop!
		while (true) {
			if (onlineCheck.getFancyName() == null) {
				// First Run
				// So Fill In Everything!
				onlineCheck.Check();
				if (onlineCheck.isOnline()) {
					//Update Queue Count
					blog.getBlog(Conf.getBlogName());
					logger.Log("Welcome " + onlineCheck.getFancyName() + "! Please Wait While I Get Everything Ready", 0, false);
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
					if(QueueCacheLife >= Conf.getHashLife()){
						logger.Log("Rebuilding queue hash cache", 0, false);
						Cache.QueueHashes.clear();
						cache.getQueueCache();
						QueueCacheLife = 0;
					}
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
}
