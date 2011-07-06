package datahandling;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import sun.applet.Main;

public class Parser {

	static String customers = "Daten/customers.list";
	static String rentals = "Daten/rentals.list";
	static String modmovies = "Daten/modmovies.list";
	static String actor = "Daten/actor.list";
	static String actress = "Daten/actors.list";
	static String directors = "Daten/directors.list";
	static String locations = "Daten/locations.list";
	static String releaseDates = "Daten/release-dates.list";
	static String plot = "Daten/plot.list";

	/***
	 * Parses the customer.list file and adds it to the database.
	 * Format:
	 * id,surename,forename,street+number,postal code,city,phonenumber
	 */
	public static void parseCustomer() {

		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(customers), "ISO-8859-15"));

			String stringLine;
			// Read File Line By Line, but ignore the first line
			reader.readLine();
			while ((stringLine = reader.readLine()) != null) {
				String[] arrayLine = stringLine.split(",");
				System.out.println(arrayLine[1]);

				// TODO: add to Database, what about the street and number ?
			}

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

	}

	/***
	 * Parses the location.list file and adds it ti the database.
	 * Format:
	 * title	|	town/city, state/province/county, country	|	attribute	|	order
	 * Where | is a tab.
	 */
	public static void parseLocation() {
		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(locations), "ISO-8859-15"));

			String stringLine;
			// Read File Line By Line, but start at line 265
			for (int i = 0; i< 263; i++){
				reader.readLine();
			}
			System.out.println(reader.readLine());
			int l = 0;
			while ((stringLine = reader.readLine()) != null) {
				
				String[] arrayLine = stringLine.split("\t");
				
				if (arrayLine.length > l){
					l = arrayLine.length;
					System.out.println(l);
				}

				// TODO: add to Database
			}
			System.out.println(l);

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

	}

	public static void main(String[] args) {
		parseLocation();

	}

}
