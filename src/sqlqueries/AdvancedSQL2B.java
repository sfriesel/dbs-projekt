package sqlqueries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import view.PrintResult;

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

	private static Queue<QueueEntry> queue;

	private static DBConnector con;
	private static PreparedStatement getAllConectedActorStmt = null;

	private static void prepare() throws SQLException {
		// DBConnection
		DBConnector.configure("localhost", "5432", "movies", "alexa", "dinkel");
		con = DBConnector.getInstance();

		// prepare Statements
		getAllConectedActorStmt = con.connection
				.prepareStatement("SELECT actor, gender FROM Starring WHERE movie in "
						+ "( SELECT movie FROM Starring WHERE actor = ? AND gender = ?)"
						+ "AND actor <> ? ORDER BY actor;");

		queue = new LinkedList<QueueEntry>();
	}

	public static void execute(String actorFrom, String genFrom,
			String actorTo, String genTo) throws SQLException {

		// add actorFrom to queue, the first one has no predecessor
		QueueEntry qe = new QueueEntry(new Tupel(actorFrom, genFrom), new ArrayList<Tupel>());
		queue.offer(qe);

		boolean found = false;

		// as long as the queue is not empty
		while (!queue.isEmpty()) {

			QueueEntry value = queue.poll();
			actorFrom = value.actor.name;
			genFrom = value.actor.gender;

			getAllConectedActorStmt.setString(1, actorFrom);
			getAllConectedActorStmt.setString(2, genFrom);
			getAllConectedActorStmt.setString(3, actorFrom);

			ResultSet actorRS = getAllConectedActorStmt.executeQuery();

			// have a look at all actors
			while (actorRS.next()) {

				String name = actorRS.getString("actor");
				String gender = actorRS.getString("gender");

				// if we found the actor already
				if (name.equals(actorTo) && gender.equals(genTo)) {
					
					System.out.println("Path:");
					for (int i = 0; i < value.pre.size(); i++) {
						System.out.println(value.pre.get(i).name);
					}
					found = true;
					return;
				} else {

					// add it to the queue and add vorgänger
					ArrayList<Tupel> newPredecessors = (ArrayList<Tupel>) value.pre
							.clone();
					newPredecessors.add(value.actor);
					QueueEntry newEntry = new QueueEntry(
							new Tupel(name, gender), newPredecessors);
					queue.offer(newEntry);
				}
			}
			if (found == true) {
				queue = null;
				return;
			}
		}
	}

	public static void main(String[] args) {
		try {
			prepare();
			execute("Depp, Johnny", "m", "Dalton, Timothy", "m");
			execute("Depp, Johnny", "m", "Diehl, August", "m");
			execute("Murray, Bill (I)", "m", "Stallone, Sylvester", "m");
			execute("Norton, Edward (I)", "m", "Cheadle, Don", "m");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
