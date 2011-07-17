package sqlqueries;

import java.sql.*;

import cli.PrintResult;

import cli.MenuEntry;
import database.*;

public class PlainSQL1B implements MenuEntry {

	@Override
	public String getName() {
		return "1B-Rechnungssummen";
	}

	@Override
	public String getDescription() {
		return "Die Rechnungssummen für alle Kunden für den Monat Januar.";
	}

	@Override
	public void execute() throws Exception {
		DBConnector con = DBConnector.getInstance();
		
		Statement stmt = con.connection.createStatement();
		ResultSet rs = stmt.executeQuery(
			  "SELECT x.customer, c.forename, c.surname, x.year, sum(x.price) "
			+ "FROM "
			+ "( "
				+ "(SELECT r.customer, EXTRACT(year from start) AS year, r.duration*p.priceperunit AS price "
				+ " FROM rental r JOIN movie m ON r.movie = m.title NATURAL JOIN pricing p "
				+ ") "
				+ "UNION "
				+ "(SELECT DISTINCT r.customer, EXTRACT(year from start) AS year, count(*)*10.00 AS price "
				+ " FROM rental r "
				+ " WHERE pricemodel = 'flat' "
				+ " GROUP BY r.customer, EXTRACT(year from start), EXTRACT(month from start) "
				+ ") "
			+ ") x JOIN customer c ON x.customer = c.id "
			+ "GROUP BY x.customer, c.forename, c.surname, x.year "
			+ "ORDER BY customer; ");
		PrintResult pr = new PrintResult(rs);
		pr.setHead("Kundennr.", "Vorname", "Nachname", "Jahr", "Rechnungssumme (€)");
		pr.setDescription("Rechnungssummen für alle Kunden für den Monat Januar:");
		pr.print();
	}

}
