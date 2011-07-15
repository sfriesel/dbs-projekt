package cli;

import database.*;
import sqlqueries.*;
import java.io.*;
import java.sql.SQLException;

public class Main {
	public static void printMenu() {
		System.out.println("Please select one of the following options:");
		System.out.println("(a) 5.1a Wie viele Kunden haben sich für welches Preismodell entschieden?");
		System.out.println("(r) reset db");
		System.out.println("(q) quit");
		System.out.print("$ ");
	}
	public static void main(String[] args) throws IOException, SQLException {
		if(args.length < 3) {
			System.err.println("too few arguments: <database> <user> <password> [host port]");
			System.exit(1);
		}
		if(args.length >= 5) {
			DBConnector.configure(args[3], args[4], args[0], args[1], args[2]);
		} else {
			DBConnector.configure(null, null, args[0], args[1], args[2]);
		}
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String input;
		do {
			System.out.println();
			printMenu();
			input = br.readLine();
			switch(input.charAt(0)) {
				case 'a':
					sqlqueries.PlainSQL1a.execute();
					break;
				case 'r':
					DBConnector.getInstance().resetDB();
				case 'q':
					break;
				default:
					System.err.println("unknown input");
					break;
			}
		} while(input.charAt(0) != 'q');
	}
}
