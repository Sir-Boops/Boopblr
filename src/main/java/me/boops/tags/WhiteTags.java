package me.boops.tags;

import java.util.List;

import me.boops.cache.Config;

public class WhiteTags {
	
	// Check if the tags contain spefic tags
	public boolean Check(List<String> tag_list) {

		for (int runs = 0; tag_list.size() > runs; runs++) {

			// Check if it's a one word tag or not
			if (tag_list.get(runs).contains(" ")) {

				// Split The Tags
				String[] sub_tags = tag_list.get(runs).split(" ");

				// Check Each Tag One By One
				for (int subruns = 0; sub_tags.length>subruns; subruns++) {

					// Check if it's a match
					if (Config.whitelisted_tags.contains(sub_tags[subruns].toLowerCase())) {
						return true;
					}
				}
			} else {

				// It's just a one word tag
				if (Config.whitelisted_tags.contains(tag_list.get(runs).toLowerCase())) {
					return true;
				}

			}

		}

		return false;
	}

}
