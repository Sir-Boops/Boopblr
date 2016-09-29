package me.boops;

import me.boops.cache.Cache;
import me.boops.cache.Config;
import me.boops.cache.LoadConfig;
import me.boops.functions.FindPost;
import me.boops.functions.OnlineCheck;
import me.boops.functions.api.APIQueueCount;
import me.boops.logger.Logger;

public class Main {

	public static void main(String[] args) throws Exception {
		new Logger("Attempting To Load Config File", 0, false);
		new LoadConfig();
		new Logger("Starting Boopblr Version " + Config.version, 0, false);
		MasterLoop();
	}

	static void MasterLoop() throws Exception {

		// EndLess Loop!
		while (true) {
			if (Cache.name_fancy == null) {
				// First Run
				// So Fill In Everything!

				new OnlineCheck();
				if (OnlineCheck.isOnline) {
					new Logger("Welcome " + Cache.name_fancy + "! Please Wait While I Get Everything Ready", 0, false);
					new Logger("Updating Queue Count!", 0, false);
					new APIQueueCount();
					new Logger("Currently " + Config.queue_count + " Posts In Queue!", 0, false);
				} else {

					// Not Online
					new Logger("You Are Not Online", 2, false);
					System.exit(1);
				}
			} else {
				// If i have less then 250 Posts In Queue Add a new one every X
				// amount of time

				if (Config.queue_count < Config.queue_size) {
					new Logger("Trying To Queue Another Post", 0, false);
					new FindPost();
				} else {
					new Logger("Waiting " + Config.post_speed + " Sec Before Trying Again", 0, false);
					Thread.currentThread();
					Thread.sleep(Config.post_speed * 1000);
					new Logger("Checking Queue Count", 0, false);
					new APIQueueCount();
				}
			}
		}
	}
}
