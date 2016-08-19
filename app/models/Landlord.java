package models;

import javax.persistence.CascadeType;
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
	public String address1;
	public String address2;
	public String city;
	public String county;	
	public String password;

	@OneToMany(mappedBy = "landlord", cascade=CascadeType.ALL)
	public List<Residence> residences = new ArrayList<Residence>();

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
	 * Overrides toString method for Landlord object
	 */
	public String toString() {
		return firstName + " " + lastName;
	}
}