package sqlqueries;

import java.sql.*;

import cli.PrintResult;

import cli.MenuEntryInterface;
import database.*;;

public class PlainSQL1C implements MenuEntryInterface {

	@Override
	public String getName() {
		return "1C-Oscarfrage";
	}

	@Override
	public String getDescription() {
		return "Sollte man bestimmte Monate für eine Premiere wählen, um für einen Oscar in den "
				+ "Kategorien Bester Film/Regisseur/HauptdarstellerIN nominiert zu werden?";
	}

	@Override
	public void execute() throws Exception {
		DBConnector con = DBConnector.getInstance();
		
		Statement stmt = con.connection.createStatement();
		ResultSet rs = stmt.executeQuery(
			  "SELECT EXTRACT(MONTH FROM Release), COUNT(*) "
			+ "FROM Nomination n JOIN Movie m ON n.title = m.title "
			+ "GROUP BY EXTRACT(MONTH FROM Release) "
			+ "ORDER BY COUNT(*) DESC");
		PrintResult pr = new PrintResult(rs);
		pr.setHead("Monat", "Nominierungen");
		pr.setDescription("Monate mit den meisten Oscar-nominierten Veröffentlichungen:");
		pr.print();
	}

}
