package datahandling;

/***
 * Here are some userfull methods, some DataHandlers need to use.
 * 
 * @author alexa
 * 
 */
public class DataHandlerUtils {

	/**
	 * Checks weather a movie title is in the time range of 2010 and 2011.
	 * 
	 * @param title
	 *            The title to check.
	 * @return True if the movie was released in 2010 or 2011. False if all
	 *         other years.
	 */
	public static boolean isInTimeRange(String title) {

		if (title.contains("2010") || title.contains("2011")) {
			return true;
		} else
			return false;
	}
}
