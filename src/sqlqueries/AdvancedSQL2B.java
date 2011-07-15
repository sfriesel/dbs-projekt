package sqlqueries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

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
public class AdvancedSQL2B {

	private static DBConnector con;
	private static PreparedStatement getAllConectedActorStmt = null;

	private static void prepare() throws SQLException {
		// DBConnection
		DBConnector.configure("localhost", "5432", "movies", "alexa", "dinkel");
		con = DBConnector.getInstance();

		// prepare Statements
		getAllConectedActorStmt = con.connection
				.prepareStatement("SELECT actor, gender FROM Starring WHERE movie in "
						+ "( SELECT movie FROM Starring WHERE actor = ? AND gender = ? ORDER BY movie)"
						+ "AND NOT (actor = ? AND gender = ?) ORDER BY actor;");
	}

	public static void execute() throws SQLException {

		String[][] actorList = {
				{ "Depp, Johnny", "m", "Diehl, August", "m" },
				{ "Depp, Johnny", "m", "Dalton, Timothy", "m" },
				{ "Murray, Bill (I)", "m", "Stallone, Sylvester", "m" },
				{ "Norton, Edward (I)", "m", "Cheadle, Don", "m" }
				};

		// calculate all shortestPathes
		for (int i = 0; i < actorList.length; i++) {
			ArrayList<Tupel> result = shortestPath(new Tupel(actorList[i][0],
					actorList[i][1]), new Tupel(actorList[i][2],
					actorList[i][3]));

			// TODO print the results in a nice way
			for (int j = 0; j < result.size(); j++) {
				System.out.println(result.get(j).toString());
			}
			System.out.println("----");
		}
	}

	private static ArrayList<Tupel> shortestPath(Tupel actorFrom, Tupel actorTo)
			throws SQLException {

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

		ArrayList<Tupel> result = new ArrayList<Tupel>();

		// find the way from actorTo to actorFrom
		do {
			result.add(actorTo);
			actorTo = path.get(actorTo);
		} while (actorTo != null);

		return result;
	}

	private static ResultSet getConnectedActors(Tupel value)
			throws SQLException {
		getAllConectedActorStmt.setString(1, value.name);
		getAllConectedActorStmt.setString(2, value.gender);
		getAllConectedActorStmt.setString(3, value.name);
		getAllConectedActorStmt.setString(4, value.gender);
		return getAllConectedActorStmt.executeQuery();
	}

	public static void main(String[] args) {
		try {
			prepare();
			execute();
			System.out.println("Main ende.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.getNextException().printStackTrace();
		}
	}
}
