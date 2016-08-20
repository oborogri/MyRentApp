package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Landlord;
import models.Residence;
import models.Tenant;
import play.Logger;
import play.mvc.Controller;

public class Landlords extends Controller {

	/**
	 * Renders landlord index page Identifies all residences of current landlord
	 * and renders to the page
	 */
	public static void index() {

		Landlord landlord = Landlords.getCurrentLandlord();

		List<Residence> residencesAll = Residence.findAll();
		ArrayList<Residence> residences = new ArrayList<Residence>();

		// finds all residences for current landlord
		for (Residence r : residencesAll) {

			if (r.landlord.equals(Landlords.getCurrentLandlord())) {
				residences.add(r);
			}
		}
		Logger.info("Landlord " + getCurrentLandlord() + " Number residences " + residences.size());

		// render landlord details to the html page
		render(residences, landlord);
	}

	/**
	 * Renders landlord signup page
	 */
	public static void signup() {
		render();
	}

	/**
	 * Renders landlord signup error page
	 */
	public static void signuperror() {
		render();
	}

	/**
	 * Renders landlord login page
	 */
	public static void login() {

		render();
	}

	/**
	 * Renders landlord login error page
	 */
	public static void loginerror() {

		render();
	}

	/**
	 * Logs out current landlord
	 */
	public static void logout() {

		session.clear();
		Welcome.index();
	}

	/**
	 * Renders landlords edit profile page
	 */
	public static void editprofile() {

		Landlord landlord = getCurrentLandlord();

		render(landlord);
	}

	/**
	 * Renders update residence page
	 */
	public static void editresidence(String eircode_edit) {

		Residence residence = Residence.findByEircode(eircode_edit);

		// checks that residence field is selected and residence is not null
		if (residence != null) {
			render(residence);
		}
		index();
	}

	/**
	 * Registers new user with details entered on sign up page Displays error
	 * message if user already registered
	 * 
	 * @param user
	 */
	public static void register(Landlord landlord, boolean terms) {

		List<Landlord> landlords = Landlord.findAll();

		// check if landlord not already registered
		for (Landlord a : landlords) {
			if (equalLandlord(landlord, a)) {
				Logger.info("Error - landlord " + landlord.email + " already registered!");
				signuperror();
			}
		}
		// check if entered e-mail is valid format and terms box checked
		if (Accounts.isValidEmailAddress(landlord.email) && (terms != false)) {
			landlord.save();
			Logger.info("New landlord details: " + landlord.firstName + " " + landlord.lastName + " " + landlord.email
					+ " " + landlord.password);
			Administrators.index();

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

		// check if landlord exists and password is valid
		if ((landlord != null) && (landlord.checkPassword(password) == true)) {
			Logger.info("Authentication successful");

			session.put("logged_in_landlordid", landlord.id);
			session.put("logged_status", "logged_in");

			index();

		} else {
			Logger.info("Authentication failed");
			loginerror();
		}
	}

	/**
	 * Facilitates updating profile details for user landlord
	 * 
	 * @param firstName
	 * @param lastName
	 * @param address1
	 * @param address2
	 * @param city
	 * @param county
	 */
	public static void updateprofile(String firstName, String lastName, String address1, String address2, String city,
			String county) {

		Landlord landlord = getCurrentLandlord();

		landlord.firstName = firstName;
		landlord.lastName = lastName;
		landlord.address1 = address1;
		landlord.address2 = address2;
		landlord.city = city;
		landlord.county = county;

		landlord.save();

		index();
	}

	/**
	 * Facilitates deleting a residence with a specific eircode from a specific
	 * residences list
	 * 
	 * @param residence eircode ro delete
	 */
	public static void deleteresidence(String eircode_delete) {

		Landlord landlord = getCurrentLandlord();
		List<Residence> residences = Residence.findAll();

		Residence residence = Residence.findByEircode(eircode_delete);

		// find residence by e-mail and delete if exists
		if (residence != null) {
			residence.delete();

			Logger.info("Residence to be deleted " + residence);
			Logger.info("Eircode to be deleted " + eircode_delete);

			// remove residence from residences list and update landlord
			residences.remove(residence);
			landlord.save();

			index();
		}
		index();
	}

	/**
	 * Facilitates editing residence rent field only
	 * 
	 * @param eircode_edit
	 *            - eircode of residence to update
	 * @param rent
	 *            new rent amount
	 */
	public static void updateresidence(String eircode_edit, int rent) {

		Landlord landlord = getCurrentLandlord();
		Residence residence = Residence.findByEircode(eircode_edit);

		// check if residence exists and update rent field
		if (residence != null) {
			residence.rent = rent;

			// save residence update and landlord
			residence.save();
			landlord.save();

			Logger.info("Updated rent amount for residence: " + residence.eircode);
			Logger.info("Updated rent: " + rent);

			index();
		}
		index();
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
	 * Checks logged in landlord
	 * 
	 * @return String currentlandlord
	 */
	public static Landlord getCurrentLandlord() {

		Landlord landlord = null;

		if (session.contains("logged_in_landlordid")) {
			String landlordId = session.get("logged_in_landlordid");
			landlord = Landlord.findById(Long.parseLong(landlordId));
		}
		return landlord;
	}
}