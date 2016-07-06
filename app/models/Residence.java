package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.Model;
import utils.LatLng;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class Residence extends Model {
	
	public String residenceType;
	public String rented;
	public int rent;
	public int numberBedrooms;
	public int numberBathrooms;
	public int area;
	public String geolocation;
	public Date dateRegistered;
	
@ManyToOne
public User user;

	/**
	 * Constructor for Residence object
	 * 
	 * @param user - user registering the residence
	 * @param residenceType - type of residence
	 * @param rented - occupied or no
	 * @param rent - amount of rent 
	 * @param numberBedrooms 
	 * @param numberBathrooms
	 * @param area
	 * @param geolocation
	 *//*
	public Residence(User user, String residenceType, String rented, int rent, int numberBedrooms, int numberBathrooms, int area, String geolocation) {

		this.user = user;
		this.residenceType = residenceType;
		this.rented = rented;
		this.rent = rent;
		this.numberBedrooms = numberBedrooms;
		this.numberBathrooms = numberBathrooms;
		this.area = area;
		this.geolocation = geolocation;
		this.dateRegistered = new Date();
	}*/
	
	/**
	 * Facilitates adding user to a residence
	 * @param user
	 */
	
	public void addUser(User user) {
		this.user = user;
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
	
}