package edu.pdx.cs410J.agilston;

import edu.pdx.cs410J.lang.Human;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

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
   * @param name                                                                    
   *        The student's name                                                      
   * @param classes                                                                 
   *        The names of the classes the student is taking.  A student              
   *        may take zero or more classes.                                          
   * @param gpa                                                                     
   *        The student's grade point average                                       
   * @param gender                                                                  
   *        The student's gender ("male", "female", or "other", case insensitive)
   */                                                                               
  public Student(String name, ArrayList<String> classes, double gpa, String gender) {
    super(name);
    this.classes = classes;
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
   * @param args command line arguments
   * @return new student object
   */
  private static Student createStudent(String[] args) {
    String missingArgs = "";
    String name;
    String gender;
    double gpa;
    ArrayList<String> classes;

    if(args.length < 1) {
      missingArgs += "<name> ";
    }

    if(args.length < 2) {
      missingArgs += "<gender> ";
    }

    if(args.length < 3) {
      missingArgs += "<gpa> ";
    }

    if(args.length < 4) {
      missingArgs += "<classes>";
      throw new IllegalArgumentException("Missing command line arguments: " + missingArgs);
    }

    name = args[0];
    gender = args[1].toLowerCase();

    if(!Objects.equals(gender, "male")
            && !Objects.equals(gender, "female")
            && !Objects.equals(gender, "other")) {
      throw new IllegalArgumentException("Incorrect argument: \"gender\" must be \"male\", \"female\", or \"other\"");
    }

    try {
      gpa = Double.parseDouble(args[2]);
    }
    catch(NumberFormatException e) {
      throw new IllegalArgumentException("Incorrect argument: GPA must be a number");
    }

    if(gpa < 0.0) {
      throw new IllegalArgumentException("Incorrect argument: GPA cannot be negative");
    }

    classes = new ArrayList<>(Arrays.asList(args).subList(3, args.length));
    return new Student(name, classes, gpa, gender);
  }

  /**
   * Main program that parses the command line, creates a
   * <code>Student</code>, and prints a description of the student to
   * standard out by invoking its <code>toString</code> method.
   */
  public static void main(String[] args) {
    try {
      Student student = createStudent(args);
      System.out.println(student);
    }
    catch(Exception e) {
      System.err.println(e.getMessage());
    }
  }
}
