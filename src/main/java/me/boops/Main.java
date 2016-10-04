package me.boops;

import me.boops.cache.Config;
import me.boops.functions.FindPost;
import me.boops.functions.OnlineCheck;
import me.boops.functions.api.APIQueueCount;
import me.boops.logger.Logger;

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
		APIQueueCount APIQueue = new APIQueueCount();
		Config Conf = new Config();
		Logger logger = new Logger();

		// EndLess Loop!
		while (true) {
			if (onlineCheck.getFancyName() == null) {
				// First Run
				// So Fill In Everything!
				onlineCheck.Check();
				if (onlineCheck.isOnline()) {
					//Update Queue Count
					APIQueue.Count();
					logger.Log("Welcome " + onlineCheck.getFancyName() + "! Please Wait While I Get Everything Ready", 0, false);
					logger.Log("Updating Queue Count!", 0, false);
					logger.Log("Currently " + APIQueue.getQueueCount() + " Posts In Queue!", 0, false);
				} else {

					// Not Online
					logger.Log("You Are Not Online", 2, false);
					System.exit(1);
				}
			} else {
				// If i have less then 250 Posts In Queue Add a new one every X
				// amount of time

				if (APIQueue.getQueueCount() < Conf.getQueueSize()) {
					logger.Log("Trying To Queue Another Post", 0, false);
					new FindPost();
					APIQueue.Count();
				} else {
					logger.Log("Waiting " + Conf.getPostSpeed() + " Sec Before Trying Again", 0, false);
					Thread.currentThread();
					Thread.sleep(Conf.getPostSpeed() * 1000);
					logger.Log("Checking Queue Count", 0, false);
					APIQueue.Count();
					logger.Log("Currently " + APIQueue.getQueueCount() + " Posts In Queue!", 0, false);
				}
			}
		}
	}
}
