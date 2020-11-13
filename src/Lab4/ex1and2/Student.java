package Lab4.ex1and2;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/** This is a simple Student class, containing the name of the student and a list of the programming
 * languages this student knows. **/
public class Student implements Cloneable, Serializable {

    private final String name;
    private ArrayList<String> programmingLanguages;

    public Student(String name, ArrayList<String> programmingLanguages){
        this.name = name;
        this.programmingLanguages = programmingLanguages;
    }

    public ArrayList<String> getProgrammingLanguages(){
        return programmingLanguages;
    }

    public void addProgrammingLanguage(String language){
        programmingLanguages.add(language);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student1 = (Student) o;
        return Objects.equals(name, student1.name) &&
                Objects.equals(programmingLanguages, student1.programmingLanguages);
    }

    public Student clone() {

        Student clonedStudent = new Student(name, new ArrayList<String>());
        for (String s: programmingLanguages) {
            clonedStudent.addProgrammingLanguage(s);
        }
        return clonedStudent;

    }




    public static void main (String[] args) {
    }

}