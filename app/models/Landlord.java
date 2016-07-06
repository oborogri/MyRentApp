package models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.jpa.Model;
import play.Logger;
import play.db.jpa.Blob;

import java.util.List;
import java.util.ArrayList;

@Entity
public class Landlord extends Model {
	public String firstName;
	public String lastName;
	public String email;
	public String password;

	@OneToMany(mappedBy = "landlord")
	public List<Residence> residences = new ArrayList<Residence>();

	/**
	 * Constructor for landlord object
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 *//*
		 * public Landlord(String firstName, String lastName, String email,
		 * String password) {
		 * 
		 * this.firstName = firstName; this.lastName = lastName; this.email =
		 * email; this.password = password;
		 * 
		 * }
		 */

	/**
	 * Facilitates identifying a user by their e-mail
	 * 
	 * @param email
	 * @return user
	 */
	public static Landlord findByEmail(String email) {
		return find("email", email).first();
	}

	/**
	 * Validates user password
	 * 
	 * @param password
	 * @return true if password match
	 */
	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}

	/**
	 * Overrides toString method for user object
	 */
	public String toString() {
		return firstName + " " + lastName;
	}
}