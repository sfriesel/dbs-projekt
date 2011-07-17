package inserts;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import cli.MenuEntryInterface;
import database.DBConnector;

public class InsertCustomer implements MenuEntryInterface {

	@Override
	public String getName() {
		return "Insert-Neuer Kunde";
	}

	@Override
	public String getDescription() {
		return "Fügt einen neuen Kunden zur DB hinzu.";
	}

	@Override
	public void execute() throws Exception {
		System.out.println("Geben Sie einen neuen Kunden ein:");
		System.out
				.println("<Nachname, Vorame, Straße, Hausnummer, PLZ, Stadt, Telefonnummer>");

		DBConnector con = DBConnector.getInstance();
		PreparedStatement insertStmt = con.connection
				.prepareStatement("INSERT INTO Customer (surname, forename, "
						+ "street, streetnumber, zip, city, telephone) VALUES (?,?,?,?,?,?,?,?)");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();

		while (input.split(", ").length != 7) {

			System.out
					.println("Folgendes Format beachten: "
							+ "<Nachname, Vorame, Straße, Hausnummer, PLZ, Stadt, Telefonnummer>");
			input = br.readLine();
		}

		String[] data = input.split(", ");

		insertStmt.setString(1, data[0]);
		insertStmt.setString(2, data[1]);
		insertStmt.setString(3, data[2]);
		insertStmt.setInt(4, Integer.parseInt(data[3]));
		insertStmt.setString(5, data[4]);
		insertStmt.setString(6, data[5]);
		insertStmt.setString(7, data[6]);

		PreparedStatement selectStmt = con.connection
				.prepareStatement("SELECT * FROM Customer "
						+ "WHERE surname = ? AND forename = ?;");

		selectStmt.setString(1, data[0]);
		selectStmt.setString(2, data[1]);

		ResultSet rs = selectStmt.executeQuery();

		if (!rs.next()) {
			insertStmt.execute();
			System.out.println("Kunde " + data[0] + ", " + data[1]
					+ "wurde hinzugefügt.");
		} else {
			System.out
					.println("Neuer Kunde konnte nicht hinzugefügt werden, Kunde mit gleichem Namen "
							+ "existiert bereits.");
		}
	}
}
