package edu.pdx.cs410J.agilston;

import edu.pdx.cs410J.agilston.commandline.CommandLineParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static edu.pdx.cs410J.agilston.Student.createStudent;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThrows;

/**
 * Unit tests for the Student class.  In addition to the JUnit annotations,
 * they also make use of the <a href="http://hamcrest.org/JavaHamcrest/">hamcrest</a>
 * matchers for more readable assertion statements.
 */
public class StudentTest {
    @Test
    void studentNamedPatIsNamedPat() {
        String name = "Pat";
        var student = new Student(name, new ArrayList<>(), 0.0, "Doesn't matter");
        assertThat(student.getName(), equalTo(name));
        assertThat(student.getGPA(), equalTo(0.0));
        assertThat(student.getClasses(), equalTo(new ArrayList<String>()));
        assertThat(student.getGender(), equalTo("Doesn't matter"));
    }

    private Student createStudentNamed(String name) {
        return new Student(name, new ArrayList<>(), 0.0, "Doesn't matter");
    }

    private Student createStudentNamedWithGender(String name, String gender) {
        return new Student(name, new ArrayList<>(), 0.0, gender);
    }

    private String[] createArgsArray(String name, String gender, String gpa, String... classes) {
        return new ArrayList<String>() {{
            add(name);
            add(gender);
            add(gpa);
            addAll(List.of(classes));
        }}.toArray(new String[0]);
    }

    private String[] createArgsArray() {
        return createArgsArray("Name", "female", "3.5", "Algorithms", "Operating Systems",
                "Java");
    }

    private String[] createArgsArrayWithName(String name) {
        return createArgsArray(name, "female", "3.5", "Algorithms", "Operating Systems", "Java");
    }

    private String[] createArgsArrayWithGender(String gender) {
        return createArgsArray("Name", gender, "3.5", "Algorithms", "Operating Systems", "Java");
    }

    private String[] createArgsArrayWithGpa(String gpa) {
        return createArgsArray("Name", "female", gpa, "Algorithms", "Operating Systems", "Java");
    }

    private String[] createArgsArrayWithClasses(String... classes) {
        return createArgsArray("Name", "female", "3.5", classes);
    }

    private CommandLineParser createCommandLineParser() {
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

        return parser;
    }

    @Test
    void allStudentsSayThisClassIsTooMuchWork() {
        // GIVEN that there is a student
        var student = createStudentNamedWithGender("Student", "female");

        // WHEN Pat is asked to say something
        // THEN Pat says "This class is too much work"
        assertThat(student.says(), equalTo("She says \"This class is too much work\""));
    }

    @Test
    void zeroArgumentsReturnsMissingCommandLineArguments() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, ()
                -> createStudent(null, null));
        assertThat(e.getMessage(), equalTo("Missing command line arguments: name, gender, gpa, classes"));
    }

    @Test
    void oneArgumentsReturnsMissingCommandLineArguments() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, ()
                -> createStudent(null, new String[] { "Name" }));
        assertThat(e.getMessage(), equalTo("Missing command line arguments: gender, gpa, classes"));
    }

    @Test
    void twoArgumentsReturnsMissingCommandLineArguments() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, ()
                -> createStudent(null, new String[] { "Name", "female" }));
        assertThat(e.getMessage(), equalTo("Missing command line arguments: gpa, classes"));
    }

    @Test
    void threeArgumentsReturnsMissingCommandLineArguments() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> createStudent(null, new String[] { "Name", "female", "3.0" }));
        assertThat(e.getMessage(), equalTo("Missing command line arguments: classes"));
    }

    @Test
    void gpaLessThanZeroFails() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> createStudent(createCommandLineParser(), new String[] { "Name", "female", "-1.0", "Algorithms" }));
        assertThat(e.getMessage(), equalTo("Invalid argument: GPA cannot be negative"));
    }

    @Test
    void gpaThatIsNotANumberFails() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> createStudent(createCommandLineParser(), new String[] { "Name", "female", "abc", "Algorithms" }));
        assertThat(e.getMessage(), equalTo("Invalid argument: GPA must be a number"));
    }

    @Test
    void studentWithInvalidGenderCreatedWithDefaultValue() {
        Student student = createStudent(createCommandLineParser(), createArgsArrayWithGender("test"));
        assertThat(student.getGender(), equalTo("other"));
    }
}
