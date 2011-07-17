package database;

import cli.MenuEntryInterface;
import datahandling.AbstractDataHandler;
import datahandling.ActorsActressDataHandler;
import datahandling.CustomerDataHandler;
import datahandling.DirectorsDataHandler;
import datahandling.LocationDataHandler;
import datahandling.MovieDataHandler;
import datahandling.ReleaseDataHandler;
import datahandling.RentalsDataHandler;

public class DBHandler implements MenuEntryInterface {

	@Override
	public String getName() {
		return "Einlesen der DB";
	}

	@Override
	public String getDescription() {
		return "Die Datenbank wird komplett neu gemacht.";
	}

	@Override
	public void execute() throws Exception {
		
		System.out.println("Einlesen der Datenbank wird gestartet ...");

		DBConnector con = DBConnector.getInstance();
		con.executeSqlScript("Datenbankschema/scriptSQL");

		AbstractDataHandler handler = new CustomerDataHandler();
		handler.parse();

		handler = new MovieDataHandler();
		handler.parse();

		handler = new ReleaseDataHandler();
		handler.parse();

		handler = new DirectorsDataHandler();
		handler.parse();

		handler = new LocationDataHandler();
		handler.parse();

		handler = new RentalsDataHandler();
		handler.parse();

		handler = new ActorsActressDataHandler("Daten/actors.list", 239,
				11272388, "\t+", "m");
		handler.parse();

		handler = new ActorsActressDataHandler("Daten/actresses.list", 241,
				6592286, "\t+", "f");
		handler.parse();

		// TODO parse plot

		System.out.println(AbstractDataHandler.time / 1000f / 60f + " min");
	}
}
