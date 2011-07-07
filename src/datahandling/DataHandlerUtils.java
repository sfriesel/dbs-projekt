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
		String year = title.substring(title.lastIndexOf("\"")+3,
				title.lastIndexOf("\"") + 7);

		//TDOD: Bug " kommt nicht immer vor, am besten nur nach 2011 oder 2010 im titel suchen ?
		//TODO: What to do with "????" ?
		if (year.equals("2010") || year.equals("2011"))
			return true;
		else
			return false;

	}
}
