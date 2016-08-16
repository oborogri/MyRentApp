package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import models.Administrator;
import models.Landlord;
import models.Residence;
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
	 * Finds all registered residences and renders residences details to the html page  
	 */
	public static void geolocations() {
		
		List<Residence> residences = Residence.findAll();

		List<List<String>> geolocations = new ArrayList<List<String>>();

		for (Residence r : residences) {

			geolocations.add(0, Arrays.asList(r.eircode, String.valueOf(r.getGeolocation().getLatitude()),
					String.valueOf(r.getGeolocation().getLongitude()), Residence.getTenant(r)));
			}
		
		Logger.info("List of residences: " + geolocations);
		renderJSON(geolocations);
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
