import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MockEmployeeRepository implements IEmployeeRepository {
    private final Map<String, Employee> employees = new ConcurrentHashMap<>();
    private boolean saveCalled = false;
    private boolean updateCalled = false;
    private boolean deleteCalled = false;

    @Override
    public void save(Employee employee) throws DuplicateEntityException {
        saveCalled = true;
        if (employees.containsKey(employee.getId())) {
            throw new DuplicateEntityException("Duplicate ID found: " + employee.getId());
        }
        employees.put(employee.getId(), employee);
    }

    @Override
    public void update(Employee employee) {
        updateCalled = true;
        employees.put(employee.getId(), employee);
    }

    @Override
    public void delete(String employeeId) {
        deleteCalled = true;
        employees.remove(employeeId);
    }

    @Override
    public Optional<Employee> findById(String employeeId) {
        return Optional.ofNullable(employees.get(employeeId));
    }

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(employees.values());
    }

    @Override
    public List<Employee> findByDepartmentId(String departmentId) {
        return employees.values().stream()
                .filter(employee -> employee.getDepartment() != null && employee.getDepartment().getId().equals(departmentId))
                .collect(java.util.stream.Collectors.toList());
    }

    // Helper methods for testing
    public boolean isSaveCalled() {
        return saveCalled;
    }

    public boolean isUpdateCalled() {
        return updateCalled;
    }

    public boolean isDeleteCalled() {
        return deleteCalled;
    }

    public void clear() {
        employees.clear();
        saveCalled = false;
        updateCalled = false;
        deleteCalled = false;
    }
}
