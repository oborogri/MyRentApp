package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;

import models.Administrator;
import models.Landlord;
import models.Residence;
import models.Tenant;
import play.Logger;
import play.mvc.Controller;
import utils.AscendingRentComparator;
import utils.DescendingRentComparator;

public class AdminReports extends Controller {

	/**
	 * Renders admin reports page
	 */
	public static void index() {

		render();
	}

	/**
	 * Sorts residences by rented status Renders sorted residences to the
	 * reports page
	 * 
	 * @param rentedStatus
	 *            vacant/rented
	 */
	public static void byRented(String rentedStatus) {

		List<Residence> residences = Residence.findAll();

		Logger.info("Sort by rented status: " + rentedStatus);

		ArrayList<Residence> byrented = new ArrayList<Residence>();
		
		//loop through existing residences and check their rented status
		for (Residence r : residences) {
			if ((rentedStatus.equals("vacant")) && (Residence.vacant(r))) {
				byrented.add(r);
			}

			else if ((rentedStatus.equals("rented")) && (!Residence.vacant(r))) {
				byrented.add(r);
			}

		}
		render(byrented);
	}

	/**
	 * Sorts residences by residence type Renders residences to reports page
	 * 
	 * @param residenceType
	 */
	public static void byResidenceType(String residenceType) {

		List<Residence> residences = Residence.findAll();
		Logger.info("Sort by residence type: " + residenceType);

		ArrayList<Residence> byResidenceType = new ArrayList<Residence>();

		//loop through all residences and check their residence Type
		for (Residence r : residences) {
			if ((r.residenceType.equals("Apartment|Flat")) && ((r.residenceType).equals(residenceType))) {
				byResidenceType.add(r);
			}

			else if ((r.residenceType.equals("House")) && ((r.residenceType).equals(residenceType))) {
				byResidenceType.add(r);

			} else if ((r.residenceType.equals("Studio")) && ((r.residenceType).equals(residenceType))) {
				byResidenceType.add(r);
			}
		}
		render(byResidenceType);
	}

	/**
	 * Sorts residences list by rent ascending or descending Renders sorted
	 * residences list to the report page
	 * 
	 * @param sortDirection
	 */
	public static void bySortedRent(String sortDirection) {

		List<Residence> bySortedRent = Residence.findAll();

		Logger.info("Sort by rent: " + sortDirection);

		// sort residences by ascending rent
		if (sortDirection.equals("ascending")) {
			Collections.sort(bySortedRent, new AscendingRentComparator());

		} else if ((sortDirection.equals("descending"))) {
			// sort residences by descending rent
			Collections.sort(bySortedRent, new DescendingRentComparator());
		}
		render(bySortedRent);
	}

	/**
	 * Renders all residences in unsorted list (by registration date order)
	 */
	public static void filter() {

		List<Residence> residences = Residence.findAll();
		Logger.info("Residences list by registration date");

		render(residences);
	}
}