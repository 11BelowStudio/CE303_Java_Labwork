package books.rest;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import books.Book;

@Path("/book")
public class BookServer {

	private final static Map<String, Book> BOOKS = books.BooksJson.bookMap(); 
	public static final Gson GSON = new Gson();

	// Note: default life-cycle of this class is "per request",
	// This means that a new instance is created for each request
	public BookServer() {
	};

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{isbn}")
	public Book getBookAsJson(@PathParam("isbn") String isbn) {
		return BOOKS.get(isbn);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("plain/{isbn}")
	public String getBookAsText(@PathParam("isbn") String isbn) {
		return GSON.toJson(BOOKS.get(isbn));
	}
}
