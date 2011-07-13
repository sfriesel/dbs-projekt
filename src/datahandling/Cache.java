package datahandling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cache {

	private static Cache instance = null;

	public ArrayList<String> movie = null;
	public ArrayList<String> actor = null;
	public ArrayList<String> starring = null;

	private Cache() {
		movie = new ArrayList<String>();
		actor = new ArrayList<String>();
		starring = new ArrayList<String>();
	}

	public static Cache getInstance() {
		if (instance == null) {
			instance = new Cache();
		}
		return instance;
	}
}
