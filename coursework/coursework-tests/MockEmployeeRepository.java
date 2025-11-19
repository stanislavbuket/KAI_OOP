import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

/** A mock implementation of the IEmployeeRepository for testing purposes. */
public class MockEmployeeRepository implements IEmployeeRepository {
  private final Map<String, Employee> employees = new ConcurrentHashMap<>();
  private boolean saveCalled = false;
  private boolean updateCalled = false;
  private boolean deleteCalled = false;

  /** {@inheritDoc} */
  @Override
  public void save(Employee employee) throws DuplicateEntityException {
    saveCalled = true;
    if (employees.containsKey(employee.getId())) {
      throw new DuplicateEntityException("Duplicate ID found: " + employee.getId());
    }
    employees.put(employee.getId(), employee);
  }

  /** {@inheritDoc} */
  @Override
  public void update(Employee employee) {
    updateCalled = true;
    employees.put(employee.getId(), employee);
  }

  /** {@inheritDoc} */
  @Override
  public void delete(String employeeId) {
    deleteCalled = true;
    employees.remove(employeeId);
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Employee> findById(String employeeId) {
    return Optional.ofNullable(employees.get(employeeId));
  }

  /** {@inheritDoc} */
  @Override
  public List<Employee> findAll() {
    return new ArrayList<>(employees.values());
  }

  /** {@inheritDoc} */
  @Override
  public List<Employee> findByDepartmentId(String departmentId) {
    return employees.values().stream()
        .filter(
            employee ->
                employee.getDepartment() != null
                    && employee.getDepartment().getId().equals(departmentId))
        .collect(Collectors.toList());
  }

  /**
   * Checks if the save method was called.
   *
   * @return true if save was called, false otherwise.
   */
  public boolean isSaveCalled() {
    return saveCalled;
  }

  /**
   * Checks if the update method was called.
   *
   * @return true if update was called, false otherwise.
   */
  public boolean isUpdateCalled() {
    return updateCalled;
  }

  /**
   * Checks if the delete method was called.
   *
   * @return true if delete was called, false otherwise.
   */
  public boolean isDeleteCalled() {
    return deleteCalled;
  }

  /** Clears the repository and resets the call flags. */
  public void clear() {
    employees.clear();
    saveCalled = false;
    updateCalled = false;
    deleteCalled = false;
  }
}
