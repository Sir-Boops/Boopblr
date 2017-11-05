package me.boops.logger;

import me.boops.config.Config;

public class Logger {
	
	public void Log(String message, boolean debug){
		
		//Define needed classes
		Config Conf = new Config();
		
		
		// If debug output is enabled display debug output else don't
		if(debug && !Conf.getDebugOutput()){
			return;
		} else {
			
			//0 Is Normal Output
			System.out.println("[Boopblr - Message]: " + message);
		}
	}
}
