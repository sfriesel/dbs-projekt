package cli;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import sqlqueries.PlainSQL1A;
import sqlqueries.PlainSQL1B;
import sqlqueries.PlainSQL1C;
import sqlqueries.PlainSQL1D;

public class SimpleSQLEntry implements MenuEntryInterface {

	ArrayList<MenuEntryInterface> menu;

	public SimpleSQLEntry() {

		menu = new ArrayList<MenuEntryInterface>();

		menu.add(new BackEntry());
		menu.add(new PlainSQL1A());
		menu.add(new PlainSQL1B());
		menu.add(new PlainSQL1C());
		menu.add(new PlainSQL1D());
	}

	@Override
	public String getName() {
		return "1-Abfragen";
	}

	@Override
	public String getDescription() {
		return "Abfragen in reines SQL";
	}

	private void printMenu() {
		System.out.println(getName());

		for (int i = 0; i < menu.size(); i++) {
			System.out.println("[" + i + "] " + menu.get(i).getName());
		}
		System.out.println();
	}

	@Override
	public void execute() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int input = -1;
		do {
			printMenu();

			try {
				input = Integer.parseInt(br.readLine());
			} catch (NumberFormatException e) {
			}

			if (input < menu.size()) {
				menu.get(input).execute();
			}

		} while (input != '0');
	}

}
