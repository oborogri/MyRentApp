package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import controllers.Landlords;
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

	@OneToOne
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
	 * @return residence
	 */
	public static Residence findByLandlord() {

		Landlord landlord = Landlords.getCurrentLandlord();
		return find("landlord", landlord).first();
	}
}