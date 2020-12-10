package books.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import books.Book;
import books.BooksJson;
import webserver.TomcatServer;

public class TestBook {
	public BookClient client; 
	public final static String ISBN = "0-201-89683-4";
	public final static Book BOOK = BooksJson.bookMap().get(ISBN); 
	
	public TestBook() {
		client = new BookClient(TomcatServer.BOOK_URL); 
	}
	
	@Test
	public void getBook() {
		Book b = client.getBook(ISBN);
		System.out.println(b);
		assertEquals(b,BOOK); 
	}

	@Test
	public void getBookManualGson() {
		Book b = client.getBookManualGson(ISBN);
		assertEquals(b,BOOK); 
	}

	@Test
	public void getBookAsString() {
		String response = client.getBookPlainString(ISBN);
		assertTrue(response.contains(ISBN)); 		
	}

}
