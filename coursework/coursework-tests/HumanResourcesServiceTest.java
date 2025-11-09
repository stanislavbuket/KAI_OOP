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
}
