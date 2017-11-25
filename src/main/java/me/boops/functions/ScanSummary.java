package me.boops.functions;

import org.json.JSONObject;

import me.boops.config.Config;

public class ScanSummary {
	
	public boolean scan(JSONObject post) {
		String[] summBreak = post.getString("summary").split(" ");
		boolean ans = false;
		for(int i = 0; (i < summBreak.length) && !ans; i++) {
			for (int i2 = 0; (i2 < Config.sumBlacklist.size()) && !ans; i2++) {
				if(summBreak[i].equalsIgnoreCase(Config.sumBlacklist.get(i2))) {
					ans = true;
				}
			}		
		}
		return ans;
	}
}
