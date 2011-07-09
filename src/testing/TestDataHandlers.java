package testing;

import datahandling.AbstractDataHandler;
import datahandling.CustomerDataHandler;
import datahandling.LocationDataHandler;
import datahandling.MovieDataHandler;
import datahandling.ReleaseDataHandler;

public class TestDataHandlers {
	public static void main(String[] args) {

//		AbstractDataHandler handler1 = new CustomerDataHandler();
//		handler1.parse();
//		
//		AbstractDataHandler handler2 = new MovieDataHandler();
//		handler2.parse();
		
		AbstractDataHandler handler3 = new ReleaseDataHandler();
		handler3.parse();
		
//		AbstractDataHandler handler4 = new LocationDataHandler();
//		handler4.parse();
	}

}
