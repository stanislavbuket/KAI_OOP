import java.util.*;
import java.util.concurrent.*;

/** A mock implementation of the IDepartmentRepository for testing purposes. */
public class MockDepartmentRepository implements IDepartmentRepository {
  private final Map<String, Department> departments = new ConcurrentHashMap<>();

  /** {@inheritDoc} */
  @Override
  public void save(Department department) throws DuplicateEntityException {
    if (departments.values().stream()
        .anyMatch(d -> d.getName().equalsIgnoreCase(department.getName()))) {
      throw new DuplicateEntityException("Duplicate department name");
    }
    departments.put(department.getId(), department);
  }

  /** {@inheritDoc} */
  @Override
  public void update(Department department) {
    departments.put(department.getId(), department);
  }

  /** {@inheritDoc} */
  @Override
  public void delete(String departmentId) {
    departments.remove(departmentId);
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Department> findById(String departmentId) {
    return Optional.ofNullable(departments.get(departmentId));
  }

  /** {@inheritDoc} */
  @Override
  public List<Department> findAll() {
    return new ArrayList<>(departments.values());
  }

  /** Clears all departments from the repository. */
  public void clear() {
    departments.clear();
  }
}

