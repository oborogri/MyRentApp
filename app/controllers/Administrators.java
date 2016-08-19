package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;

import models.Administrator;
import models.Landlord;
import models.Residence;
import models.Tenant;
import play.Logger;
import play.mvc.Controller;
import utils.DescendingRentComparator;
import utils.ResidenceLandlordComparator;

public class Administrators extends Controller {

	/**
	 * Finds all registered landlords and tenants and renders their details to
	 * admin index page
	 */
	public static void index() {

		List<Landlord> landlords = Landlord.findAll();
		List<Tenant> tenants = Tenant.findAll();

		render(landlords, tenants);
	}

	/**
	 * Renders login page
	 */
	public static void login() {

		render();
	}

	/**
	 * Renders login error page
	 */
	public static void loginerror() {

		render();
	}

	/**
	 * Finds all registered residences, sorts list in alphabetical order by
	 * landlord Renders index page with sorted residence list
	 */
	public static void charts() {

		List<Residence> residences = Residence.findAll();
		Collections.sort(residences, new ResidenceLandlordComparator());

		render(residences);
	}

	/**
	 * Renders residences data for ajax call
	 */
	public static void chart_data() {

		List<Residence> residences = Residence.findAll();

		JSONObject obj = new JSONObject();
		obj.put("residences", residences);
		renderJSON(obj);
	}

	/**
	 * Checks login details are correct and renders admin home page
	 * 
	 * @param email
	 * @param password
	 */
	public static void authenticate(String email, String password) {

		Logger.info("Attempting to authenticate with " + email + " : " + password);
		Administrator administrator = Administrator.findByEmail(email);

		// check if valid email
		if (Accounts.isValidEmailAddress(email)) {

			// authorized admin login details are e-mail: admin@witpres.ie and
			// password: secret
			if ((email.equals("admin@witpress.ie")) && (administrator.checkPassword(password) == true)) {
				Logger.info("Authentication successful");

				session.put("logged_in_userid", administrator.id);
				session.put("logged_status", "logged_in");
				index();

			} else {
				Logger.info("Authentication failed");
				loginerror();
			}
		} else {
			loginerror();
		}
	}

	/**
	 * Facilitates deleting a landlord and affiliated residences from the list
	 * updates the markers on residences map
	 * 
	 * @param email_landlord
	 */
	public static void deletelandlord(String email_landlord) {

		// find and delete landlord with given email
		Landlord landlord = Landlord.findByEmail(email_landlord);
		landlord.delete();

		// checks remaining landlords
		List<Landlord> landlordsAll = Landlord.findAll();
		Logger.info("Landlords list: " + landlordsAll);

		geolocations();

		List<Tenant> tenants = Tenant.findAll();

		// render json object to the page
		JSONObject obj = new JSONObject();
		obj.put("landlord deleted", landlord.email);
		obj.put("tenants", tenants);
		renderJSON(obj);

	}

	/**
	 * Facilitates deleting a tenant from registered tenants list updates
	 * markers on residences map
	 * 
	 * @param email_tenant
	 */
	public static void deletetenant(String email_tenant) {

		// find and delete tenant with given email
		Tenant tenant = Tenant.findByEmail(email_tenant);

		if (tenant != null) {

			tenant.delete();

			// check remaining tenants
			List<Tenant> tenantsAll = Tenant.findAll();
			Logger.info("Tenants list: " + tenantsAll);

			geolocations();

			JSONObject obj = new JSONObject();
			obj.put("tenant deleted", tenant.email);
			renderJSON(obj);
		}
		index();
	}

	/**
	 * Finds all registered residences and renders residences details to the
	 * html page
	 */
	public static void geolocations() {

		List<Residence> residences = Residence.findAll();

		List<List<String>> geolocations = new ArrayList<List<String>>();

		for (Residence r : residences) {

			geolocations.add(0, Arrays.asList(r.eircode, String.valueOf(r.getGeolocation().getLatitude()),
					String.valueOf(r.getGeolocation().getLongitude()), Residence.getTenant(r)));
		}
		// render residences data to the page as JSON object
		renderJSON(geolocations);
	}

	/**
	 * Checks logged in administrator
	 * 
	 * @return current loggedIn administrator
	 */
	public static Administrator getCurrentAdministrator() {

		Administrator administrator = null;

		if (session.contains("logged_in_userid")) {
			String userId = session.get("logged_in_userid");
			administrator = Administrator.findById(Long.parseLong(userId));
		}
		return administrator;
	}
}
