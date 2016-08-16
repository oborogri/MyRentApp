package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import models.Tenant;
import models.Landlord;
import models.Residence;
import play.Logger;
import play.mvc.Controller;
import utils.Circle;
import utils.Geodistance;
import utils.LatLng;

public class Tenants extends Controller {

	/**
	 * Renders tenants index page
	 */
	public static void index() {

		Tenant tenant = Tenants.getCurrentTenant();
		Residence tr = Residence.findByTenant();

		ArrayList<Residence> vr = getVacantResidences();

		Logger.info("Tenant: " + tenant + " residence: " + tr);

		render(tenant, tr, vr);
	}

	/**
	 * Renders tenants signup page
	 */
	public static void signup() {
		render();
	}

	/**
	 * Renders tenants signup error page
	 */
	public static void signuperror() {
		render();
	}

	/**
	 * Renders tenants login page
	 */
	public static void login() {

		render();
	}

	/**
	 * Renders tenants login error page
	 */
	public static void loginerror() {

		render();
	}

	/**
	 * Logs out current tenant
	 */
	public static void logout() {

		session.clear();
		Welcome.index();
	}

	/**
	 * Registers new tenant with details entered on sign up page Displays error
	 * message if tenant already registered
	 * 
	 * @param tenant
	 */
	public static void register(Tenant tenant, boolean terms) {

		List<Tenant> tenants = Tenant.findAll();

		for (Tenant a : tenants) {
			if (equalTenant(tenant, a)) {
				Logger.info("Error - tenant " + tenant.email + " already registered!");
				signuperror();
			}
		}
		if (Accounts.isValidEmailAddress(tenant.email) && (terms != false)) {
			tenant.save();
			Logger.info("New tenant details: " + tenant.firstName + " " + tenant.lastName + " " + tenant.email + " "
					+ tenant.password);
			Administrators.index();

		} else {
			Logger.info("Error - tenant " + tenant.email + " not registered! Please check your details!");
			signuperror();
		}
	}

	/**
	 * Checks login details are correct and renders tenant home page
	 * 
	 * @param email
	 * @param password
	 */
	public static void authenticate(String email, String password) {
		Logger.info("Attempting to authenticate tenant with " + email + " : " + password);
		Tenant tenant = Tenant.findByEmail(email);

		if ((tenant != null) && (tenant.checkPassword(password) == true)) {
			Logger.info("Authentication successful");

			session.put("logged_in_tenantid", tenant.id);
			session.put("logged_status", "logged_in");

			Logger.info("Logged in tenant: " + tenant.email);

			Tenants.index();

		} else {
			Logger.info("Tenant authentication failed");
			loginerror();
		}
	}

	/**
	 * Facilitates ending tenancy at current residence
	 */
	public static void endtenancy() {

		Tenant tenant = getCurrentTenant();
		Residence residence = Residence.findByTenant();

		if (residence != null) {

			residence.tenant = null;
			residence.save();
			tenant.save();

			Logger.info("Tenant " + tenant + " tenancy at: " + residence.eircode + " terminated");
		}
		index();

	}

	/**
	 * Facilitates changing tenancy for current tenant
	 * 
	 * @param eircode_residence
	 */
	public static void changetenancy(String eircode_vacancy) {

		Tenant tenant = getCurrentTenant();
		Residence residence = Residence.findByEircode(eircode_vacancy);

		if (tenant.residence == null) {

			Logger.info("Tenant: " + tenant + " changing tenancy");
			Logger.info("New residence: " + residence);

			residence.addtenant(tenant);
			tenant.save();

			index();

		} else {
			index();
		}
	}

	/**
	 * Finds all vacant residences and returns JSON object with residence
	 * details as array
	 */
	public static void vacantresidences() {

		List<Residence> residences = Residence.findAll();

		List<List<String>> vacantresidences = new ArrayList<List<String>>();

		for (Residence r : residences) {

			if (r.tenant == null) {
				vacantresidences.add(0, Arrays.asList(r.eircode, String.valueOf(r.getGeolocation().getLatitude()),
						String.valueOf(r.getGeolocation().getLongitude()), Residence.getTenant(r)));
			}
		}
		Logger.info("Vacant residences " + vacantresidences);
		renderJSON(vacantresidences);
	}

	/**
	 * Finds all vacant residences returns ArrayList of all vacant residences
	 */
	private static ArrayList<Residence> getVacantResidences() {

		List<Residence> residences = Residence.findAll();
		ArrayList<Residence> vr = new ArrayList<Residence>();

		for (Residence r : residences) {
			if (r.tenant == null) {
				vr.add(r);
			}
		}
		Logger.info("Vacant residences: " + vr);

		return vr;
	}

	/**
	 * Generates a Report instance relating to all vacant residences within
	 * circle
	 * 
	 * @param radius
	 * @param latcenter
	 * @param lngcenter
	 */
	public static void generateReport(double radius, double latcenter, double lngcenter) {

		Tenant tenant = Tenants.getCurrentTenant();

		ArrayList<Residence> residences = new ArrayList<Residence>();

		// All reported residences will fall within this circle
		Circle circle = new Circle(latcenter, lngcenter, radius);

		// Fetch all residences and filter out those within circle

		List<Residence> residencesAll = Residence.findAll();

		for (Residence res : residencesAll) {
			if (res.tenant == null) {

				LatLng residenceLocation = res.getGeolocation();

				if (Geodistance.inCircle(residenceLocation, circle)) {
					residences.add(res);
				}
			}
		}

		render(tenant, circle, residences);
	}

	/**
	 * Compares two tenants based on their e-mails
	 * 
	 * @param Tenant
	 *            a
	 * @param Tenant
	 *            b
	 * 
	 * @return true if two tenant e-mails are the same
	 */
	private static boolean equalTenant(Tenant a, Tenant b) {
		return (a.email.equals(b.email));
	}

	/**
	 * Checks logged in tenant
	 * 
	 * @return current tenant
	 */
	public static Tenant getCurrentTenant() {
		Tenant tenant = null;
		if (session.contains("logged_in_tenantid")) {
			String tenantId = session.get("logged_in_tenantid");
			tenant = Tenant.findById(Long.parseLong(tenantId));
		}
		return tenant;
	}
}
