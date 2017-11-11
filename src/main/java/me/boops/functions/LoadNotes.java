package me.boops.functions;

import org.json.JSONArray;
import org.json.JSONObject;

import me.boops.base.GetURLKey;
import me.boops.base.GetURLRaw;
import me.boops.cache.Cache;

public class LoadNotes {

	public LoadNotes(String blogName, long postID) throws Exception {

		String[] titles = { "mode" };
		String[] args = { "all" };
		String rawNotes = new GetURLRaw().connect("https://www.tumblr.com/svc/tumblelog/" + blogName + "/" + postID + "/notes", titles, args);
		int totalNotes = new JSONObject(rawNotes).getJSONObject("response").getInt("total_notes");
		
		// Set how many notes to load
		int toLoad = 700;
		
		// Make sure we have enough notes to load
		if(totalNotes < toLoad) {
			toLoad = totalNotes;
		}
		
		// Start the loop
		load(toLoad, blogName, postID);
	}
	
	private void load(int toLoad, String blogName, long postID) throws Exception {
		
		JSONArray allNotes = new JSONArray();
		
		long timeStamp = 0;
		int readNotes = 0;
		while(readNotes < toLoad) {
			
			String rawNotes;
			
			// Check if we are loading before a timestamp
			// And load the rawNotes
			if(timeStamp > 0) {
				String[] titles = { "mode", "before_timestamp"};
				String[] args = { "all", String.valueOf(timeStamp) };
				rawNotes = new GetURLRaw().connect("https://www.tumblr.com/svc/tumblelog/" + blogName + "/" + postID + "/notes", titles, args);
			} else {
				String[] titles = { "mode" };
				String[] args = { "all" };
				rawNotes = new GetURLRaw().connect("https://www.tumblr.com/svc/tumblelog/" + blogName + "/" + postID + "/notes", titles, args);
			}
			
			// Pull the notes and the timestamp
			// From the response
			JSONArray notes = new JSONObject(rawNotes).getJSONObject("response").getJSONArray("notes");
			if(new JSONObject(rawNotes).getJSONObject("response").has("_links")) {
				timeStamp = new JSONObject(rawNotes).getJSONObject("response").getJSONObject("_links").getJSONObject("next")
						.getJSONObject("query_params").getLong("before_timestamp");
			}
			
			// Add reblogs to reblogs and ID counters and add likes to the
			// Likes counter
			for(int i = 0; i < notes.length(); i++) {
				if(notes.getJSONObject(i).getString("type").equals("reblog") || notes.getJSONObject(i).getString("type").equals("posted")) {
					allNotes.put(notes.getJSONObject(i));
				} else {
					Cache.likeUsers.add(notes.getJSONObject(i).getString("blog_name"));
				}
			}
			
			readNotes += notes.length();
		}
		
		for(int i = 0; i < allNotes.length(); i++) {
			if(allNotes.getJSONObject(i).getString("type").equals("posted")) {
				String[] titles = {"reblog_info", "id"};
				String[] args = {"true", String.valueOf(allNotes.getJSONObject(i - 1).getLong("post_id"))};
				JSONObject roots = new JSONObject(new GetURLKey().connect("https://api.tumblr.com/v2/blog/" + allNotes.getJSONObject(i - 1).getString("blog_name") + "/posts", titles, args));
				Cache.reblogUsers.add(roots.getJSONObject("response").getJSONArray("posts").getJSONObject(0).getString("reblogged_from_name"));
				Cache.reblogIDs.add(roots.getJSONObject("response").getJSONArray("posts").getJSONObject(0).getLong("reblogged_root_id"));
			} else {
				Cache.reblogUsers.add(allNotes.getJSONObject(i).getString("blog_name"));
				Cache.reblogIDs.add(allNotes.getJSONObject(i).getLong("post_id"));
			}
		}
	}
}
