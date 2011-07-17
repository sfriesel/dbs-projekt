package cli;

import inserts.InsertCustomer;
import inserts.InsertMovie;
import inserts.InsertRental;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import sqlqueries.AccountingPerYearCustomer;

public class InsertInvoiceEntry implements MenuEntryInterface {

	ArrayList<MenuEntryInterface> menu;

	public InsertInvoiceEntry() {
		menu = new ArrayList<MenuEntryInterface>();

		menu.add(new BackEntry());
		menu.add(new AccountingPerYearCustomer());
		menu.add(new InsertCustomer());
		menu.add(new InsertMovie());
		menu.add(new InsertRental());
	}

	@Override
	public String getName() {
		return "Rechnungen und Hinzufügen von Einträgen";
	}

	@Override
	public String getDescription() {
		return "Rechungen können gedruckt werden, es können neue Datensätze zur Datenbank hinzugefügt werden.";
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
