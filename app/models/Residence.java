package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.Model;
import utils.LatLng;
import play.Logger;
import play.db.jpa.Blob;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class Residence extends Model {
	
	public String residenceType;
	public String rented;
	public int rent;
	public int numberBedrooms;
	public String geolocation;
	public Date dateRegistered;
	
@ManyToOne
public User tenant;

	/**
	 * Constructor for Residence object
	 * 
	 * @param user
	 * @param rented
	 * @param rent
	 * @param numberBedrooms
	 */
	public Residence(User user, String residenceType, String rented, int rent, int numberBedrooms, String geolocation) {

		this.tenant = user;
		this.residenceType = residenceType;
		this.rented = rented;
		this.rent = rent;
		this.numberBedrooms = numberBedrooms;
		this.geolocation = geolocation;
		this.dateRegistered = new Date();
	}
	
	/**
	 * Access method to return residence geolocation as LatLang 
	 * 
	 * @return LatLng geolocation
	 */
	public LatLng getGeolocation() {
		return LatLng.toLatLng(geolocation);
		
	}
	
}