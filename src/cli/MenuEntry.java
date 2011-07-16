package cli;

public interface MenuEntry {

	public String getName();

	public String getDescription();

	public void execute() throws Exception;
}
