package me.boops.logger;

import me.boops.cache.Config;

public class Logger {
	
	public Logger(String message, int type, boolean debug){
		
		
		// If debug output is enabled display debug output else don't
		if(debug && !Config.debug_output){
			return;
		} else {
			
			//0 Is Normal Output
			if(type == 0){
				System.out.println("[Boopblr - Message]: " + message);
			}
			
			//1 Is Warning
			if(type == 1){
				System.out.println("[Boopblr - Warning]: " + message);
			}
			
			//2 Is Error
			if(type == 2){
				System.out.println("[Boopblr - Error]: " + message);
			}
			
		}
	}
	
}
