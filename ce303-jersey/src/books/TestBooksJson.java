package books;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestBooksJson {

	public static final String BOOKS_JSON_FILE = "data/books.json";
	public static final String BOOKS_JSON_COPY_FILE = "data/books_copy.json";

	public static List<Book> books;

	@BeforeClass
	public static void setUp() throws IOException {
		books = BooksJson.readBooks(BOOKS_JSON_FILE);
	}

	@Test
	public void nonEmpty() {
		assertFalse("books empty", books.isEmpty());
	}

	@Test
	public void testRoundTrip() throws IOException {
		BooksJson.writeBooks(books, BOOKS_JSON_COPY_FILE);
		List<Book> booksCopy = BooksJson.readBooks(BOOKS_JSON_COPY_FILE);
		System.out.println(books);
		assertTrue(books + " not equals " + booksCopy, books.equals(booksCopy));
		for (Book b : booksCopy) {
			assertTrue("book type incorrect: ", b.getClass().equals(Book.class));
		}
	}

}
