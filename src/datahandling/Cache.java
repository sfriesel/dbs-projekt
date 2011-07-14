package datahandling;

import java.util.HashSet;

public class Cache {

	private static Cache instance = null;
	public HashSet<String> movie = null;
	public HashSet<String> customer = null;

	private Cache() {
		this.movie = new HashSet<String>();
		this.customer = new HashSet<String>();
	}

	public static Cache getInstance() {
		if (instance == null) {
			instance = new Cache();
		}
		return instance;
	}
}
