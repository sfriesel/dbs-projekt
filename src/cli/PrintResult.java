package cli;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrintResult {

	private String description = null;
	private String[] head = null;
	private ArrayList<String[]> rows = null;

	private int numSpaces = 5;

	public PrintResult() {
		this.rows = new ArrayList<String[]>();
	}

	public PrintResult(ResultSet rs) throws SQLException {
		this.rows = new ArrayList<String[]>();

		ResultSetMetaData metadata = rs.getMetaData();
		int number = metadata.getColumnCount();

		while (rs.next()) {
			String[] r = new String[number];
			for (int i = 0; i < number; i++) {
				r[i] = rs.getString(i + 1);
			}
			addRow(r);
		}
		
		rs.close();
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setHead(String... head) {
		this.head = head;
	}

	public void addRow(String... row) {
		this.rows.add(row);
	}

	private int getHeadLength() {
		int result = 0;
		for (int i = 0; i < head.length; i++) {
			result += head[i].toCharArray().length + numSpaces + 3;
		}
		return result;
	}

	private void printLine() {
		for (int i = 0; i < getHeadLength(); i++)
			System.out.print("-");
		System.out.print("\r");
	}

	public void print() {

		System.out.println(description);

		printLine();

		String format = "";

		for (int i = 0; i < head.length; i++) {
			format = format.concat("%" + (head[i].length() + numSpaces)
					+ "s  |");
		}
		format = format.concat("\n");

		System.out.printf(format, (Object[]) head);

		printLine();

		for (int i = 0; i < rows.size(); i++) {
			System.out.printf(format, (Object[]) rows.get(i));
		}

		printLine();

		System.out.println("\n");
	}
}
