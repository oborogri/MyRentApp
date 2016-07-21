package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Landlord;
import models.Residence;
import play.Logger;
import play.mvc.Controller;
import org.json.simple.JSONObject;

public class InputData extends Controller {

	/**
	 * Renders InputData page if user not logged in - redirects to login page
	 */

	public static void index() {

		if (session.get("logged_in_userid") != null) {

			Logger.info("Logged in userId: " + session.get("logged_in_userid"));
			render();

		} else {
			Logger.info("No user logged in");
			Welcome.index();
		}

	}

	/**
	 * Facilitates registering new residence details
	 * 
	 * @param residence
	 */

	public static void datacapture(Residence residence) {

		Landlord landlord = Landlords.getCurrentLandlord();
		List<Residence> residences = new ArrayList<Residence>();

		residence.dateRegistered = new Date();
		residence.addLandlord(landlord);
		residence.save();
		residences.add(residence);
		landlord.save();

		Logger.info("Residence data received and saved");
		Logger.info("Registered by: " + residence.landlord);
		Logger.info("Residence type: " + residence.residenceType);
		Logger.info("Rented? " + residence.rented);
		Logger.info("Number bathrooms: " + residence.numberBathrooms);
		Logger.info("Residence area: " + residence.area);

		Landlords.index();
	}

}
