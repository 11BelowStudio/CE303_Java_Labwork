package books;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Book implements Comparable<Book>, Cloneable, Serializable {

	public final String isbn;
	public final double price;
	public final String title;
	public final List<Author> authors;

	public List<Author> getAuthors() {
		return authors;
	}

	public String getTitle() {
		return title;
	}

	public String getIsbn() {
		return isbn;
	}

	public double getPrice() {
		return price;
	}

	// default constructor needed by some frameworks
	public Book() {
		isbn = null;
		price = 0;
		title = null;
		authors = null;
	}

	public Book(String title, String isbn, double price, List<Author> authors) {
		this.title = title;
		this.isbn = isbn == null ? "" : isbn;
		this.price = price;
		this.authors = new ArrayList<>();
		if (authors != null) {
			this.authors.addAll(authors);
		}
	}

	// varargs constructor is convenient to define sample books
	public Book(String title, String isbn, double price, Author... authors) {
		this(title, isbn, price, Arrays.asList(authors));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != Book.class)
			return false;
		else {
			Book other = (Book) obj;
			return this.isbn.equals(other.isbn) && this.title.equals(other.title)
					&& Objects.equals(this.authors, other.authors) && this.price == other.price;
		}
	}

	@Override
	public String toString() {
		String authorsString = "";
		if (authors != null) {
			authorsString += authors.get(0).getName();
			for (int i = 1; i < authors.size() - 1; i++)
				authorsString += ", " + authors.get(i).getName();
			if (authors.size() > 1) {
				authorsString += " and " + authors.get(authors.size() - 1).getName();
			}
		}
		return authorsString + ": " + title + ". ISBN " + isbn + " " + price;
	}

	@Override
	public int compareTo(Book o) {
		return this.isbn.compareTo(o.isbn);
	}

	/* Shallow clone method */

	/*
	 * public Book clone() { try { return (Book) super.clone(); } catch
	 * (CloneNotSupportedException e) { e.printStackTrace(); return null; } }
	 */

	public Book clone() {
		return new Book(this.title, this.isbn, this.price, this.authors);
	}

}