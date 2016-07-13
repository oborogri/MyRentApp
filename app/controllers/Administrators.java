package controllers;

import java.util.Date;
import java.util.List;

import models.Landlord;
import models.Residence;
import models.Administrator;
import play.Logger;
import play.mvc.Controller;

public class Administrators extends Controller {

	/**
	 * Renders signup page
	 */
	public static void signup() {
		render();
	}

	/**
	 * Renders signup error page
	 */
	public static void signuperror() {
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
	 * Registers new admin with details entered on sign up page 
	 * Displays error message if admin already registered
	 * 
	 * @param administrator
	 */
	public static void register(Administrator administrator, boolean terms) {

		List<Administrator> admins = Administrator.findAll();

		for (Administrator a : admins) {
			if (equalAdministrator(administrator, a)) {
				Logger.info("Error - administrator " + administrator.email + " already registered!");
				signuperror();
			}
		}
		if (Accounts.isValidEmailAddress(administrator.email) && (terms != false)) {
			administrator.save();
			Logger.info("New administrator details: " + administrator.firstName + " " + administrator.lastName + " " + administrator.email
					+ " " + administrator.password);
			Welcome.index();

		} else {
			Logger.info("Error - could not register administrator: " + administrator.email + " Please check your details!");
			signuperror();
		}
	}

	/**
	 * Checks login details are correct and renders admin home page
	 * 
	 * @param email
	 * @param password
	 */
	public static void authenticate(String email, String password) {
		Logger.info("Attempting to authenticate landlord with " + email + " : " + password);
		Landlord landlord = Landlord.findByEmail(email);

		if ((landlord != null) && (landlord.checkPassword(password) == true)) {
			Logger.info("Authentication successful");

			session.put("logged_in_userid", landlord.id);
			session.put("logged_status", "logged_in");
			InputData.index();

		} else {
			Logger.info("Authentication failed");
			loginerror();
		}
	}

	/**
	 * Compares two admins based on their e-mails
	 * 
	 * @param Administrator
	 *            a
	 * @param Administrator
	 *            b
	 * 
	 * @return true if two admins e-mails are the same
	 */
	private static boolean equalAdministrator(Administrator a, Administrator b) {
		return (a.email.equals(b.email));
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
