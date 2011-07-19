package inserts;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import cli.MenuEntryInterface;
import database.DBConnector;

public class InsertRental implements MenuEntryInterface {

	@Override
	public String getName() {
		return "Ausleihe hinzufügen";
	}

	@Override
	public String getDescription() {
		return "Fügt einen neuen Ausleihvorgang zur DB hinzu.";
	}

	@Override
	public void execute() throws Exception {

		System.out
				.println("Geben Sie einen Teil des Filmes ein, den Sie ausleihen wollen (%film%):");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String movie = br.readLine();

		DBConnector con = DBConnector.getInstance();
		PreparedStatement selectStmt = con.connection
				.prepareStatement("SELECT title FROM Movie WHERE title LIKE ?;");

		selectStmt.setString(1, movie);
		ResultSet rs = selectStmt.executeQuery();

		ArrayList<String> titel = new ArrayList<String>();

		while (rs.next()) {
			titel.add(rs.getString("title"));
		}

		if (titel.size() > 0) {

			for (int i = 0; i < titel.size(); i++) {
				System.out.println("(" + i + ") " + titel.get(i));
			}

			System.out.println("Fügen Sie einen neuen Ausleihvorgang hinzu:");
			System.out
					.println("<KundenID, Preismodell (starter, flat, speedy), Movie(Zahl)>");

			String input = br.readLine();
			

			while (input.split(", ").length != 3
					|| !(data[1].equals("starter") || data[1].equals("flat") || data[1]
							.equals("speedy"))) {

				System.out
						.println("Folgendes Format beachten: "
								+ "<KundenID, Preismodell (starter, flat, speedy), Movie(Zahl)>");
				input = br.readLine();
			}
			
			String[] data = input.split(", ");

			PreparedStatement insertStmt = con.connection
					.prepareStatement("INSERT INTO Rental (customer, pricemodel, "
							+ "movie, start) VALUES (?,?,?,?)");
			
			int kdNum = Integer.parseInt(data[0]);
			String filmNum = titel.get(Integer.parseInt(data[2]));

			insertStmt.setInt(1, kdNum);
			insertStmt.setString(2, data[1]);
			insertStmt.setString(3, filmNum);
			insertStmt.setTimestamp(4,
					new Timestamp(System.currentTimeMillis()));
			insertStmt.execute();
			con.connection.commit();
			
			System.out.println("Ausleihe für den Kunden ["+kdNum+"] mit Film [" + filmNum + "] wurde hinzugefügt.");
			
		} else {
			System.out
					.println("Eskonnten keine Filme mit diesem Suchwort gefunden werden.");
		}
	}
}
