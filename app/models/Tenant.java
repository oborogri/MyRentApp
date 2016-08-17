package models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import play.db.jpa.Model;
import play.Logger;
import play.db.jpa.Blob;

import java.util.List;
import java.util.ArrayList;

@Entity
public class Tenant extends Model {
	public String firstName;
	public String lastName;
	public String email;
	public String password;

	@OneToOne
	public Residence residence;

	/**
	 * Facilitates identifying a tenant by their e-mail
	 * 
	 * @param email
	 * @return tenant
	 */
	public static Tenant findByEmail(String email) {
		return find("email", email).first();
	}

	/**
	 * Validates tenant password
	 * 
	 * @param password
	 * @return true if password match
	 */
	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}

	/**
	 * Overrides toString method for tenant object
	 */
	public String toString() {
		return firstName + " " + lastName;
	}
}