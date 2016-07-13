package controllers;

import java.util.Date;
import java.util.List;

import models.Tenant;
import models.Residence;
import play.Logger;
import play.mvc.Controller;

public class Tenants extends Controller {

	/**
	 * Renders signup page
	 */
	public static void signup() {
		render();
	}

	/**
	 * Renders signup error page
	 */
	public static void signuperror() {
		render();
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
	 * Registers new user with details entered on sign up page Displays error
	 * message if user already registered and if user not USA citizen
	 * 
	 * @param user
	 */
	public static void register(Tenant tenant, boolean terms) {

		List<Tenant> tenants = Tenant.findAll();

		for (Tenant a : tenants) {
			if (equalTenant(tenant, a)) {
				Logger.info("Error - user " + tenant.email + " already registered!");
				signuperror();
			}
		}
		if (Accounts.isValidEmailAddress(tenant.email) && (terms != false)) {
			tenant.save();
			Logger.info("New member details: " + tenant.firstName + " " + tenant.lastName + " " + tenant.email
					+ " " + tenant.password);
			Welcome.index();

		} else {
			Logger.info("Error - user " + tenant.email + " not registered! Please check your details!");
			signuperror();
		}
	}

	/**
	 * Checks login details are correct and renders user home page
	 * 
	 * @param email
	 * @param password
	 */
	public static void authenticate(String email, String password) {
		Logger.info("Attempting to authenticate with " + email + " : " + password);
		Tenant tenant = Tenant.findByEmail(email);

		if ((tenant != null) && (tenant.checkPassword(password) == true)) {
			Logger.info("Authentication successful");

			session.put("logged_in_userid", tenant.id);
			session.put("logged_status", "logged_in");
			InputData.index();

		} else {
			Logger.info("Authentication failed");
			loginerror();
		}
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
	 * Checks logged in userId
	 * 
	 * @return String currenttenant
	 */
	public static Tenant getCurrentTenant() {
		Tenant tenant = null;
		if (session.contains("logged_in_userid")) {
			String userId = session.get("logged_in_userid");
			tenant = Tenant.findById(Long.parseLong(userId));
		}
		return tenant;
	}
}
