package testing;

import java.io.IOException;
import java.sql.SQLException;

import database.DBConnector;
import datahandling.AbstractDataHandler;
import datahandling.ActorsActressDataHandler;
import datahandling.MovieDataHandler;

public class TestDataHandlers {
	public static void main(String[] args) {

		DBConnector.configure("localhost", "5432", "movies", "alexa", "dinkel");

		AbstractDataHandler handler1 = new MovieDataHandler();
		try {
			handler1.parse();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		System.out.println("finished movies");

		AbstractDataHandler handler2 = new ActorsActressDataHandler(
				"Daten/actors.list", 239, 11272388, "\t+", "m");
		try {
			handler2.parse();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
