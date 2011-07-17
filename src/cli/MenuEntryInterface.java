package cli;

public interface MenuEntryInterface {

	public String getName();

	public String getDescription();

	public void execute() throws Exception;
}
