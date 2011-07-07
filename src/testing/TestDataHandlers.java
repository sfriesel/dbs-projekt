package testing;

import database.DBConnector;
import datahandling.AbstractDataHandler;
import datahandling.CustomerDataHandler;
import datahandling.LocationDataHandler;
import datahandling.MovieDataHandler;

public class TestDataHandlers {
	public static void main(String[] args) {

		AbstractDataHandler handler = new MovieDataHandler();
		handler.parse();
	}

}
