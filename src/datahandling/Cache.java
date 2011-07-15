package datahandling;

import java.util.HashSet;

public class Cache {

	private static Cache instance = null;
	public HashSet<String> movie = null;
	public HashSet<Integer> customer = null;

	private Cache() {
		this.movie = new HashSet<String>();
		this.customer = new HashSet<Integer>();
	}

	public static Cache getInstance() {
		if (instance == null) {
			instance = new Cache();
		}
		return instance;
	}
}
