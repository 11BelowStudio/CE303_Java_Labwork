package books;

import java.util.List;

/* 
 * Store (= "create"), load (="read") and delete methods for a database with books and authors
 *  
 * Store methods throw an exception if objects could not be saved, 
 * for example because of a primary key constraint violation
 * 
 * Load-methods return null if an object could not be retrieved
 * 
 * Find-methods return empty collections if no object could be found
 * 
 * Delete-methods return the number of deleted rows
 * they throw an exception if the object could not be deleted due 
 * to foreign key constraints etc 
 */

public interface BookDb {

	/* author */

	public boolean storeAuthor(Author author);

	public Author loadAuthor(String personId);

	public boolean deleteAuthor(String id);

	/* bulk operations */

	public boolean storeAuthors(Iterable<Author> author);

	public List<Author> loadAllAuthors();

	/* books */

	public boolean storeBook(Book book);

	public Book loadBook(String id);

	public boolean deleteBook(String id);

	/* bulk operations */

	public boolean storeBooks(Iterable<Book> books);

	public List<Book> loadAllBooks();

	/* find/ search operations */

	public List<Book> findBooksByAuthor(Author a);
	
	/* delete all rows from one table */ 
	
	public boolean deleteDataFrom(String ...tables); 

}
