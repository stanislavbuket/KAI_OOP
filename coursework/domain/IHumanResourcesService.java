import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Defines the contract for business logic operations related to Human Resources.
 */
public interface IHumanResourcesService {
    // --- Employee Management ---
    void hireEmployee(Employee employee) throws DuplicateEntityException;
    void updateEmployee(Employee employee);
    void deleteEmployee(String employeeId);
    Optional<Employee> getEmployeeById(String employeeId);
    List<Employee> getAllEmployees();
    List<Employee> getAllEmployeesSortedByFirstName();
    List<Employee> getAllEmployeesSortedByLastName();
    List<Employee> getAllEmployeesSortedBySalary();

    // --- Department Management ---
    void addDepartment(Department department) throws DuplicateEntityException;
    void updateDepartment(Department department);
    void deleteDepartment(String departmentId);
    Optional<Department> getDepartmentById(String departmentId);
    List<Department> getAllDepartments();
    List<Employee> getEmployeesByDepartment(String departmentId);
    List<Employee> getEmployeesByDepartmentSortedByPosition(String departmentId);
    List<Employee> getEmployeesByDepartmentSortedByProjectCost(String departmentId);

    // --- Position Management ---
    void addPosition(Position position) throws DuplicateEntityException;
    void updatePosition(Position position);
    void deletePosition(String positionId);
    Optional<Position> getPositionById(String positionId);
    List<Position> getAllPositions();
    List<Position> getMostAttractivePositions(int count);
    Optional<Employee> getMostProfitableEmployeeForPosition(String positionId);

    // --- Project Management ---
    void addProject(Project project) throws DuplicateEntityException;
    void updateProject(Project project);
    void deleteProject(String projectId);
    Optional<Project> getProjectById(String projectId);
    List<Project> getAllProjects();
    void assignProjectToEmployee(String employeeId, String projectId);
    void removeProjectFromEmployee(String employeeId, String projectId);

    // --- Search ---
    List<Employee> searchEmployeesByKeyword(String keyword);
    List<Project> searchProjectsByKeyword(String keyword);
    Map<String, List<?>> searchAllByKeyword(String keyword);
    List<Employee> searchEmployeesAdvanced(String lastName, String salaryAccountNumber);
}
