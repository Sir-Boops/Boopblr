package me.boops.logger;

import me.boops.cache.Config;

public class Logger {
	
	public Logger(String message, int type, boolean debug) throws Exception{
		
		//Define needed classes
		Config Conf = new Config();
		
		
		// If debug output is enabled display debug output else don't
		if(debug && !Conf.getDebugOutput()){
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
