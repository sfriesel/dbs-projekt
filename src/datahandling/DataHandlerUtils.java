package datahandling;

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
	 * Checks weather a given pattern is already inside a certain table and
	 * column. E.g. is a given movie title already in the DB?
	 * 
	 * @param pattern
	 * @param table
	 * @param column
	 * @return
	 */
	public static boolean isAlreadyInserted(String pattern, String table,
			String column) {

		// TODO: implement
		return true;
	}

	/**
	 * Takes the release-String (e.g. USA:22 January 2006) and returns the Date
	 * in a proper Date-Formet the DB can work with.
	 * 
	 * @param releaseString
	 * @return only the date
	 */
	public static String extractDate(String releaseString) {
		// TODO: make a real date format for inserting into the db
		return releaseString.split(":")[1];
	}
}
