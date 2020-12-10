package books;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class BooksJson {

	public static final String BOOKS_JSON_FILE = "data/books.json";
	public static final String BOOKS_JSON_COPY_FILE = "data/books_copy.json";

	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	public static Map<String, Book> bookMap() {
		Map<String, Book> result = new ConcurrentHashMap<String, Book>();
		try {
			for (Book b : BooksJson.readBooks("data/books.json"))
				result.put(b.getIsbn(), b);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static List<Book> readBooks(String fileName) throws IOException {
		Path path = Paths.get(fileName);
		BufferedReader reader = Files.newBufferedReader(path);
		Type t = new TypeToken<List<Book>>() {
		}.getType();
		List<Book> result = GSON.fromJson(reader, t);
		reader.close();
		return result;
	}

	public static void writeBooks(List<Book> books, String fileName) throws IOException {
		Path path = Paths.get(fileName);
		BufferedWriter writer = Files.newBufferedWriter(path);
		GSON.toJson(books, writer);
		writer.close();
	}

	public static void main(String[] args) throws IOException {
		List<Book> books = BooksJson.readBooks(BOOKS_JSON_FILE);
		for (Book book : books)
			System.out.println(book);
		BooksJson.writeBooks(books, BOOKS_JSON_COPY_FILE);
		System.out.println("Done");
	}
}
