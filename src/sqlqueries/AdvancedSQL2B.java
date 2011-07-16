package sqlqueries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import cli.MenuEntry;

import database.DBConnector;

/**
 * Sei jeder Schauspieler mit allen anderen Schauspielern verbunden, mit denen
 * er in einem Film (in 2010/2011) zusammengearbeitet hat. Des Weiteren seien
 * zwei Schauspieler A und B miteinander verbunden, wenn ein Schauspieler C
 * existiert, der sowohl mit A als auch mit B verbunden ist. Schreiben Sie ein
 * Java-Programm, das die kürzeste Verbidung zwischen zwei Schauspielern
 * ermittelt. Ermitteln Sie nun die kürzesten Verbindungen zwischen folgenden
 * Paaren: i. Johnny Depp und Timothy Dalton ii. Johnny Depp und August Diehl
 * iii. Bill Murray und Sylvester Stallone iv. Edward Norton und Don Cheadle
 * 
 * @author alexa
 * 
 */
public class AdvancedSQL2B implements MenuEntry {

	PreparedStatement getAllConnectedActorStmt;

	@Override
	public String getName() {
		return "Kürzeste Verbindung";
	}

	@Override
	public String getDescription() {
		return "Die kürzesten Verbindungen zwischen (Johnny Depp|Timothy Dalton), (Johnny Depp|August Diehl), "
				+ "(Bill Murray|Sylvester Stallone) and (Edward Norton|Don Cheadle):";
	}

	public void execute() throws Exception {

		String[][] actorList = { { "Depp, Johnny", "m", "Diehl, August", "m" },
				{ "Depp, Johnny", "m", "Dalton, Timothy", "m" },
				{ "Murray, Bill (I)", "m", "Stallone, Sylvester", "m" },
				{ "Norton, Edward (I)", "m", "Cheadle, Don", "m" } };

		System.out.println("Started calculating ..." + "\n");

		// calculate all shortestPathes
		for (int i = 0; i < actorList.length; i++) {

			ArrayList<Tupel> result = shortestPath(new Tupel(actorList[i][0],
					actorList[i][1]), new Tupel(actorList[i][2],
					actorList[i][3]));

			// print results
			System.out.println("[" + actorList[i][0] + "]" + " and " + "["
					+ actorList[i][2] + "]");

			int length = actorList[i][0].toCharArray().length
					+ actorList[i][2].toCharArray().length + 9;
			for (int k = 0; k <= length; k++)
				System.out.print("-");
			System.out.print("\r");

			for (int j = 0; j < result.size() - 1; j++) {
				System.out.print(result.get(j) + " --> ");
			}
			System.out.println(result.get(result.size()-1) + "\n");
		}

		getAllConnectedActorStmt.close();
	}

	/**
	 * Implements a BFS to find the shortest path between two actors. If there
	 * is a path, it returns a list of all actors found on the way. Returns null
	 * if nothing is found.
	 * 
	 * @param actorFrom
	 * @param actorTo
	 * @return
	 * @throws SQLException
	 */
	private ArrayList<Tupel> shortestPath(Tupel actorFrom, Tupel actorTo)
			throws SQLException {

		boolean found = false;

		HashMap<Tupel, Tupel> path = new HashMap<Tupel, Tupel>();
		Queue<Tupel> queue = new LinkedList<Tupel>();
		HashSet<Tupel> visited = new HashSet<Tupel>();

		// add actorFrom to queue, the first one has no predecessor
		queue.offer(actorFrom);

		// as long as the queue is not empty
		bfs: while (!queue.isEmpty()) {

			// get the next actor from the queue
			Tupel value = queue.poll();

			// visited :)
			visited.add(value);

			ResultSet actorRS = getConnectedActors(value);

			// have a look at all actors
			while (actorRS.next()) {

				Tupel newActor = new Tupel(actorRS.getString("actor"),
						actorRS.getString("gender"));

				// only if he was not visited yet
				if (!visited.contains(newActor)) {

					// if we found the actor already
					if (newActor.equals(actorTo)) {
						path.put(actorTo, value);
						actorRS.close();
						found = true;
						break bfs;

					} else {

						// add it to the queue and add a tupel to the path
						queue.offer(newActor);
						path.put(newActor, value);
						visited.add(newActor);
					}
				}
			}
			actorRS.close();
		}

		if (found) {
			ArrayList<Tupel> result = new ArrayList<Tupel>();

			// find the way from actorTo to actorFrom
			do {
				result.add(actorTo);
				actorTo = path.get(actorTo);
			} while (actorTo != null);

			return result;
		}

		return null;
	}

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SQLException
	 */
	private ResultSet getConnectedActors(Tupel value) throws SQLException {

		// DBConnection
		DBConnector con = DBConnector.getInstance();

		// prepare Statements
		getAllConnectedActorStmt = con.connection
				.prepareStatement("SELECT actor, gender FROM Starring WHERE movie in "
						+ "( SELECT movie FROM Starring WHERE actor = ? AND gender = ? ORDER BY movie)"
						+ "AND NOT (actor = ? AND gender = ?) ORDER BY actor;");

		getAllConnectedActorStmt.setString(1, value.name);
		getAllConnectedActorStmt.setString(2, value.gender);
		getAllConnectedActorStmt.setString(3, value.name);
		getAllConnectedActorStmt.setString(4, value.gender);

		return getAllConnectedActorStmt.executeQuery();
	}
}
