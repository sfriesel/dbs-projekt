package testing;

import java.io.IOException;
import java.sql.SQLException;

import database.DBConnector;
import datahandling.AbstractDataHandler;
import datahandling.ActorsActressDataHandler;
import datahandling.CustomerDataHandler;
import datahandling.LocationDataHandler;
import datahandling.MovieDataHandler;
import datahandling.ReleaseDataHandler;

public class TestDataHandlers {
	public static void main(String[] args) {
		
		DBConnector.configure(
				"localhost", "5432", "movies", "alexa", "dinkel");

		// AbstractDataHandler handler1 = new CustomerDataHandler();
		// handler1.parse();

//		AbstractDataHandler handler2 = new MovieDataHandler();
//		try {
//			handler2.parse();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//
//		 AbstractDataHandler handler3 = new ReleaseDataHandler();
//		 try {
//		 handler3.parse();
//		 } catch (IOException e) {
//		 // TODO Auto-generated catch block
//		 e.printStackTrace();
//		 } catch (SQLException e) {
//		 // TODO Auto-generated catch block
//		 e.printStackTrace();
//		 }

//		AbstractDataHandler handler4 = new LocationDataHandler();
//		try {
//			handler4.parse();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		AbstractDataHandler handler5 = new ActorsActressDataHandler("Daten/actors.list", 239 , 11272388, "\t+", "m");
		try {
			handler5.parse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		AbstractDataHandler handler6 = new ActorsActressDataHandler("Daten/actress.list", 241, 6592288, "\t+", "w");
		try {
			handler6.parse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
