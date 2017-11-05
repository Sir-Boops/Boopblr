package me.boops.nonapi.functions;

import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import me.boops.cache.Cache;
import me.boops.config.Config;
import me.boops.logger.Logger;

public class GetAllNotes {
	
	public GetAllNotes(String blogName, long blogID) throws Exception {
		
		// Load the config
		Config config = new Config();
		Logger log = new Logger();
		
		boolean done = false;
		int reblogsFound = 0;
		URL nextURL = new URL("https://www.tumblr.com/svc/tumblelog/" + blogName + "/" + blogID + "/notes?mode=all");
		
		while(!done && (config.getNoteDepth() >= reblogsFound)) {
			
			log.Log("Loading notes from " + nextURL, true);
			
			JSONObject notes = new JSONObject(new LoadAPage().load(nextURL));
			
			// Check if there are more
			if(notes.getJSONObject("response").has("_links")) {
				long beforeTimeStamp = notes.getJSONObject("response").getJSONObject("_links").getJSONObject("next").getJSONObject("query_params").getLong("before_timestamp");
				nextURL = new URL("https://www.tumblr.com/svc/tumblelog/" + blogName + "/" + blogID + "/notes?mode=all&before_timestamp=" + beforeTimeStamp);
			} else {
				done = true;
			}
			
			// Scan through all the notes to
			// Find all the reblogs
			JSONArray noteList = notes.getJSONObject("response").getJSONArray("notes");
			for (int i = 0; i < noteList.length(); i++) {
				if(noteList.getJSONObject(i).getString("type").equals("reblog")) {
					Cache.reblogUsers.add(noteList.getJSONObject(i).getString("blog_name"));
					Cache.reblogIDs.add(noteList.getJSONObject(i).getLong("post_id"));
					reblogsFound++;
					if(reblogsFound >= config.getNoteDepth()) {
						i = noteList.length() + 1;
						done = true;
					}
				}
			}
			log.Log("Reading notes, Currently have " + reblogsFound, false);
		}
	}
}
