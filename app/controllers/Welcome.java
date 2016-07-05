package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Welcome extends Controller {

	/**
	 * Renders welcome start page
	 */
	public static void index() {

		session.clear();
		render();
	}

}