package datahandling;

import java.text.SimpleDateFormat;
import java.sql.Date;
import java.text.ParseException;
import java.util.Locale;

/***
 * Here are some userfull methods, some DataHandlers need to use.
 * 
 * @author alexa
 * 
 */
public class DataHandlerUtils {

	/**
	 * Checks weather a line contains 2010 or 2011.
	 * 
	 * @param line
	 *            The line to check.
	 * @return True if the movie was released in 2010 or 2011. False if all
	 *         other years.
	 */
	public static boolean isInTimeRange(String line) {

		if (line.contains("2010") || line.contains("2011")) {
			return true;
		} else
			return false;
	}

	/**
	 * Takes the release-String (e.g. USA:22 January 2006) and returns the Date
	 * in a proper Date-Formet the DB can work with.
	 * 
	 * @param releaseString
	 *            the string to extract the date from
	 * @return the date as an sql date.
	 */
	public static Date extractDate(String releaseString) {

		String dateString = releaseString.split(":")[1].replaceAll("\\s+", " ");
		int dateLength = dateString.split(" ").length;

		String pattern = "d MMM yyyy";
		Locale locale = new Locale("en");
		SimpleDateFormat format = null;
		java.util.Date date = null;

		// month year
		if (dateLength == 2) {
			pattern = "MMM yyyy";
		}

		// year
		if (dateLength == 1) {
			pattern = "yyyy";
		}

		format = new SimpleDateFormat(pattern, locale);

		try {
			date = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// change it to s sql date
		java.sql.Date dt = null;

		long t = date.getTime();
		dt = new java.sql.Date(t);

		return dt;
	}

	/**
	 * Extracts the country from a proper release string.
	 * 
	 * @param releaseString
	 * @return the country the movie was released
	 */
	public static String extractCountry(String releaseString) {
		return releaseString.split(":")[0];
	}

	/**
	 * Only Strings are allowed, who contain a ":", so we can be sure to get a
	 * country and a release date.
	 * 
	 * @param releaseString
	 *            the String to be checked
	 * @return true if contains a ":" and false if not
	 */
	public static boolean isValidReleaseString(String releaseString) {
		if (releaseString.split(":").length == 2) {
			return true;
		} else {
			return false;
		}
	}
}
