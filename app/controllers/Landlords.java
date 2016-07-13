package controllers;

import java.util.Date;
import java.util.List;

import models.Landlord;
import models.Residence;
import play.Logger;
import play.mvc.Controller;

public class Landlords extends Controller {

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
	 * Registers new user with details entered on sign up page 
	 * Displays error message if user already registered
	 * 
	 * @param user
	 */
	public static void register(Landlord landlord, boolean terms) {

		List<Landlord> landlords = Landlord.findAll();

		for (Landlord a : landlords) {
			if (equalLandlord(landlord, a)) {
				Logger.info("Error - landlord " + landlord.email + " already registered!");
				signuperror();
			}
		}
		if (Accounts.isValidEmailAddress(landlord.email) && (terms != false)) {
			landlord.save();
			Logger.info("New landlord details: " + landlord.firstName + " " + landlord.lastName + " " + landlord.email
					+ " " + landlord.password);
			login();

		} else {
			Logger.info("Error - could not register landlord: " + landlord.email + " Please check your details!");
			signuperror();
		}
	}

	/**
	 * Checks login details are correct and renders user home page
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
	 * Compares two users based on their e-mails
	 * 
	 * @param User
	 *            a
	 * @param User
	 *            b
	 * 
	 * @return true if two user e-mails are the same
	 */
	private static boolean equalLandlord(Landlord a, Landlord b) {
		return (a.email.equals(b.email));
	}

	/**
	 * Checks logged in userId
	 * 
	 * @return String currentlandlord
	 */
	public static Landlord getCurrentLandlord() {
		Landlord landlord = null;
		if (session.contains("logged_in_userid")) {
			String userId = session.get("logged_in_userid");
			landlord = Landlord.findById(Long.parseLong(userId));
		}
		return landlord;
	}
}
