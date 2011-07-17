package cli;

import database.DBConnector;

public class Main {

	public static void main(String[] args) {
		
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

		MainMenu me = new MainMenu();
		
		try {
			me.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
