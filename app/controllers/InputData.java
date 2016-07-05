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
	 * Facilitates registering new residence  
	 * 
	 */

	public static void datacapture(Residence residence) {

		User user = Accounts.getCurrentUser();

		residence.addUser(user);
		residence.dateRegistered = new Date();
		residence.save();
		
		Logger.info("Residence data received and saved");
	    Logger.info("Residence type: " + residence.residenceType);
	    Logger.info("Rented? " + residence.rented);
	    
		index();
	}
}