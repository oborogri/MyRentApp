package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Welcome extends Controller {

	/**
	 * Renders welcome start page
	 */
	public static void index() {

		//clears any open sessions 
		session.clear();
		
		//checks if any user logged in
		Landlord landlord = Landlords.getCurrentLandlord();
		Tenant tenant = Tenants.getCurrentTenant();
		
		Logger.info("Current landlord: " + landlord);
		Logger.info("Current tenant: " + tenant);
		
		render();
	}
	
	/**
	 * Logs out current user
	 */
	public static void logout() {

		session.clear();
		Welcome.index();
	}

}