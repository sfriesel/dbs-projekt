package datahandling;

import java.util.HashSet;

public class Cache {

	private static Cache instance = null;

	public HashSet<String> movie = null;
	public HashSet<String> actor = null;
	public HashSet<String> customer = null;
	public HashSet<String> director = null;
	public HashSet<String> directedBy = null;
	public HashSet<String> location = null;
	public HashSet<String> shotIn = null;

	private Cache() {
		movie = new HashSet<String>();
		actor = new HashSet<String>();
		customer = new HashSet<String>();
		director = new HashSet<String>();
		directedBy = new HashSet<String>();
		location = new HashSet<String>();
		shotIn = new HashSet<String>();
	}

	public static Cache getInstance() {
		if (instance == null) {
			instance = new Cache();
		}
		return instance;
	}
}
