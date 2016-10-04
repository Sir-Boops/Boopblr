package me.boops;

import me.boops.cache.Config;
import me.boops.functions.FindPost;
import me.boops.functions.OnlineCheck;
import me.boops.functions.api.APIQueueCount;
import me.boops.logger.Logger;

public class Main {

	public static void main(String[] args) throws Exception {
		
		//Define needed classes
		Config Conf = new Config();
		new Logger("Starting Boopblr Version " + Conf.getVersion(), 0, false);
		MasterLoop();
	}

	static void MasterLoop() throws Exception {
		
		//Define needed classes
		OnlineCheck onlineCheck = new OnlineCheck();
		Config Conf = new Config();
		APIQueueCount APIQueue = new APIQueueCount();

		// EndLess Loop!
		while (true) {
			if (onlineCheck.getFancyName() == null) {
				// First Run
				// So Fill In Everything!
				onlineCheck.Check();
				if (onlineCheck.isOnline()) {
					//Update Queue Count
					APIQueue.Count();
					new Logger("Welcome " + onlineCheck.getFancyName() + "! Please Wait While I Get Everything Ready", 0, false);
					new Logger("Updating Queue Count!", 0, false);
					new Logger("Currently " + APIQueue.getQueueCount() + " Posts In Queue!", 0, false);
				} else {

					// Not Online
					new Logger("You Are Not Online", 2, false);
					System.exit(1);
				}
			} else {
				// If i have less then 250 Posts In Queue Add a new one every X
				// amount of time

				if (APIQueue.getQueueCount() < Conf.getQueueSize()) {
					new Logger("Trying To Queue Another Post", 0, false);
					new FindPost();
				} else {
					new Logger("Waiting " + Conf.getPostSpeed() + " Sec Before Trying Again", 0, false);
					Thread.currentThread();
					Thread.sleep(Conf.getPostSpeed() * 1000);
					new Logger("Checking Queue Count", 0, false);
					APIQueue.Count();
				}
			}
		}
	}
}
