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

		render();

	}

	/**
	 * Facilitates registering new residence details
	 * 
	 * @param residence
	 */
	public static void datacapture(Residence residence) {

		Landlord landlord = Landlords.getCurrentLandlord();
		List<Residence> residences = new ArrayList<Residence>();

		//new residence has assigned a time stamp and registering landlord 
		residence.dateRegistered = new Date();
		residence.addLandlord(landlord);
		
		//saves residence model passed from the html page
		residence.save();
		
		//adds new residence to the residences list
		residences.add(residence);
		
		//saves landlord that registered the new residence
		landlord.save();

		Logger.info("Residence data received and saved");
		Logger.info("Registered by: " + residence.landlord);
		Logger.info("Residence type: " + residence.residenceType);
		Logger.info("Tenant: " + residence.tenant);
		Logger.info("Number bathrooms: " + residence.numberBathrooms);
		Logger.info("Residence area: " + residence.area);

		Landlords.index();
	}
}
