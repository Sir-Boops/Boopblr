package me.boops.functions;

import java.util.List;

import me.boops.config.Config;
import me.boops.logger.Logger;

public class CheckForPastReblog {
	
	public boolean scan(List<String> users) {
		
		// Load the config and log
		Config config = new Config();
		Logger log = new Logger();
		
		boolean ans = false;
		
		for (int i=0; i < users.size() && i < config.getScanNotesDepth() && !ans; i++) {
			log.Log("Checking " + config.getBlogName() + " : " + users.get(i), true);
			if(users.get(i).equalsIgnoreCase(config.getBlogName())) {
				ans = true;
			}
		}
		return ans;
	}	
}
