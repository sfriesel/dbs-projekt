package datahandling;

public class CustomerDataHandler extends AbstractDataHandler {
	
	static String filename = "Daten/customers.list";
	static int lineNumber = 1;
	static String pattern = ",";
	
	public CustomerDataHandler() {
		super(filename, lineNumber, pattern);
	}

	@Override
	protected void insertDB(String[] arrayLine) {
		for (int i = 0; i < arrayLine.length; i++){
			System.out.print(arrayLine[i]);
			System.out.print(" ");
		}
		System.out.println("\r");
	}
	
	public static void main(String[] args) {
		AbstractDataHandler dh = new CustomerDataHandler();
		dh.parse();
	}
}
