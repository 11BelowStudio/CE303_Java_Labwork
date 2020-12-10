package books;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class TestBookCloning {

	Book sampleBook, sampleClone;

	@Before
	public void setUp() {
		Book b = Example.BOOKS.get(0);
		Author[] authors = new Author[b.getAuthors().size()]; 
		sampleBook = new Book(b.getTitle(), b.getIsbn(), b.getPrice(), 
				b.getAuthors().toArray(authors)); 
		sampleClone = sampleBook.clone();
	}

	@Test
	public void testShallowClone() throws IOException {
		assertEquals(sampleBook, sampleClone); 
		assertFalse(sampleBook==sampleClone); 
		sampleClone.getAuthors().remove(0);
		assertEquals("Shallow cloning", sampleBook, sampleClone);
	}

	@Test
	public void testDeepClone() throws IOException {
		assertEquals(sampleBook, sampleClone); 
		assertFalse(sampleBook==sampleClone); 
		sampleClone.getAuthors().remove(0);
		assertEquals("Deep cloning", Example.BOOKS.get(0), sampleBook);
	}

}
