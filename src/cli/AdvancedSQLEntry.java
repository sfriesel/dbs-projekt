package cli;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import sqlqueries.AdvancedSQL2A;
import sqlqueries.AdvancedSQL2B;

public class AdvancedSQLEntry implements MenuEntryInterface {

	ArrayList<MenuEntryInterface> menu;

	public AdvancedSQLEntry() {
		menu = new ArrayList<MenuEntryInterface>();

		menu.add(new BackEntry());
		menu.add(new AdvancedSQL2A());
		menu.add(new AdvancedSQL2B());
	}

	@Override
	public String getName() {
		return "2-Erweiterte Aufgaben";
	}

	@Override
	public String getDescription() {
		return "Erweiterte Aufgaben die nicht nur mit SQL zu lösen sind.";
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
