package controllers;

import java.util.Date;

import models.Landlord;
import models.Residence;
import play.Logger;
import play.mvc.Controller;

public class Landlords extends Controller {

	/**
	 * Renders index page
	 */
	public static void index() {

		if (session.get("logged_in_userid") == null) {

			Logger.info("No user logged in");
			Accounts.login();
		} else {

			Landlord landlord = Accounts.getCurrentUser();
			render(landlord);
		}
	}

	/**
	 * Updates profile information for landlord
	 * 
	 * @param firstName
	 * @param lastName
	 * @param address
	 */
	public static void editProfile(String firstName, String lastName, String address) {

		Landlord landlord = Accounts.getCurrentUser();

		landlord.firstName = firstName;
		landlord.lastName = lastName;
		landlord.address = address;

		landlord.save();

		Logger.info("Landlord new details: firstName: " + landlord.firstName + "; lastName: " + landlord.lastName
				+ "; address: " + landlord.address);
		InputData.index();
	}

}
