package inserts;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import cli.MenuEntryInterface;
import database.DBConnector;

public class InsertMovie implements MenuEntryInterface {

	@Override
	public String getName() {
		return "Movie hinzufügen";
	}

	@Override
	public String getDescription() {
		return "Fügt einen neuen Film zur DB hinzu.";
	}

	@Override
	public void execute() throws Exception {

		DBConnector con = DBConnector.getInstance();
		PreparedStatement insertStmt = con.connection
				.prepareStatement("INSERT INTO Movie (title, description, "
						+ "category, release, rel_country) VALUES (?,?,?,?,?)");

		PreparedStatement selectStmt = con.connection
				.prepareStatement("SELECT * FROM Movie " + "WHERE title = ?;");

		System.out.println("Geben Sie einen neuen Movie ein:");
		System.out
				.println("<Titel, [Beschreibung], Kategorie(A,B), ReleaseDatum dd:mm:yyyy, ReleasLand>");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();

		String[] data = input.split(", ");
		
		while (input.split(", ").length != 5 ||
				!(data[2].equals("A") || data[2].equals("B"))) {
			
			System.out
					.println("Folgendes Format beachten: "
							+ "<Titel, [Beschreibung], Kategorie(A,B), Release-Datum dd:mm:yyyy, Release-Land>");
			input = br.readLine();
		}

		insertStmt.setString(1, data[0]);
		insertStmt.setString(2, data[1]);
		insertStmt.setString(3, data[2]);

		SimpleDateFormat format = new SimpleDateFormat("d:MM:yyyy");
		java.util.Date date = null;

		try {
			date = format.parse(data[3]);
		} catch (ParseException e) {
			System.out.println("Falsches Datumsformat.");
			return;
		}

		java.sql.Date sqlD = new java.sql.Date(date.getTime());

		insertStmt.setDate(4, sqlD);
		insertStmt.setString(5, data[4]);

		selectStmt.setString(1, data[0]);

		ResultSet rs = selectStmt.executeQuery();

		if (!rs.next()) {
			insertStmt.execute();
			con.connection.commit();
			System.out.println("Movie [" + data[0] + "] wurde hinzugefügt.");
		} else {
			System.out
					.println("Neuer Movie konnte nicht hinzugefügt werden, Movie mit gleichem Titel "
							+ "existiert bereits.");
			System.out.println("\n");
		}
	}
}
