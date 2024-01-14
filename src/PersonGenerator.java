import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class PersonGenerator {

    public static class Person {
        String id;
        String firstName;
        String lastName;
        String title;
        int yearOfBirth;
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        ArrayList<Person> people = new ArrayList<Person>();

        while(true) {
            Person newPerson = new Person();
            newPerson.id = SafeInput.getRegExString(in, "Please Enter your persons ID", "[0-9]+");
            newPerson.firstName = SafeInput.getRegExString(in, "Please Enter your persons first name", "[a-zA-Z]+");
            newPerson.lastName = SafeInput.getRegExString(in, "Please Enter your persons last name", "[a-zA-Z]+");
            newPerson.title = SafeInput.getRegExString(in, "Please Enter your persons title", "[a-zA-Z]+");
            newPerson.yearOfBirth = SafeInput.getInt(in, "Please Enter your persons Year of Birth");
            people.add((newPerson));
            System.out.println("Finished collecting input for " + newPerson.firstName + " " + newPerson.lastName);
            if(!SafeInput.getYNConfirm(in, "Would you like to add another person?")) {
                break;
            }
        }

        File workingDir = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDir.getPath() + "PersonTestData.txt");

        try {
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, StandardOpenOption.CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            for(int i = 0; i < people.size(); i++) {
                Person person = people.get(i);
                String entry = person.id + "," + person.firstName + "," + person.lastName + "," + person.title + ".," + Integer.toString(person.yearOfBirth);
                writer.write(entry, 0, entry.length());
                writer.newLine();
            }
            writer.close();
            System.out.println("Data file written!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
