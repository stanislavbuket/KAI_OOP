import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee with full details as per the requirements.
 */
public class Employee {
    private final String id;
    private String firstName;
    private String lastName;
    private String salaryAccountNumber;
    private int workExperienceYears; // Трудовий стаж у роках
    private Department department;
    private Position position;
    private List<Project> projects;

    /**
     * Constructs a new Employee.
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
     * @return The sum of costs of all projects.
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