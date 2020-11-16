package Lab4.ex1and2;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStudentSerialization {

    @Test
    public void SerializeTest(){
        try {
            Student s = new Student("Pete", new ArrayList<>(
                    Arrays.asList("Java", "C")));

            File f = new File("src/Lab4/ex1and2/serializedStudent.ser");
            FileOutputStream fOut = new FileOutputStream(f);
            ObjectOutputStream oOut = new ObjectOutputStream(fOut);

            oOut.writeObject(s);

            oOut.close();
            fOut.close();

            FileInputStream fIn = new FileInputStream(f);

            ObjectInputStream oIn = new ObjectInputStream(fIn);

            Student s2 = (Student)(oIn.readObject());

            System.out.println(s2);

            assertEquals(s, s2);

            oIn.close();
            fIn.close();


        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            System.out.println(">:(");
        }
    }

}
