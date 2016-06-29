package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Welcome extends Controller {

	/**
	 * Renders welcome start page
	 */
	public static void index() {

		session.clear();
		render();
	}

	/**
	 * Renders contact page
	 */
	public static void contact() {
		render();
	}

	/**
	 * Renders feedback page
	 */
	public static void feedback(User from) {
		render(from);
	}

	/**
	 * Facilitates capturing message data from user
	 * 
	 * @param user
	 * @param messageText
	 */
	public static void acknowledge(String messageText, String email) {
		
		ArrayList<Message> messages = new ArrayList<Message>();

		if (Accounts.isValidEmailAddress(email)) {
			
			String userId = session.get("logged_in_userid");
			User from = User.findById(Long.parseLong(userId));
			
			Message msg = new Message(from, messageText);

			msg.save();

			messages.add(msg);

			Logger.info("New message from: " + from.email);
			feedback(from);

		} else {
			contact();
		}
	}
}