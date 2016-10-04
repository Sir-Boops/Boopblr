package me.boops.parsers;

import me.boops.logger.Logger;

public class GetMoreNotesLink {
	
	public String new_url;
	
	public GetMoreNotesLink(String end, String blog_name, long id) throws Exception{
		
		//Define needed classes
		Logger logger = new Logger();
		
		// Get Notes Key
		boolean done = false;
		String scan_item = end;
		String notes_url = "http://" + blog_name + ".tumblr.com/notes/" + id + "/";
		int extra = ("/notes/" + id + "/").length();
		int pos = scan_item.indexOf("/notes/") + extra;
		int runs = 0;
		while (!done) {

			// Convert Char To A String
			String letter = "" + scan_item.charAt(pos + runs);

			// Check For the ?
			if (!letter.equals("'")) {

				// Still Need More
				notes_url = (notes_url + scan_item.charAt(pos + runs));
				runs++;
			} else {

				// Done
				done = true;
			}
		}
		
		//Push The New URL
		new_url = notes_url;
		logger.Log(notes_url, 0, true);
	}
}
