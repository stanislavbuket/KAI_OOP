/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Coursework
 */
import java.util.*;
import java.util.stream.*;

/**
 * Implements the IHumanResourcesService interface, providing the core business logic for managing
 * employee records and other related entities.
 */
public class HumanResourcesService implements IHumanResourcesService {
  /** Repository for employee data. */
  private final IEmployeeRepository employeeRepository;
  /** Repository for department data. */
  private final IDepartmentRepository departmentRepository;
  /** Repository for position data. */
  private final IPositionRepository positionRepository;
  /** Repository for project data. */
  private final IProjectRepository projectRepository;

  /**
   * Constructs a new HumanResourcesService with the given repositories. This uses dependency
   * injection to decouple the service from concrete repository implementations.
   *
   * @param employeeRepository Repository for employee data.
   * @param departmentRepository Repository for department data.
   * @param positionRepository Repository for position data.
   * @param projectRepository Repository for project data.
   */
  public HumanResourcesService(
      IEmployeeRepository employeeRepository,
      IDepartmentRepository departmentRepository,
      IPositionRepository positionRepository,
      IProjectRepository projectRepository) {
    this.employeeRepository = employeeRepository;
    this.departmentRepository = departmentRepository;
    this.positionRepository = positionRepository;
    this.projectRepository = projectRepository;
  }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hireEmployee(Employee employee) throws DuplicateEntityException {
        employeeRepository.save(employee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEmployee(Employee employee) {
        employeeRepository.update(employee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteEmployee(String employeeId) {
        employeeRepository.delete(employeeId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Employee> getEmployeeById(String employeeId) {
        return employeeRepository.findById(employeeId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getAllEmployeesSortedByFirstName() {
        return employeeRepository.findAll().stream()
                .sorted(Comparator.comparing(Employee::getFirstName))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getAllEmployeesSortedByLastName() {
        return employeeRepository.findAll().stream()
                .sorted(Comparator.comparing(Employee::getLastName))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getAllEmployeesSortedBySalary() {
        return employeeRepository.findAll().stream()
                .sorted(Comparator.comparingDouble(e -> e.getPosition().getSalary()))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDepartment(Department department) throws DuplicateEntityException {
        departmentRepository.save(department);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateDepartment(Department department) {
        departmentRepository.update(department);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteDepartment(String departmentId) {
        departmentRepository.delete(departmentId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Department> getDepartmentById(String departmentId) {
        return departmentRepository.findById(departmentId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getEmployeesByDepartment(String departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getEmployeesByDepartmentSortedByPosition(String departmentId) {
        return getEmployeesByDepartment(departmentId).stream()
                .sorted(Comparator.comparing(e -> e.getPosition().getName()))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getEmployeesByDepartmentSortedByProjectCost(String departmentId) {
        return getEmployeesByDepartment(departmentId).stream()
                .sorted(Comparator.comparingDouble(Employee::getTotalProjectsCost).reversed())
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPosition(Position position) throws DuplicateEntityException {
        positionRepository.save(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePosition(Position position) {
        positionRepository.update(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePosition(String positionId) {
        positionRepository.delete(positionId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Position> getPositionById(String positionId) {
        return positionRepository.findById(positionId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Position> getMostAttractivePositions(int count) {
        return positionRepository.findAll().stream()
                .sorted(Comparator.comparingDouble(Position::getAttractivenessRatio).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Employee> getMostProfitableEmployeeForPosition(String positionId) {
        return employeeRepository.findAll().stream()
                .filter(e -> e.getPosition() != null && e.getPosition().getId().equals(positionId))
                .max(Comparator.comparingDouble(e -> {
                    if (e.getWorkExperienceYears() == 0) return 0;
                    return e.getTotalProjectsCost() / e.getWorkExperienceYears();
                }));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addProject(Project project) throws DuplicateEntityException {
        projectRepository.save(project);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateProject(Project project) {
        projectRepository.update(project);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProject(String projectId) {
        projectRepository.delete(projectId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Project> getProjectById(String projectId) {
        return projectRepository.findById(projectId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignProjectToEmployee(String employeeId, String projectId) {
        Optional<Employee> empOpt = getEmployeeById(employeeId);
        Optional<Project> projOpt = getProjectById(projectId);
        if (empOpt.isPresent() && projOpt.isPresent()) {
            Employee employee = empOpt.get();
            Project project = projOpt.get();
            if (employee.getProjects().stream().noneMatch(p -> p.getId().equals(projectId))) {
                employee.getProjects().add(project);
                updateEmployee(employee);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeProjectFromEmployee(String employeeId, String projectId) {
        Optional<Employee> empOpt = getEmployeeById(employeeId);
        if (empOpt.isPresent()) {
            Employee employee = empOpt.get();
            employee.getProjects().removeIf(p -> p.getId().equals(projectId));
            updateEmployee(employee);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> searchEmployeesByKeyword(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return employeeRepository.findAll().stream()
                .filter(e -> e.getFirstName().toLowerCase().contains(lowerKeyword) ||
                             e.getLastName().toLowerCase().contains(lowerKeyword) ||
                             e.getId().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Project> searchProjectsByKeyword(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return projectRepository.findAll().stream()
                .filter(p -> p.getName().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, List<?>> searchAllByKeyword(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        Map<String, List<?>> results = new HashMap<>();
        
        List<Employee> employees = employeeRepository.findAll().stream()
                .filter(e -> e.toString().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
        
        List<Department> departments = departmentRepository.findAll().stream()
                .filter(d -> d.toString().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());

        List<Position> positions = positionRepository.findAll().stream()
                .filter(p -> p.toString().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());

        List<Project> projects = projectRepository.findAll().stream()
                .filter(p -> p.toString().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());

        results.put("Employees", employees);
        results.put("Departments", departments);
        results.put("Positions", positions);
        results.put("Projects", projects);

        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> searchEmployeesAdvanced(String lastName, String salaryAccountNumber) {
        return employeeRepository.findAll().stream()
                .filter(e -> (lastName == null || e.getLastName().equalsIgnoreCase(lastName)) &&
                             (salaryAccountNumber == null || e.getSalaryAccountNumber().equalsIgnoreCase(salaryAccountNumber)))
                .collect(Collectors.toList());
    }
}
