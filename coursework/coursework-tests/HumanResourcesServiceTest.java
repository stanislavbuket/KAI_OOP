import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class HumanResourcesServiceTest {

    private MockEmployeeRepository mockEmployeeRepo;
    private MockDepartmentRepository mockDepartmentRepo;
    private MockPositionRepository mockPositionRepo;
    private MockProjectRepository mockProjectRepo;
    private HumanResourcesService service;
    private Department devDept;
    private Position juniorPos;
    private Position seniorPos;

    @BeforeEach
    void setUp() {
        mockEmployeeRepo = new MockEmployeeRepository();
        mockDepartmentRepo = new MockDepartmentRepository();
        mockPositionRepo = new MockPositionRepository();
        mockProjectRepo = new MockProjectRepository();
        service = new HumanResourcesService(
                mockEmployeeRepo,
                mockDepartmentRepo,
                mockPositionRepo,
                mockProjectRepo
        );
        devDept = new Department("Engineering");
        juniorPos = new Position("Junior Dev", 50000, 40);
        seniorPos = new Position("Senior Dev", 100000, 40);
    }

    private Employee createEmployee(String id, String firstName, String lastName, Position position) {
        return new Employee(id, firstName, lastName, "ACC123", 1, devDept, position);
    }

    @Test
    void testHireEmployee_shouldSaveNewEmployee() throws DuplicateEntityException {
        Employee newEmployee = createEmployee("101", "Test", "User", juniorPos);
        service.hireEmployee(newEmployee);
        assertTrue(mockEmployeeRepo.isSaveCalled());
        assertEquals(1, mockEmployeeRepo.findAll().size());
        assertEquals("101", mockEmployeeRepo.findAll().get(0).getId());
    }

    @Test
    void testHireEmployee_shouldThrowExceptionForDuplicateEmployee() throws DuplicateEntityException {
        Employee existingEmployee = createEmployee("101", "Test", "User", juniorPos);
        service.hireEmployee(existingEmployee);

        Employee duplicateEmployee = createEmployee("101", "Duplicate", "User", juniorPos);
        assertThrows(DuplicateEntityException.class, () -> service.hireEmployee(duplicateEmployee));
        assertEquals(1, mockEmployeeRepo.findAll().size());
    }

    @Test
    void testUpdateEmployee_shouldUpdateExistingEmployee() throws DuplicateEntityException {
        Employee employee = createEmployee("101", "Test", "User", juniorPos);
        service.hireEmployee(employee);

        employee.setLastName("Updated");
        service.updateEmployee(employee);

        assertTrue(mockEmployeeRepo.isUpdateCalled());
        assertEquals("Updated", service.getEmployeeById("101").get().getLastName());
    }

    @Test
    void testDeleteEmployee_shouldRemoveEmployee() throws DuplicateEntityException {
        Employee employee = createEmployee("101", "Test", "User", juniorPos);
        service.hireEmployee(employee);
        assertEquals(1, service.getAllEmployees().size());

        service.deleteEmployee("101");

        assertTrue(mockEmployeeRepo.isDeleteCalled());
        assertEquals(0, service.getAllEmployees().size());
    }

    @Test
    void testGetAllEmployees_shouldReturnAllFromRepository() throws DuplicateEntityException {
        service.hireEmployee(createEmployee("1", "John", "Doe", juniorPos));
        service.hireEmployee(createEmployee("2", "Jane", "Smith", seniorPos));
        List<Employee> employees = service.getAllEmployees();
        assertNotNull(employees);
        assertEquals(2, employees.size());
    }

    @Test
    void testGetAllEmployeesSortedByFirstName() throws DuplicateEntityException {
        service.hireEmployee(createEmployee("1", "Zoe", "Zane", juniorPos));
        service.hireEmployee(createEmployee("2", "Adam", "Apple", seniorPos));
        List<Employee> employees = service.getAllEmployeesSortedByFirstName();
        assertEquals("Adam", employees.get(0).getFirstName());
        assertEquals("Zoe", employees.get(1).getFirstName());
    }

    @Test
    void testGetAllEmployeesSortedByLastName() throws DuplicateEntityException {
        service.hireEmployee(createEmployee("1", "John", "Zulu", juniorPos));
        service.hireEmployee(createEmployee("2", "Jane", "Abbot", seniorPos));
        List<Employee> employees = service.getAllEmployeesSortedByLastName();
        assertEquals("Abbot", employees.get(0).getLastName());
        assertEquals("Zulu", employees.get(1).getLastName());
    }

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

    @Test
    void testGetMostAttractivePositions_shouldReturnTopPositionsBySalaryToHourRatio() throws DuplicateEntityException {
        // Arrange
        Position internPos = new Position("Intern", 15000, 20); // Ratio: 750
        mockPositionRepo.save(internPos);
        mockPositionRepo.save(juniorPos); // Ratio: 1250
        mockPositionRepo.save(seniorPos); // Ratio: 2500

        // Act
        List<Position> attractivePositions = service.getMostAttractivePositions(2);

        // Assert
        assertNotNull(attractivePositions);
        assertEquals(2, attractivePositions.size());
        assertEquals("Senior Dev", attractivePositions.get(0).getName()); // Highest ratio
        assertEquals("Junior Dev", attractivePositions.get(1).getName()); // Second highest
    }

    @Test
    void testGetMostProfitableEmployeeForPosition_shouldReturnEmployeeWithBestProjectCostToExperienceRatio() throws DuplicateEntityException {
        // Arrange
        Project p1 = new Project("P1", 20000);
        Project p2 = new Project("P2", 50000);
        mockProjectRepo.save(p1);
        mockProjectRepo.save(p2);

        Employee emp1 = new Employee("1", "A", "A", "ACC1", 2, devDept, juniorPos); // Ratio: 10000
        emp1.getProjects().add(p1);
        service.hireEmployee(emp1);

        Employee emp2 = new Employee("2", "B", "B", "ACC2", 1, devDept, juniorPos); // Ratio: 50000
        emp2.getProjects().add(p2);
        service.hireEmployee(emp2);

        Employee emp3 = new Employee("3", "C", "C", "ACC3", 5, devDept, seniorPos); // Different position
        service.hireEmployee(emp3);

        // Act
        var profitableEmployeeOpt = service.getMostProfitableEmployeeForPosition(juniorPos.getId());

        // Assert
        assertTrue(profitableEmployeeOpt.isPresent());
        assertEquals("2", profitableEmployeeOpt.get().getId()); // emp2 has the highest ratio
    }

    @Test
    void testGetEmployeesByDepartmentSortedByProjectCost_shouldReturnEmployeesSortedByTotalProjectValue() throws DuplicateEntityException {
        // Arrange
        Project p1 = new Project("P1", 10000);
        Project p2 = new Project("P2", 50000);
        mockProjectRepo.save(p1);
        mockProjectRepo.save(p2);

        Employee emp1 = createEmployee("1", "Low", "Earner", juniorPos); // Cost: 10000
        emp1.getProjects().add(p1);
        service.hireEmployee(emp1);

        Employee emp2 = createEmployee("2", "High", "Earner", juniorPos); // Cost: 50000
        emp2.getProjects().add(p2);
        service.hireEmployee(emp2);

        // Act
        List<Employee> sortedEmployees = service.getEmployeesByDepartmentSortedByProjectCost(devDept.getId());

        // Assert
        assertEquals(2, sortedEmployees.size());
        assertEquals("2", sortedEmployees.get(0).getId()); // High Earner first
        assertEquals("1", sortedEmployees.get(1).getId()); // Low Earner second
    }

    @Test
    void testAssignProjectToEmployee_shouldAddProjectToEmployee() throws DuplicateEntityException {
        // Arrange
        Employee emp = createEmployee("1", "Test", "User", juniorPos);
        service.hireEmployee(emp);
        Project proj = new Project("New Project", 1000);
        service.addProject(proj);

        // Act
        service.assignProjectToEmployee(emp.getId(), proj.getId());

        // Assert
        Employee updatedEmp = service.getEmployeeById(emp.getId()).get();
        assertEquals(1, updatedEmp.getProjects().size());
        assertEquals(proj.getId(), updatedEmp.getProjects().get(0).getId());
    }

    @Test
    void testSearchAllByKeyword_shouldFindMatchingEntities() throws DuplicateEntityException {
        // Arrange
        service.hireEmployee(createEmployee("1", "John", "Doe", juniorPos));
        service.addDepartment(new Department("Marketing"));
        service.addProject(new Project("Project Phoenix", 100000));

        // Act
        var results = service.searchAllByKeyword("Phoenix");

        // Assert
            assertTrue(results.get("Employees").isEmpty()); // Employee toString() doesn't contain project names
            assertTrue(results.get("Departments").isEmpty());
            assertFalse(results.get("Projects").isEmpty());
            assertEquals(1, results.get("Projects").size());
            assertEquals("Project Phoenix", ((Project) results.get("Projects").get(0)).getName());    }
}
