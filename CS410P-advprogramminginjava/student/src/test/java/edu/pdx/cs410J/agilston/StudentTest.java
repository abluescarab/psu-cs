package edu.pdx.cs410J.agilston;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Unit tests for the Student class.  In addition to the JUnit annotations,
 * they also make use of the <a href="http://hamcrest.org/JavaHamcrest/">hamcrest</a>
 * matchers for more readable assertion statements.
 */
public class StudentTest
{

  @Test
  void studentNamedPatIsNamedPat() {
    String name = "Pat";
    var student = new Student(name, new ArrayList<>(), 0.0, "Doesn't matter");
    assertThat(student.getName(), equalTo(name));
    assertThat(student.getGPA(), equalTo(0.0));
    assertThat(student.getClasses(), equalTo(new ArrayList<String>()));
    assertThat(student.getGender(), equalTo("Doesn't matter"));
  }

}
