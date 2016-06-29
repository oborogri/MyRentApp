package controllers;

import play.*;
import play.mvc.*;
import utils.LatLng;

import java.util.*;

import models.*;
import utils.LatLng;

public class InputData extends Controller {
	
	/**
	 * Renders InputData page 
	 * if user not logged in - redirects to login page 
	 */

	public static void index() {
		
		if (session.get("logged_in_userid") != null)  {
			
			Logger.info("Logged in userId: " + session.get("logged_in_userid"));
			render();
			
		} else {			
			Logger.info("No user logged in");
			Accounts.login();
		}		
		
	}
	
	/**
	 * Facilitates residence data capture  
	 * 
	 * @param residenceType
	 * @param rented
	 * @param rent
	 * @param numberBedrooms
	 * @param geolocation
	 */

	public static void datacapture(String residenceType, String rented, int rent, int numberBedrooms,
			String geolocation) {

		ArrayList<Residence> residences = new ArrayList<Residence>();

		String userId = session.get("logged_in_userid");
		User tenant = User.findById(Long.parseLong(userId));

		Residence residence = new Residence(tenant, residenceType, rented, rent, numberBedrooms, geolocation);
		residence.save();
		residences.add(residence);

		Logger.info("Geolocation " + geolocation + " Tenant: " + tenant);
		index();
	}
}