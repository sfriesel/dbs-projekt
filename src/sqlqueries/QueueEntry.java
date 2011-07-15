package sqlqueries;

import java.util.ArrayList;

public class QueueEntry {
	Tupel actor;
	ArrayList<Tupel> pre;

	public QueueEntry(Tupel actor, ArrayList<Tupel> pre) {
		this.actor = actor;
		this.pre = pre;
	}
}