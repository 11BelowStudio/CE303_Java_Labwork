package Lab5.ex1;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStudentGsonSerialization {

    @Test
    public void SerializeTest(){
        try {

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            Student s = new Student("Pete", new ArrayList<>(
                    Arrays.asList("Java", "C")));

            File f = new File("src/Lab5/ex1/gsonSerializedStudent.json");

            FileWriter fWriter = new FileWriter(f);
            fWriter.write(gson.toJson(s));

            fWriter.close();

            BufferedReader bReader = new BufferedReader(new FileReader(f));

            Student s2 = gson.fromJson(bReader, Student.class);

            bReader.close();


            assertEquals(s, s2);


        } catch (IOException e){
            e.printStackTrace();
            System.out.println(">:(");
        }
    }

}
