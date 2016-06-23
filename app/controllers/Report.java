package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Residence;
import models.User;
import play.Logger;
import play.mvc.Before;
import play.mvc.Controller;
import utils.Circle;
import utils.Geodistance;
import utils.LatLng;

public class Report extends Controller {

	/**
	 * Renders report index page 
	 */
    public static void index() {
        render();
    }
    
    /**
     * Generates a Report instance relating to all residences within circle
     * 
     * @param radius    The radius (metres) of the search area
     * @param latcenter The latitude of the centre of the search area
     * @param lngcenter The longtitude of the centre of the search area
     */
    public static void generateReport(double radius, double latcenter, double lngcenter) {
    	
    	String userId = session.get("logged_in_userid");
		User user = User.findById(Long.parseLong(userId));
		
		ArrayList<Residence> residences = new ArrayList<Residence>();
		
		// All reported residences will fall within this circle
	    Circle circle = new Circle(latcenter, lngcenter, radius);
	    
	    // Fetch all residences and filter out those within circle
	    
	    List<Residence> residencesAll = Residence.findAll();
	    
	    for (Residence res : residencesAll)
	    {
	      LatLng residenceLocation = res.getGeolocation();
	      
	      if (Geodistance.inCircle(residenceLocation, circle))
	      {
	        residences.add(res);
	      }
	      
	    }
	    
	    render(user, circle, residences);		
    }   
    
}