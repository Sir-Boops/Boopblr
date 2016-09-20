package me.boops.functions;

import me.boops.functions.queuecheck.QueueCheckAnswer;
import me.boops.functions.queuecheck.QueueCheckAudio;
import me.boops.functions.queuecheck.QueueCheckChat;
import me.boops.functions.queuecheck.QueueCheckLink;
import me.boops.functions.queuecheck.QueueCheckPhoto;
import me.boops.functions.queuecheck.QueueCheckQuote;
import me.boops.functions.queuecheck.QueueCheckText;
import me.boops.functions.queuecheck.QueueCheckVideo;

public class QueueCheck {

	// If Post Is Found
	public boolean found = false;

	public QueueCheck(Long id, String blog_name, String type) throws Exception {

		// Find Out What Type Of Post We Are Checking

		// If Photo
		if (type.equals("photo")) {
			if (new QueueCheckPhoto(id, blog_name).found) {

				// Found The Post!
				found = true;
			}
		}

		// If Text
		if (type.equals("text")) {
			if (new QueueCheckText(id, blog_name).found) {

				// Found The Post!
				found = true;
			}
		}

		// If Quote
		// Need To Finish
		if (type.equals("quote")) {
			if (new QueueCheckQuote(id, blog_name).found) {

				// Found The Post!
				found = true;
			}
		}

		// If Link
		if (type.equals("link")) {
			if (new QueueCheckLink(id, blog_name).found) {

				// Found The Post!
				found = true;
			}
		}

		// If Answer
		if (type.equals("answer")) {
			if (new QueueCheckAnswer(id, blog_name).found) {

				// Found The Post!
				found = true;
			}
		}

		// If Video
		if (type.equals("video")) {
			if (new QueueCheckVideo(id, blog_name).found) {

				// Found The Post!
				found = true;
			}
		}

		// If Audio
		if (type.equals("audio")) {
			if (new QueueCheckAudio(id, blog_name).found) {

				// Found The Post!
				found = true;
			}
		}

		// If Audio
		if (type.equals("chat")) {
			if (new QueueCheckChat(id, blog_name).found) {

				// Found The Post!
				found = true;
			}
		}

	}

}
