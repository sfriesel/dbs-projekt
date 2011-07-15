package cli;

public interface MenuEntry {

	public String getName();

	public String getDiscription();

	public void execute() throws Exception;
}
