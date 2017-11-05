package me.boops;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.boops.config.Config;
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
					System.out.println(e);
				}
			}
		});
	}

	private static void MasterLoop() throws Exception {
		
	}
}
