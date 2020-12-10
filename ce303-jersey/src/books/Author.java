package books;

import java.io.Serializable;

public class Author implements Comparable<Author>, Serializable {

	public final String id;
	public final String name;
	
	public Author(String authorId, String name) {
		this.id = authorId;
		this.name = name;
	}
	 
  // default constructor needed by some frameworks 
	public Author() {
		id = null;
		name = null; 
	}

	@Override
	public String toString() {
		return "Author[" + id + "," + name + "]";
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object otherObject) {
		if (otherObject == null || otherObject.getClass() != Author.class)
			return false;
		else {
			Author other = (Author) otherObject;
			return this.id.equals(other.id) && this.name.equals(other.name); 
		}
	}

	@Override
	public int compareTo(Author other) {
		return this.id.compareTo(other.id);
	}
}
