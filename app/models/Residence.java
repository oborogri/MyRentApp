package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import controllers.Landlords;
import controllers.Tenants;
import play.Logger;
import play.db.jpa.Model;
import utils.LatLng;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class Residence extends Model {

	public String residenceType;
	public int rent;
	public int numberBedrooms;
	public int numberBathrooms;
	public int area;
	public String geolocation;
	public String eircode;
	public Date dateRegistered;

	@ManyToOne
	public Landlord landlord;

	@OneToOne(mappedBy = "residence", cascade=CascadeType.ALL)
	public Tenant tenant;

	/**
	 * Facilitates adding landlord to a residence
	 * 
	 * @param landlord
	 */
	public void addLandlord(Landlord landlord) {
		this.landlord = landlord;
		this.save();
	}

	/**
	 * Facilitates adding tenant to a residence
	 * 
	 * @param tenant
	 */
	public void addtenant(Tenant tenant) {
		this.tenant = tenant;
		this.save();
	}

	/**
	 * Access method that return residence geolocation as LatLang
	 * 
	 * @return LatLng geolocation
	 */
	public LatLng getGeolocation() {
		return LatLng.toLatLng(geolocation);

	}

	/**
	 * Facilitates finding residence by its eircode
	 * 
	 * @param eircode
	 * @return residence
	 */
	public static Residence findByEircode(String eircode) {
		return find("eircode", eircode).first();
	}

	/**
	 * Facilitates finding residence of current landlord
	 * 
	 * @return residence
	 */
	public static Residence findByLandlord() {

		Landlord landlord = Landlords.getCurrentLandlord();
		return find("landlord", landlord).first();
	}
	
	/**
	 * Helper method to find residences of given landlord
	 * @param landlord
	 * @return residence
	 */
	public static Residence findByOneLandlord(Landlord landlord) {
		return find("landlord", landlord).first();
	}	

	/**
	 * Facilitates finding residence of current tenant
	 * 
	 * @return residence
	 */
	public static Residence findByTenant() {

		Tenant tenant = Tenants.getCurrentTenant();

		List<Residence> residences = Residence.findAll();
		
		//check residence tenant is same as logged in tenant 
		for (Residence r : residences) {
			if (r.tenant == tenant) {
				return r;
			}
		}
		//return null if no residence found for current tenant 
		return null;
	}

	/**
	 * Helper method to return rentedStatus of a residence as String
	 * 
	 * @param residence
	 * @return String rentedStatus
	 */
	public static String rentedStatus(Residence residence) {

		if (residence.tenant == null) {
			return "vacant";
		}
		return "rented";
	}
	
	/**
	 * Helper method to find vacant residence
	 * 
	 * @param residence r
	 * @return boolean vacant
	 */
	public static boolean vacant(Residence r) {
		return r.tenant == null;
	}

	/**
	 * Helper method to find current residence status
	 * 
	 * @return tenant info string
	 */
	public static String getTenant(Residence residence) {

		if (residence.tenant == null) {
			return "No Tenant";
		}
		return "Tenant is " + residence.tenant.toString();
	}

	/**
	 * Overrides toString method for Residence object
	 */
	public String toString() {
		return eircode;
	}
}