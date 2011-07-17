package inserts;

import cli.MenuEntryInterface;

public class InsertMovie implements MenuEntryInterface {

	@Override
	public String getName() {
		return "Movie hinzufügen";
	}

	@Override
	public String getDescription() {
		return "Fügt einen neuen Film zur DB hinzu.";
	}

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
