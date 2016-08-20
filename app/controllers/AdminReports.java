package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;

import models.Administrator;
import models.Landlord;
import models.Residence;
import models.Tenant;
import play.Logger;
import play.mvc.Controller;
import utils.AscendingRentComparator;
import utils.DescendingRentComparator;
import utils.ResidenceLandlordComparator;

public class AdminReports extends Controller {

	/**
	 * Renders admin reports page
	 */
	public static void index() {

		render();
	}

	/**
	 * Finds all registered residences, sorts list in alphabetical order by
	 * landlord Renders index page with sorted residence list
	 */
	public static void charts() {

		List<Residence> residences = Residence.findAll();
		Collections.sort(residences, new ResidenceLandlordComparator());

		render(residences);
	}

	/**
	 * Collects residence data for all residences and renders data in JSON
	 * object
	 * 
	 */
	public static void chart_data() {

		List<Residence> res = Residence.findAll();
		HashMap<String, Integer> data = new HashMap<String, Integer>();
		List<List<String>> residences = new ArrayList<List<String>>();

		// fetch all residences and add data to hashmap grouping by landlord
		for (Residence r : res) {
			String landlord = r.landlord.toString();

			int sumrent = data.containsKey(landlord) ? data.get(landlord) : 0;

			// residence rent is summed up by each landlord
			sumrent += r.rent;

			data.put(landlord, sumrent);
		}

		// iterate through hashmap of residences and extract data for JSON
		// object
		Iterator<String> keySetIterator = data.keySet().iterator();

		while (keySetIterator.hasNext()) {
			String key = keySetIterator.next();
			
			//calculate landlord residence % rate of total residences, cast to String and add to JSON object residences
			residences.add(0, Arrays.asList(key, (Integer.valueOf((int) Math.round(data.get(key) / totalRent() * 100)).toString())));
		}
		renderJSON(residences);
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

		// loop through existing residences and check their rented status
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

		// loop through all residences and check their residence Type
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

	/**
	 * Helper method to get total residences rent for residence rent %
	 * calculations
	 * 
	 * @return double totalRent
	 */
	private static double totalRent() {
		List<Residence> residences = Residence.findAll();
		int totalRent = 0;
		for (Residence r : residences) {
			totalRent += r.rent;
		}
		return totalRent;
	}
}