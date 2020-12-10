package Lab7.ex2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Staff {
	public String name;
	public String phoneNumber;
	public String roomNumber;

	public Staff(String name, String phoneNumber, String roomNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.roomNumber = roomNumber;
	}

	public Staff(String line) {
		String[] tokens = line.trim().split("\\s*,\\s*");
		this.name = tokens[0];
		this.phoneNumber = tokens[1];
		this.roomNumber = tokens[2];
	}

	@Override
	public String toString() {
		return name + ", " + phoneNumber + ", " + roomNumber;
	}
	
	public String getRoomNumber() {
		return roomNumber; 
	}

	public static void main(String[] args) {
		Map<String, List<Staff>> staffMap = roomStaffMap(Paths.get("src/Lab7/ex2/staff.csv"));
		System.out.println(staffMap);
	}

	public static Comparator<Staff> compareByPhone = Comparator.comparing((Staff st) -> st.phoneNumber)
			.thenComparing(Comparator.comparing(st -> st.name));



	/**
	 * This should:
	 * 1. Read a stream of lines from the file
	 * 2. Transform it into a stream of staff objects
	 * 3. Collect it into a map from room numbers to lists of staff objects
	 * @param path
	 * @return
	 */
	public static  Map<String,List<Staff>> roomStaffMap(Path path){
		try {
			return Files.lines(path).map(Staff::new).collect(
					Collectors.groupingBy(Staff::getRoomNumber)
			);
		} catch (IOException e){
			System.out.println(">:(");
		}
		return null;
	}
}
