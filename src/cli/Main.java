package cli;

import database.*;
import sqlqueries.*;

import java.io.*;
import java.util.ArrayList;

public class Main {

	private static ArrayList<MenuEntryInterface> menu;

	private static void printMenu() {
		System.out.println("DVD-Verleih-Menü:");

		for (int i = 0; i < menu.size(); i++) {
			System.out.println("[" + i + "] " + menu.get(i).getName());
		}
		System.out.println();

	}

	public static void main(String[] args) throws Exception {

		// Configure DB connection
		if (args.length < 3) {
			System.err
					.println("Too few args: <database> <user> <password> [host port]");
			System.exit(1);
		}
		if (args.length >= 5) {
			DBConnector.configure(args[3], args[4], args[0], args[1], args[2]);
		} else {
			DBConnector.configure(null, null, args[0], args[1], args[2]);
		}

		// set up menueEntries
		menu = new ArrayList<MenuEntryInterface>();
		menu.add(new ExitEntry());
		menu.add(new DBHandler());
		menu.add(new PlainSQL1A());
		menu.add(new PlainSQL1B());
		menu.add(new PlainSQL1C());
		menu.add(new PlainSQL1D());
		menu.add(new AdvancedSQL2A());
		menu.add(new AdvancedSQL2B());

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int input = -1;
		do {
			printMenu();

			try {
				input = Integer.parseInt(br.readLine());
			} catch (NumberFormatException e) {
			}

			menu.get(input).execute();

		} while (input != '0');
	}
}
