import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class PersonReader {
    //Yes I'm redeclaring this until I know the format of this class. Normally I would setup my classes differently
    public static class Person {
        String id;
        String firstName;
        String lastName;
        String title;
        int yearOfBirth;
    }
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        Scanner inFile;
        String line;
        Path target = new File(System.getProperty("user.dir")).toPath();
        target = target.resolve("src");
        // set the chooser to the project src directory
        chooser.setCurrentDirectory(target.toFile());

        try  // Code that might trigger the exception goes here
        {

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                target = chooser.getSelectedFile().toPath();  // this is a File object not a String filename

                inFile = new Scanner(target);

                ArrayList<Person> people = new ArrayList<Person>();

                while (inFile.hasNextLine()) {
                    Person parsed = ParsePersonData(inFile.nextLine());
                    if(parsed == null) {
                        System.out.println("Cannot continue parsing");
                        break;
                    }
                    people.add(parsed);
                }

                PrintPeople(people);

                inFile.close();
            } else {
                System.out.println("Sorry, you must select a file! Termininating!");
                System.exit(0);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found Error");
            e.printStackTrace();
        } catch (IOException e) // code to handle this exception
        {
            System.out.println("IOException Error");
            e.printStackTrace();
        }
    }

    public static Person ParsePersonData(String line) {
        Person person = new Person();
        String[] arr = line.split(",");
        if(arr.length != 5) {
            System.out.println("Invalid line, cannot parse data for person");
            return null;
        }
        person.id = arr[0];
        person.firstName = arr[1];
        person.lastName = arr[2];
        person.title = arr[3];
        person.yearOfBirth = Integer.parseInt(arr[4]);
        return person;
    }

    public static void PrintPeople(ArrayList<Person> arrayList) {
        System.out.println(String.format("%s %12s %11s %5s %5s", "#ID", "Firstname", "Lastname", "Title", "YOB"));
        System.out.println(String.format("%0" + 40 + "d", 0).replace("0", "="));
        System.out.println();
        for(int i = 0; i < arrayList.size(); i++) {
            Person p = arrayList.get(i);
            System.out.println(String.format("%s %9s %11s %5s %5d", p.id, p.firstName, p.lastName, p.title, p.yearOfBirth));
        }
    }


}