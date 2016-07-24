package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Tenant;
import models.Landlord;
import models.Residence;
import play.Logger;
import play.mvc.Controller;

public class Tenants extends Controller {
	
	/**
	 * Renders tenants index page
	 */
	public static void index() {
		
		Tenant tenant = Tenants.getCurrentTenant(); 
		Residence tr = Residence.findByTenant(); 
		
		Logger.info("Tenant: " + tenant + " residence: " + tr.eircode);		
		render(tr, tenant);
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
	 * Registers new tenant with details entered on sign up page 
	 * Displays error message if tenant already registered
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
			Logger.info("New tenant details: " + tenant.firstName + " " + tenant.lastName + " " + tenant.email
					+ " " + tenant.password);
			login();

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
	
	public static void endtenancy(String existingeircode) {
		
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
