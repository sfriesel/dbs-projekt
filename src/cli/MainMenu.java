package cli;

import database.*;
import sqlqueries.*;

import java.io.*;
import java.util.ArrayList;

public class MainMenu implements MenuEntryInterface {

	private static ArrayList<MenuEntryInterface> menu;

	@Override
	public String getName() {
		return "DVD-Hauptmenu";
	}

	@Override
	public String getDescription() {
		return "";
	}

	public MainMenu() {

		// set up menueEntries
		menu = new ArrayList<MenuEntryInterface>();
		menu.add(new DBHandler());
		menu.add(new SimpleSQLEntry());
		menu.add(new AdvancedSQLEntry());
		menu.add(new InsertInvoiceEntry());
		menu.add(new ExitEntry());
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
