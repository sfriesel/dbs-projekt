package cli;

public class ExitEntry implements MenuEntry {

	@Override
	public String getName() {
		return "Exit";
	}

	@Override
	public String getDiscription() {
		return "Beendet das Consolen-Programm.";
	}

	@Override
	public void execute() throws Exception {
		System.out.println("Beendet.");
		System.exit(1);
	}
}
