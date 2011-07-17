package cli;

public class BackEntry implements MenuEntryInterface {

	@Override
	public String getName() {
		return "..";
	}

	@Override
	public String getDescription() {
		return "Zurück zum Hauptmenu";
	}

	@Override
	public void execute() throws Exception {
		new MainMenu().execute();
	}
}
