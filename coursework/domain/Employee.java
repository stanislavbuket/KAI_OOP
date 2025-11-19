/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Coursework
 */
import java.util.*;

/** Represents an employee entity, containing all personal and professional details. */
public class Employee {
  private final String id;
  private String firstName;
  private String lastName;
  private String salaryAccountNumber;
  private int workExperienceYears;
  private Department department;
  private Position position;
  private List<Project> projects;

  /**
   * Constructs a new Employee.
   *
   * @param id A unique identifier for the employee.
   * @param firstName The employee's first name.
   * @param lastName The employee's last name.
   * @param salaryAccountNumber The account number for salary payments.
   * @param workExperienceYears The number of years of work experience.
   * @param department The department the employee belongs to.
   * @param position The employee's job position.
   */
  public Employee(
      String id,
      String firstName,
      String lastName,
      String salaryAccountNumber,
      int workExperienceYears,
      Department department,
      Position position) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.salaryAccountNumber = salaryAccountNumber;
    this.workExperienceYears = workExperienceYears;
    this.department = department;
    this.position = position;
    this.projects = new ArrayList<>();
  }

  /**
   * Gets the ID of the employee.
   *
   * @return The ID of the employee.
   */
  public String getId() {
    return id;
  }

  /**
   * Gets the first name of the employee.
   *
   * @return The first name of the employee.
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets the first name of the employee.
   *
   * @param firstName The new first name of the employee.
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Gets the last name of the employee.
   *
   * @return The last name of the employee.
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets the last name of the employee.
   *
   * @param lastName The new last name of the employee.
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Gets the salary account number of the employee.
   *
   * @return The salary account number of the employee.
   */
  public String getSalaryAccountNumber() {
    return salaryAccountNumber;
  }

  /**
   * Sets the salary account number of the employee.
   *
   * @param salaryAccountNumber The new salary account number of the employee.
   */
  public void setSalaryAccountNumber(String salaryAccountNumber) {
    this.salaryAccountNumber = salaryAccountNumber;
  }

  /**
   * Gets the work experience in years of the employee.
   *
   * @return The work experience in years of the employee.
   */
  public int getWorkExperienceYears() {
    return workExperienceYears;
  }

  /**
   * Sets the work experience in years of the employee.
   *
   * @param workExperienceYears The new work experience in years of the employee.
   */
  public void setWorkExperienceYears(int workExperienceYears) {
    this.workExperienceYears = workExperienceYears;
  }

  /**
   * Gets the department of the employee.
   *
   * @return The department of the employee.
   */
  public Department getDepartment() {
    return department;
  }

  /**
   * Sets the department of the employee.
   *
   * @param department The new department of the employee.
   */
  public void setDepartment(Department department) {
    this.department = department;
  }

  /**
   * Gets the position of the employee.
   *
   * @return The position of the employee.
   */
  public Position getPosition() {
    return position;
  }

  /**
   * Sets the position of the employee.
   *
   * @param position The new position of the employee.
   */
  public void setPosition(Position position) {
    this.position = position;
  }

  /**
   * Gets the list of projects of the employee.
   *
   * @return The list of projects of the employee.
   */
  public List<Project> getProjects() {
    return projects;
  }

  /**
   * Sets the list of projects of the employee.
   *
   * @param projects The new list of projects of the employee.
   */
  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }

  /**
   * Calculates the total cost of all projects this employee has participated in.
   *
   * @return The sum of costs of all assigned projects.
   */
  public double getTotalProjectsCost() {
    if (projects == null) {
      return 0;
    }
    return projects.stream().mapToDouble(Project::getCost).sum();
  }

  @Override
  public String toString() {
    return "Employee{"
        + "id='"
        + id
        + '\''
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", salaryAccountNumber='"
        + salaryAccountNumber
        + '\''
        + ", workExperienceYears="
        + workExperienceYears
        + ", department="
        + (department != null ? department.getName() : "N/A")
        + ", position="
        + (position != null ? position.getName() : "N/A")
        + ", projects="
        + (projects != null ? projects.size() : 0)
        + '}';
  }
}
