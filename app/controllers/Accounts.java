package controllers;

import play.*;
import play.mvc.*;

import java.util.List;

import java.util.*;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import models.*;

public class Accounts extends Controller {

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
	 * Logs out current user
	 */
	public static void logout() {

		session.clear();
		Welcome.index();
	}

	/**
	 * Registers new user with details entered on sign up page Displays error
	 * message if user already registered and if user not USA citizen
	 * 
	 * @param user
	 */
	public static void register(Landlord landlord, boolean terms) {

		List<Landlord> landlords = Landlord.findAll();

		for (Landlord a : landlords) {
			if (equalLandlord(landlord, a)) {
				Logger.info("Error - user " + landlord.email + " already registered!");
				signuperror();
			}
		}
		if (isValidEmailAddress(landlord.email) && (terms != false)) {
			landlord.save();
			Logger.info("New member details: " + landlord.firstName + " " + landlord.lastName + " " + landlord.email
					+ " " + landlord.password);
			Welcome.index();

		} else {
			Logger.info("Error - user " + landlord.email + " not registered! Please check your details!");
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
		Logger.info("Attempting to authenticate with " + email + " : " + password);
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
	 * @return String currentuser
	 */
	public static Landlord getCurrentUser() {
		Landlord landlord = null;
		if (session.contains("logged_in_userid")) {
			String userId = session.get("logged_in_userid");
			landlord = Landlord.findById(Long.parseLong(userId));
		}
		return landlord;
	}

	/**
	 * Checks valid e-mail format
	 * 
	 * @param email
	 * @return true if e-mail not null and is a valid format
	 */
	public static boolean isValidEmailAddress(String email) {

		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}
}