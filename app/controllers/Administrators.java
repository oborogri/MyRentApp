package controllers;

import java.util.Date;
import java.util.List;

import models.Administrator;
import models.Landlord;
import play.Logger;
import play.mvc.Controller;

public class Administrators extends Controller {

	public static void index() {
		render();

	}

	/**
	 * Renders login page
	 */
	public static void login() {

		render();
	}

	/**
	 * Renders login error page
	 */
	public static void loginerror() {

		render();
	}

	/**
	 * Checks login details are correct and renders admin home page
	 * 
	 * @param email
	 * @param password
	 */
	public static void authenticate(String email, String password) {
		Logger.info("Attempting to authenticate with " + email + " : " + password);
		Administrator administrator = Administrator.findByEmail(email);
		if (Accounts.isValidEmailAddress(email)) {

			if ((email.equals("admin@witpress.ie")) && (administrator.checkPassword(password) == true)) {
				Logger.info("Authentication successful");

				session.put("logged_in_userid", administrator.id);
				session.put("logged_status", "logged_in");
				index();

			} else {
				Logger.info("Authentication failed");
				loginerror();
			}
		} else {
			loginerror();
		}
	}

	/**
	 * Checks logged in userId
	 * 
	 * @return current administrator
	 */
	public static Administrator getCurrentAdministrator() {
		Administrator administrator = null;
		if (session.contains("logged_in_userid")) {
			String userId = session.get("logged_in_userid");
			administrator = Administrator.findById(Long.parseLong(userId));
		}
		return administrator;
	}
}
