package sqlqueries;

import cli.MenuEntry;

public class PlainSQL1C implements MenuEntry {

	@Override
	public String getName() {
		return "1C-Oscarfrage";
	}

	@Override
	public String getDiscription() {
		return "Sollte man bestimmte Monate für eine Premiere wählen, um für einen Oscar in den "
				+ "Kategorien Bester Film/Regisseur/HauptdarstellerIN nominiert zu werden?";
	}

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub

	}

}
