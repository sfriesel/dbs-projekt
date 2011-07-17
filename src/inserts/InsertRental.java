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
		
		System.out.println("Geben Sie einen Teil des Filmes ein, den Sie ausleihen wollen (%film%):");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String movie = br.readLine();

		DBConnector con = DBConnector.getInstance();
		PreparedStatement selectStmt = con.connection
				.prepareStatement("SELECT title FROM Movie WHERE title LIKE ?;");
		
		selectStmt.setString(1, movie);
		ResultSet rs = selectStmt.executeQuery();
		
		ArrayList<String> titel = new ArrayList<String>();
		
		while (rs.next()){
			titel.add(rs.getString("title"));
		}
		
		for (int i = 0; i< titel.size(); i++){
			System.out.println("("+i+") "+titel.get(i));
		}
		
		System.out.println("Fügen Sie einen neuen Ausleihvorgang hizu:");
		System.out.println("<KundenID, Preismodell (starter, flat, speedy), Movie(Zahl)");
		
		String[] input = br.readLine().split(", ");
		
		
		PreparedStatement insertStmt = con.connection
				.prepareStatement("INSERT INTO Rental (customer, pricemodel, "
						+ "movie, start) VALUES (?,?,?,?)"); 
		

		insertStmt.setInt(1, Integer.parseInt(input[0]));
		insertStmt.setString(2, input[1]);
		insertStmt.setString(3, titel.get(Integer.parseInt(input[2])));
		insertStmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
		
		insertStmt.execute();
		con.connection.commit();
	}
}
