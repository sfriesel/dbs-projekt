package sqlqueries;

import cli.MenuEntry;

public class PlainSQL1B implements MenuEntry {

	@Override
	public String getName() {
		return "1B-Rechnungssummen";
	}

	@Override
	public String getDiscription() {
		return "Die Rechnungssummen für alle Kunden für den Monat Januar.";
	}

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub

	}

}
