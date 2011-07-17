package inserts;

import cli.MenuEntryInterface;

public class InsertRental implements MenuEntryInterface {

	@Override
	public String getName() {
		return "Ausleihe hinzufügen";
	}

	@Override
	public String getDescription() {
		return "Fügt einen neuen Ausleihvorgang zur DB hinzu.";
	}

	@Override
	public void execute() throws Exception {
	}
}
