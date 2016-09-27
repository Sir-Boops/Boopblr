package me.boops.tags;

import java.util.List;

import me.boops.logger.Logger;

public class BaseTagsCheck {
	
	public boolean found = false;
	
	public BaseTagsCheck(String[] check_list, List<String> tags_to_Check) {
		
		// Check The Tags
		int runs = 0;
		while (runs < tags_to_Check.size() && !found) {

			// Check for > 1 word tag
			if (tags_to_Check.get(runs).toString().contains(" ")) {

				// Tags
				String[] sub_tags = tags_to_Check.get(runs).toString().split(" ");

				// Run the Check For This Word
				int sub_runs = 0;
				while (sub_runs < sub_tags.length && !found) {

					// Check Agenst List
					int sub_sub_runs = 0;
					while (sub_sub_runs < check_list.length && !found) {

						if (sub_tags[sub_runs].toLowerCase().equals(check_list[sub_sub_runs])) {

							// Found The Post!
							found = true;
							new Logger(sub_tags[sub_runs] + " : " + check_list[sub_sub_runs], 0);
							return;
						}

						// Didn't Find It!
						new Logger(sub_tags[sub_runs] + " : " + check_list[sub_sub_runs], 0);
						sub_sub_runs++;
					}

					// Done This Check
					sub_runs++;
				}

				// Done This Check
				runs++;
			} else {

				// Tags
				String sub_tag = tags_to_Check.get(runs).toString();

				// Check The Tag
				int sub_run = 0;
				while (sub_run < check_list.length && !found) {

					if (sub_tag.toLowerCase().equals(check_list[sub_run])) {

						// Found The Tag
						found = true;
						sub_run++;
					}

					// Didn't Find It
					new Logger(sub_tag + " : " + check_list[sub_run], 0);
					sub_run++;
				}

				// Done This Check
				runs++;
			}
		}
		
	}
}
