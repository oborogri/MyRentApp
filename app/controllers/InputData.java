package controllers;

import play.*;
import play.mvc.*;
import utils.LatLng;

import java.util.*;

import models.*;
import utils.LatLng;

public class InputData extends Controller {

    public static void index() {
        render();
    }

    public static void datacapture(String residenceType, String rented, int rent, int numberBedrooms, String geolocation) {
    	
    	ArrayList<Residence> residences = new ArrayList<Residence>();
    	
    	String userId = session.get("logged_in_userid");
		User tenant = User.findById(Long.parseLong(userId));
		
    	Residence residence = new Residence (tenant, residenceType, rented, rent, numberBedrooms, geolocation);
		residence.save();
    	residences.add(residence);
    	
    	Logger.info("Geolocation " + geolocation + " Tenant: " + tenant);
        index();
    }
}