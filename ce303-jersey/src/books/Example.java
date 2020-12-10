package books;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Example {

	public static final Author KERNIGHAN = new Author("KERN", "Kernighan, Brian");
	public static final Author KNUTH = new Author("KNUT", "Knuth, Donald E.");
	public static final Author RITCHIE = new Author("RITC", "Ritchie, Dennis");
	public static final Author STROUSTRUP = new Author("STRO", "Stroustrup, Bjarne");
	public static final Author WIRTH = new Author("WIR", "Wirth, Niklaus");

	public static final List<Book> BOOKS = new ArrayList<Book>();

	static {
		BOOKS.addAll(Arrays.asList(new Book("The C Programming Language", "0-13-110362-8", 41.99, KERNIGHAN, RITCHIE),
				new Book("The C++ Programming Language", "0-201-70073-5", 64.99, STROUSTRUP),
				new Book("The Art of Computer Programming vol. 1", "0-201-89683-4", 59.99, KNUTH),
				new Book("The Art of Computer Programming vol. 2", "0-201-89684-2", 59.99, KNUTH),
				new Book("Algorithms + Data Structures = Programs", "978-0-13-022418-7", 40.99, WIRTH)));
	}

	public static void main(String[] args) {
		for (Book b : BOOKS)
			System.out.println(b);
	}
}
