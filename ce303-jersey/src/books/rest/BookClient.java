package books.rest;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;
// import org.glassfish.jersey.logging.LoggingFeature;

import com.google.gson.Gson;

import books.Book;
import webserver.GsonMessageBodyHandler;

public class BookClient {
	public final WebTarget target;

	public BookClient(String bookUrl) {
		ClientConfig config = new ClientConfig(GsonMessageBodyHandler.class);
	/*
		config.property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_CLIENT,
				LoggingFeature.Verbosity.PAYLOAD_ANY);
		config.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_CLIENT, 
				"WARNING");
		*/ 
		target = ClientBuilder.newClient(config).target(bookUrl);

	}

	public Book getBook(String isbn) {
		return target.path(isbn).request(MediaType.APPLICATION_JSON).get(Book.class);
	}

	public Book getBookManualGson(String isbn) {
		Gson gson = new Gson();
		String response = target.path(isbn).request(MediaType.APPLICATION_JSON).get(String.class);
		return gson.fromJson(response, Book.class);
	}

	public String getBookPlainString(String isbn) {
		return target.path("plain").path(isbn).request(MediaType.TEXT_PLAIN_TYPE).get(String.class);
	}

}
