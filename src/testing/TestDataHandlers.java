package testing;

import database.DBConnector;
import datahandling.AbstractDataHandler;
import datahandling.CustomerDataHandler;
import datahandling.LocationDataHandler;

public class TestDataHandlers {
	public static void main(String[] args) {

		AbstractDataHandler handler = new LocationDataHandler();
		handler.parse();
	}

}
