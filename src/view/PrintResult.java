package view;

import java.util.ArrayList;

public class PrintResult {

	private String description = null;
	private String[] head = null;
	private ArrayList<String[]> rows = null;

	public PrintResult() {
		this.rows = new ArrayList<String[]>();
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setHead(String[] head) {
		this.head = head;
	}

	public void addRow(String[] row) {
		this.rows.add(row);
	}

	public void print() {
		System.out.println(description);
		System.out
				.println("----------------------------------------------------------");

		printLine(head);
		System.out
				.println("----------------------------------------------------------");

		for (int i = 0; i < rows.size(); i++) {
			printLine(rows.get(i));
		}
	}

	public void printLine(String[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			System.out.print(array[i] + "\t");
		}
		System.out.print(array[array.length - 1]);
		System.out.print("\r");
	}
}
