package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Landlord;
import models.Residence;
import play.Logger;
import play.mvc.Before;
import play.mvc.Controller;
import utils.Circle;
import utils.Geodistance;
import utils.LatLng;

public class Report extends Controller {

	/**
	 * This method executed before each action call in the controller. Checks
	 * that a user has logged in. If no user logged in the user is presented
	 * with the log in screen.
	 */
	@Before
	static void checkAuthentification() {
		if (session.contains("logged_in_landlordid") == false)
			Welcome.index();
	}

	/**
	 * Renders the report index view html template This presents a map and
	 * resizable circle to indicate a search area for residences
	 */
	public static void index() {
		render();
	}

	/**
	 * Generates a Report instance relating to all residences within circle
	 * 
	 * @param radius
	 *            The radius (metres) of the search area
	 * @param latcenter
	 *            The latitude of the centre of the search area
	 * @param lngcenter
	 *            The longtitude of the centre of the search area
	 */
	public static void generateReport(double radius, double latcenter, double lngcenter) {

		Landlord landlord = Landlords.getCurrentLandlord();

		ArrayList<Residence> residences = new ArrayList<Residence>();

		// All reported residences will fall within this circle
		Circle circle = new Circle(latcenter, lngcenter, radius);

		// Fetch all residences and filter out those within circle

		List<Residence> residencesAll = Residence.findAll();

		for (Residence res : residencesAll) {
			LatLng residenceLocation = res.getGeolocation();

			if (Geodistance.inCircle(residenceLocation, circle)) {
				residences.add(res);
			}
		}
		
		render(landlord, circle, residences);
	}

}