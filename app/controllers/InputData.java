package controllers;

import java.util.Date;

import models.Landlord;
import models.Residence;
import play.Logger;
import play.mvc.Controller;
import org.json.simple.JSONObject;

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

		Landlord landlord = Accounts.getCurrentUser();

		residence.dateRegistered = new Date();
		residence.addUser(landlord);
		residence.save();
		
		Logger.info("Residence data received and saved");
		Logger.info("Residence user " + residence.landlord);
	    Logger.info("Residence type: " + residence.residenceType);
	    Logger.info("Rented? " + residence.rented);
	    Logger.info("Number bathrooms: " + residence.numberBathrooms);
	    Logger.info("Residence area: " + residence.area);
	    
		index();
	    
	   /* JSONObject obj = new JSONObject();
	    String value = "Congratulations. You have successfully registered your " + residence.residenceType +".";
	    obj.put("inputdata", value);
	    renderJSON(obj);*/
	}
}