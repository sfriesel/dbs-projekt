package sqlqueries;

public class Tupel {
	String name;
	String gender;

	public Tupel(String name, String gender) {
		this.name = name;
		this.gender = gender;
	}

	@Override
	public boolean equals(Object obj) {
		Tupel t = (Tupel) obj;
		return (name.equals(t.name) && gender.equals(t.gender));
	}

	@Override
	public String toString() {
		return name + "(" + gender + ")";
	}

	@Override
	public int hashCode() {
		return name.hashCode() + gender.hashCode();
	}
}