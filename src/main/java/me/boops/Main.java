package me.boops;

import me.boops.cache.Config;
import me.boops.functions.FindPost;
import me.boops.functions.OnlineCheck;
import me.boops.logger.Logger;
import pw.frgl.jumblr.BlogInfo;

public class Main {

	public static void main(String[] args) throws Exception {
		Config Conf = new Config();
		Logger logger = new Logger();
		logger.Log("Starting Boopblr Version " + Conf.getVersion(), 0, false);
		MasterLoop();
	}

	static void MasterLoop() throws Exception {
		
		//Define needed classes
		OnlineCheck onlineCheck = new OnlineCheck();
		Config Conf = new Config();
		Logger logger = new Logger();
		BlogInfo blog = new BlogInfo(Conf.getCustomerKey(), Conf.getCustomerSecret(), Conf.getToken(), Conf.getTokenSecret());
		
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
					new FindPost();
				} else {
					logger.Log("Waiting " + Conf.getPostSpeed() + " Sec Before Trying Again", 0, false);
					Thread.currentThread();
					Thread.sleep(Conf.getPostSpeed() * 1000);
					logger.Log("Checking Queue Count", 0, false);
					blog.getBlog(Conf.getBlogName());
					logger.Log("Currently " + blog.getQueueCount() + " Posts In Queue!", 0, false);
				}
			}
		}
	}
}
