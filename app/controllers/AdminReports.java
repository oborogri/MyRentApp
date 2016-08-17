package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;

import models.Administrator;
import models.Landlord;
import models.Residence;
import models.Tenant;
import play.Logger;
import play.mvc.Controller;

public class AdminReports extends Controller {

	/**
	 * Renders admin reports page
	 */
	public static void index() {

		List<Residence> residences = Residence.findAll();

		render(residences);
	}

	public static void byRented(String rentedStatus) {

		}
	}

