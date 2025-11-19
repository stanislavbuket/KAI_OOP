import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/** This class contains unit tests for the human resources service. */
class HumanResourcesServiceTest {

  /** Mock repository for employees. */
  private MockEmployeeRepository mockEmployeeRepo;
  /** Mock repository for positions. */
  private MockPositionRepository mockPositionRepo;
  /** Mock repository for projects. */
  private MockProjectRepository mockProjectRepo;
  /** The human resources service instance being tested. */
  private HumanResourcesService service;
  /** Test department. */
  private Department devDept;
  /** Test position for junior developers. */
  private Position juniorPos;
  /** Test position for senior developers. */
  private Position seniorPos;

    /** Sets up the test environment before each test. */
  @BeforeEach
  void setUp() {
    mockEmployeeRepo = new MockEmployeeRepository();
    MockDepartmentRepository mockDepartmentRepo = new MockDepartmentRepository();
    mockPositionRepo = new MockPositionRepository();
    mockProjectRepo = new MockProjectRepository();
    service =
        new HumanResourcesService(
            mockEmployeeRepo, mockDepartmentRepo, mockPositionRepo, mockProjectRepo);
    devDept = new Department("Engineering");
    juniorPos = new Position("Junior Dev", 50000, 40);
    seniorPos = new Position("Senior Dev", 100000, 40);
  }

  /**
   * Helper method to create a new employee for tests.
   *
   * @param id the employee id.
   * @param firstName the employee's first name.
   * @param lastName the employee's last name.
   * @param position the employee's position.
   * @return a new employee instance.
   */
  private Employee createEmployee(String id, String firstName, String lastName, Position position) {
    return new Employee(id, firstName, lastName, "ACC123", 1, devDept, position);
  }

    /**
   * Tests that a new employee is successfully hired and saved.
   *
   * @throws DuplicateEntityException if an employee with the same id already exists.
   */
  @Test
  void testHireEmployee_shouldSaveNewEmployee() throws DuplicateEntityException {
    Employee newEmployee = createEmployee("101", "Test", "User", juniorPos);
    service.hireEmployee(newEmployee);
    assertTrue(mockEmployeeRepo.isSaveCalled());
    assertEquals(1, mockEmployeeRepo.findAll().size());
    assertEquals("101", mockEmployeeRepo.findAll().get(0).getId());
  }

  /**
   * Tests that hiring a duplicate employee throws a duplicate entity exception.
   *
   * @throws DuplicateEntityException if an employee with the same id already exists.
   */
  @Test
  void testHireEmployee_shouldThrowExceptionForDuplicateEmployee() throws DuplicateEntityException {
    Employee existingEmployee = createEmployee("101", "Test", "User", juniorPos);
    service.hireEmployee(existingEmployee);

    Employee duplicateEmployee = createEmployee("101", "Duplicate", "User", juniorPos);
    assertThrows(DuplicateEntityException.class, () -> service.hireEmployee(duplicateEmployee));
    assertEquals(1, mockEmployeeRepo.findAll().size());
  }

  /**
   * Tests that an existing employee's information can be updated.
   *
   * @throws DuplicateEntityException if an employee with the same id already exists.
   */
  @Test
  void testUpdateEmployee_shouldUpdateExistingEmployee() throws DuplicateEntityException {
    Employee employee = createEmployee("101", "Test", "User", juniorPos);
    service.hireEmployee(employee);

    employee.setLastName("Updated");
    service.updateEmployee(employee);

    assertTrue(mockEmployeeRepo.isUpdateCalled());
    assertEquals("Updated", service.getEmployeeById("101").get().getLastName());
  }

  /**
   * Tests that an employee can be successfully deleted.
   *
   * @throws DuplicateEntityException if an employee with the same id already exists.
   */
  @Test
  void testDeleteEmployee_shouldRemoveEmployee() throws DuplicateEntityException {
    Employee employee = createEmployee("101", "Test", "User", juniorPos);
    service.hireEmployee(employee);
    assertEquals(1, service.getAllEmployees().size());

    service.deleteEmployee("101");

    assertTrue(mockEmployeeRepo.isDeleteCalled());
    assertEquals(0, service.getAllEmployees().size());
  }

  /**
   * Tests that all employees can be retrieved from the repository.
   *
   * @throws DuplicateEntityException if an employee with the same id already exists.
   */
  @Test
  void testGetAllEmployees_shouldReturnAllFromRepository() throws DuplicateEntityException {
    service.hireEmployee(createEmployee("1", "John", "Doe", juniorPos));
    service.hireEmployee(createEmployee("2", "Jane", "Smith", seniorPos));
    List<Employee> employees = service.getAllEmployees();
    assertNotNull(employees);
    assertEquals(2, employees.size());
  }

  /**
   * Tests that employees are correctly sorted by first name.
   *
   * @throws DuplicateEntityException if an employee with the same id already exists.
   */
  @Test
  void testGetAllEmployeesSortedByFirstName() throws DuplicateEntityException {
    service.hireEmployee(createEmployee("1", "Zoe", "Zane", juniorPos));
    service.hireEmployee(createEmployee("2", "Adam", "Apple", seniorPos));
    List<Employee> employees = service.getAllEmployeesSortedByFirstName();
    assertEquals("Adam", employees.get(0).getFirstName());
    assertEquals("Zoe", employees.get(1).getFirstName());
  }

  /**
   * Tests that employees are correctly sorted by last name.
   *
   * @throws DuplicateEntityException if an employee with the same id already exists.
   */
  @Test
  void testGetAllEmployeesSortedByLastName() throws DuplicateEntityException {
    service.hireEmployee(createEmployee("1", "John", "Zulu", juniorPos));
    service.hireEmployee(createEmployee("2", "Jane", "Abbot", seniorPos));
    List<Employee> employees = service.getAllEmployeesSortedByLastName();
    assertEquals("Abbot", employees.get(0).getLastName());
    assertEquals("Zulu", employees.get(1).getLastName());
  }

  /**
   * Tests that employees are correctly sorted by salary.
   *
   * @throws DuplicateEntityException if an employee with the same id already exists.
   */
  @Test
  void testGetAllEmployeesSortedBySalary() throws DuplicateEntityException {
    Employee junior = createEmployee("1", "Junior", "Dev", juniorPos); // 50000
    Employee senior = createEmployee("2", "Senior", "Dev", seniorPos); // 100000
    service.hireEmployee(senior);
    service.hireEmployee(junior);

    List<Employee> employees = service.getAllEmployeesSortedBySalary();
    assertEquals(50000, employees.get(0).getPosition().getSalary());
    assertEquals(100000, employees.get(1).getPosition().getSalary());
  }

  /**
   * Tests that the most attractive positions are returned based on the salary-to-hour ratio.
   *
   * @throws DuplicateEntityException if a position with the same id already exists.
   */
  @Test
  void testGetMostAttractivePositions_shouldReturnTopPositionsBySalaryToHourRatio()
      throws DuplicateEntityException {
    // arrange
    Position internPos = new Position("Intern", 15000, 20); // ratio: 750
    mockPositionRepo.save(internPos);
    mockPositionRepo.save(juniorPos); // ratio: 1250
    mockPositionRepo.save(seniorPos); // ratio: 2500

    // act
    List<Position> attractivePositions = service.getMostAttractivePositions(2);

    // assert
    assertNotNull(attractivePositions);
    assertEquals(2, attractivePositions.size());
    assertEquals("Senior Dev", attractivePositions.get(0).getName());
    assertEquals("Junior Dev", attractivePositions.get(1).getName());
  }

  /**
   * Tests that the most profitable employee for a given position is returned.
   *
   * @throws DuplicateEntityException if an employee with the same id already exists.
   */
  @Test
  void
      testGetMostProfitableEmployeeForPosition_shouldReturnEmployeeWithBestProjectCostToExperienceRatio()
          throws DuplicateEntityException {
    // arrange
    Project p1 = new Project("P1", 20000);
    Project p2 = new Project("P2", 50000);
    mockProjectRepo.save(p1);
    mockProjectRepo.save(p2);

    Employee emp1 = new Employee("1", "A", "A", "ACC1", 2, devDept, juniorPos); // ratio: 10000
    emp1.getProjects().add(p1);
    service.hireEmployee(emp1);

    Employee emp2 = new Employee("2", "B", "B", "ACC2", 1, devDept, juniorPos); // ratio: 50000
    emp2.getProjects().add(p2);
    service.hireEmployee(emp2);

    Employee emp3 =
        new Employee("3", "C", "C", "ACC3", 5, devDept, seniorPos); // different position
    service.hireEmployee(emp3);

    // act
    var profitableEmployeeOpt = service.getMostProfitableEmployeeForPosition(juniorPos.getId());

    // assert
    assertTrue(profitableEmployeeOpt.isPresent());
    assertEquals("2", profitableEmployeeOpt.get().getId()); // emp2 has the highest ratio
  }

  /**
   * Tests that employees in a department are correctly sorted by the total cost of their projects.
   *
   * @throws DuplicateEntityException if an employee with the same id already exists.
   */
  @Test
  void
      testGetEmployeesByDepartmentSortedByProjectCost_shouldReturnEmployeesSortedByTotalProjectValue()
          throws DuplicateEntityException {
    // arrange
    Project p1 = new Project("P1", 10000);
    Project p2 = new Project("P2", 50000);
    mockProjectRepo.save(p1);
    mockProjectRepo.save(p2);

    Employee emp1 = createEmployee("1", "Low", "Earner", juniorPos); // cost: 10000
    emp1.getProjects().add(p1);
    service.hireEmployee(emp1);

    Employee emp2 = createEmployee("2", "High", "Earner", juniorPos); // cost: 50000
    emp2.getProjects().add(p2);
    service.hireEmployee(emp2);

    // act
    List<Employee> sortedEmployees =
        service.getEmployeesByDepartmentSortedByProjectCost(devDept.getId());

    // assert
    assertEquals(2, sortedEmployees.size());
    assertEquals("2", sortedEmployees.get(0).getId()); // high earner first
    assertEquals("1", sortedEmployees.get(1).getId()); // low earner second
  }

  /**
   * Tests that a project can be successfully assigned to an employee.
   *
   * @throws DuplicateEntityException if an entity with the same id already exists.
   */
  @Test
  void testAssignProjectToEmployee_shouldAddProjectToEmployee() throws DuplicateEntityException {
    // arrange
    Employee emp = createEmployee("1", "Test", "User", juniorPos);
    service.hireEmployee(emp);
    Project proj = new Project("New Project", 1000);
    service.addProject(proj);

    // act
    service.assignProjectToEmployee(emp.getId(), proj.getId());

    // assert
    Employee updatedEmp = service.getEmployeeById(emp.getId()).get();
    assertEquals(1, updatedEmp.getProjects().size());
    assertEquals(proj.getId(), updatedEmp.getProjects().get(0).getId());
  }

  /**
   * Tests the search functionality to ensure it finds matching entities based on a keyword.
   *
   * @throws DuplicateEntityException if an entity with the same id already exists.
   */
  @Test
  void testSearchAllByKeyword_shouldFindMatchingEntities() throws DuplicateEntityException {
    // arrange
    service.hireEmployee(createEmployee("1", "John", "Doe", juniorPos));
    service.addDepartment(new Department("Marketing"));
    service.addProject(new Project("Project Phoenix", 100000));

    // act
    var results = service.searchAllByKeyword("Phoenix");

    // assert
    assertTrue(results.get("Employees").isEmpty());
    assertTrue(results.get("Departments").isEmpty());
    assertFalse(results.get("Projects").isEmpty());
    assertEquals(1, results.get("Projects").size());
    assertEquals("Project Phoenix", ((Project) results.get("Projects").get(0)).getName());
  }
}
