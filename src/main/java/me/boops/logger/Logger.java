package me.boops.logger;

public class Logger {
	
	public Logger(String message, int type){
		
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
