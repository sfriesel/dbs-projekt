package cli;

public class ExitEntry implements MenuEntryInterface {

	@Override
	public String getName() {
		return "Exit";
	}

	@Override
	public String getDescription() {
		return "Beendet das Consolen-Programm.";
	}

	@Override
	public void execute() throws Exception {
		System.out.println("Beendet.");
		System.exit(1);
	}
}
