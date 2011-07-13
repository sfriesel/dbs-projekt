package testing;

import java.io.IOException;
import java.sql.SQLException;

import database.DBConnector;
import datahandling.AbstractDataHandler;
import datahandling.ActorsActressDataHandler;
import datahandling.CustomerDataHandler;
import datahandling.DirectorsDataHandler;
import datahandling.LocationDataHandler;
import datahandling.MovieDataHandler;
import datahandling.ReleaseDataHandler;
import datahandling.RentalsDataHandler;

public class TestDataHandlers {
	public static void main(String[] args) {

		DBConnector.configure("localhost", "5432", "movies", "alexa", "dinkel");

//		AbstractDataHandler handler1 = new CustomerDataHandler();
//		try {
//			System.out.println("Start Customer.");
//			handler1.parse();
//			System.out.println("End Customer.");
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		} catch (SQLException e1) {
//			System.out.println(handler1.counter);
//			e1.printStackTrace();
//		}
//
//		AbstractDataHandler handler2 = new MovieDataHandler();
//		try {
//			System.out.println("Start Movie.");
//			handler2.parse();
//			System.out.println("End Movie.");
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		} catch (SQLException e1) {
//			System.out.println(handler2.counter);
//			e1.printStackTrace();
//		}
//
//		AbstractDataHandler handler3 = new ReleaseDataHandler();
//		try {
//			System.out.println("Start Release.");
//			handler3.parse();
//			System.out.println("End Release.");
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			System.out.println(handler3.counter);
//			e.printStackTrace();
//		}
//
//		AbstractDataHandler handler4 = new LocationDataHandler();
//		try {
//			System.out.println("Start Location.");
//			handler4.parse();
//			System.out.println("End Location.");
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			System.out.println(handler4.counter);
//			e.printStackTrace();
//		}
//
//		AbstractDataHandler handler5 = new ActorsActressDataHandler(
//				"Daten/actors.list", 239, 11272388, "\t+", "m");
//		try {
//			System.out.println("Start Actor.");
//			handler5.parse();
//			System.out.println("End Actor.");
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			System.out.println(handler5.counter);
//			e.printStackTrace();
//		}

		AbstractDataHandler handler6 = new ActorsActressDataHandler(
				"Daten/actresses.list", 241, 6592288, "\t+", "f");
		try {
			System.out.println("Start Actress.");
			handler6.parse();
			System.out.println("End Actress.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println(handler6.counter);
			e.printStackTrace();
		}

		AbstractDataHandler handler7 = new DirectorsDataHandler();
		try {
			System.out.println("Start Directors.");
			handler7.parse();
			System.out.println("End Directors.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println(handler7.counter);
			e.printStackTrace();
		}

		AbstractDataHandler handler8 = new RentalsDataHandler();
		try {
			System.out.println("Start Rentals.");
			handler8.parse();
			System.out.println("End Rentals.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println(handler8.counter);
			e.printStackTrace();
		}
	}
}
