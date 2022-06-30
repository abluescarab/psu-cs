package edu.pdx.cs410J.agilston;

import edu.pdx.cs410J.agilston.commandline.CommandLineParser;
import edu.pdx.cs410J.lang.Human;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class is represents a <code>Student</code>.
 */
public class Student extends Human {
    protected ArrayList<String> classes;
    protected double gpa;
    protected String gender;

    /**
     * Creates a new <code>Student</code>
     *
     * @param name    The student's name
     * @param classes The names of the classes the student is taking.  A student
     *                may take zero or more classes.
     * @param gpa     The student's grade point average
     * @param gender  The student's gender ("male", "female", or "other", case insensitive)
     */
    public Student(String name, ArrayList<String> classes, double gpa, String gender) {
        super(name);
        this.classes = classes.stream().filter(c -> !Objects.equals(c, ""))
                              .collect(Collectors.toCollection(ArrayList::new));
        this.gpa = gpa;
        this.gender = gender;
    }

    public final ArrayList<String> getClasses() {
        return this.classes;
    }

    public final double getGPA() {
        return this.gpa;
    }

    public final String getGender() {
        return this.gender;
    }

    /**
     * All students say "This class is too much work"
     */
    @Override
    public String says() {
        String pronounString = "";

        switch(this.gender) {
            case "male":
                pronounString = "He says";
                break;
            case "female":
                pronounString = "She says";
                break;
            case "other":
                pronounString = "They say";
                break;
        }

        return pronounString + " \"This class is too much work\"";
    }

    /**
     * Returns a <code>String</code> that describes this
     * <code>Student</code>.
     */
    public String toString() {
        String classString;

        if(this.classes.size() == 1) {
            classString = this.classes.get(0);
        }
        else if(this.classes.size() == 2) {
            classString = this.classes.get(0) + " and " + this.classes.get(1);
        }
        else {
            classString = String.join(", ", this.classes.subList(0, this.classes.size() - 1));
            classString += ", and " + this.classes.get(this.classes.size() - 1);
        }

        return String.format("%s has a GPA of %.2f and is taking %d %s: %s. %s.",
                this.name,
                this.gpa,
                this.classes.size(),
                this.classes.size() > 1 ? "classes" : "class",
                classString,
                says());
    }

    /**
     * Creates a new student object from command line arguments.
     *
     * @param args command line arguments
     * @return new student object
     */
    static Student createStudent(CommandLineParser parser, String[] args) {
        List<String> missingArgs = List.of(
                "name",
                "gender",
                "gpa",
                "classes"
        );
        String name;
        String gender;
        double gpa;
        List<String> classes;

        if(args == null || args.length < missingArgs.size()) {
            throw new IllegalArgumentException("Missing command line arguments: " +
                    String.join(", ", missingArgs.subList(args == null ? 0 : args.length, missingArgs.size())));
        }

        parser.parse(args);

        try {
            gpa = Double.parseDouble(parser.getValueOrDefault("gpa"));
        }
        catch(NumberFormatException e) {
            throw new IllegalArgumentException("Invalid argument: GPA must be a number");
        }

        if(gpa < 0.0) {
            throw new IllegalArgumentException("Invalid argument: GPA cannot be negative");
        }

        return new Student(
                parser.getValueOrDefault("name"),
                new ArrayList<>(Arrays.asList(
                        parser.getValueOrDefault("class_1"),
                        parser.getValueOrDefault("class_2"),
                        parser.getValueOrDefault("class_3"),
                        parser.getValueOrDefault("class_4"),
                        parser.getValueOrDefault("class_5"),
                        parser.getValueOrDefault("class_6"),
                        parser.getValueOrDefault("class_7"),
                        parser.getValueOrDefault("class_8")
                )),
                gpa,
                parser.getValueOrDefault("gender"));
    }

    /**
     * Main program that parses the command line, creates a
     * <code>Student</code>, and prints a description of the student to
     * standard out by invoking its <code>toString</code> method.
     */
    public static void main(String[] args) {
        try {
            CommandLineParser parser = new CommandLineParser("student-2022.0.0.jar");

            parser.addFlag("--help", "Display usage info", false, "-h");
            parser.addFlag("--readme", "Display project readme and exit", false, "-r");
            parser.addArgument("name", "Student name");
            parser.addArgument("gender", "Student gender", "other",
                    new String[] { "male", "female", "other" });
            parser.addArgument("gpa", "Student gpa");
            parser.addArgument("class_1", "First class");
            parser.addArgument("class_2", "Second class", "");
            parser.addArgument("class_3", "Third class", "");
            parser.addArgument("class_4", "Fourth class", "");
            parser.addArgument("class_5", "Fifth class", "");
            parser.addArgument("class_6", "Sixth class", "");
            parser.addArgument("class_7", "Seventh class", "");
            parser.addArgument("class_8", "Eighth class", "");

            // TODO: add list argument

            Student student = createStudent(parser, args);

            if(parser.hasArgument("--help")) {
                parser.printUsage(System.out);
                return;
            }

            if(parser.hasArgument("--readme")) {
                try(InputStream readmeFile = Project1.class.getResourceAsStream("README.txt")) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(readmeFile));
                    StringBuilder readme = new StringBuilder();
                    String line;

                    while((line = reader.readLine()) != null) {
                        readme.append(line);
                    }

                    System.out.println(readme);
                }
                catch(IOException e) {
                    throw new RuntimeException(e);
                }

                return;
            }

            System.out.println(student);
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
