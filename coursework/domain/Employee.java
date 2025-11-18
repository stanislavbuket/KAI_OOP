/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Coursework
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee entity, containing all personal and professional details.
 */
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
     * @param id A unique identifier for the employee.
     * @param firstName The employee's first name.
     * @param lastName The employee's last name.
     * @param salaryAccountNumber The account number for salary payments.
     * @param workExperienceYears The number of years of work experience.
     * @param department The department the employee belongs to.
     * @param position The employee's job position.
     */
    public Employee(String id, String firstName, String lastName, String salaryAccountNumber, int workExperienceYears, Department department, Position position) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salaryAccountNumber = salaryAccountNumber;
        this.workExperienceYears = workExperienceYears;
        this.department = department;
        this.position = position;
        this.projects = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getSalaryAccountNumber() { return salaryAccountNumber; }
    public void setSalaryAccountNumber(String salaryAccountNumber) { this.salaryAccountNumber = salaryAccountNumber; }
    public int getWorkExperienceYears() { return workExperienceYears; }
    public void setWorkExperienceYears(int workExperienceYears) { this.workExperienceYears = workExperienceYears; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
    public List<Project> getProjects() { return projects; }
    public void setProjects(List<Project> projects) { this.projects = projects; }

    /**
     * Calculates the total cost of all projects this employee has participated in.
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
        return "Employee{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", salaryAccountNumber='" + salaryAccountNumber + '\'' +
                ", workExperienceYears=" + workExperienceYears +
                ", department=" + (department != null ? department.getName() : "N/A") +
                ", position=" + (position != null ? position.getName() : "N/A") +
                ", projects=" + (projects != null ? projects.size() : 0) +
                '}';
    }
}