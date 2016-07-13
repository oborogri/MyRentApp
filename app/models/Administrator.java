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
public class Administrator extends Model {
	public String firstName;
	public String lastName;
	public String email;
	public String password;
	
	/**
	 * Facilitates identifying a admin by their e-mail
	 * 
	 * @param email
	 * @return admin
	 */
	public static Administrator findByEmail(String email) {
		return find("email", email).first();
	}

	/**
	 * Validates admin password
	 * 
	 * @param password
	 * @return true if password match
	 */
	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}

	/**
	 * Overrides toString method for tadmin object
	 */
	public String toString() {
		return firstName + " " + lastName;
	}
}