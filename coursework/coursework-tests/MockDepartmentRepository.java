import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MockDepartmentRepository implements IDepartmentRepository {
    private final Map<String, Department> departments = new ConcurrentHashMap<>();

    @Override
    public void save(Department department) throws DuplicateEntityException {
        if (departments.values().stream().anyMatch(d -> d.getName().equalsIgnoreCase(department.getName()))) {
            throw new DuplicateEntityException("Duplicate department name");
        }
        departments.put(department.getId(), department);
    }

    @Override
    public void update(Department department) {
        departments.put(department.getId(), department);
    }

    @Override
    public void delete(String departmentId) {
        departments.remove(departmentId);
    }

    @Override
    public Optional<Department> findById(String departmentId) {
        return Optional.ofNullable(departments.get(departmentId));
    }

    @Override
    public List<Department> findAll() {
        return new ArrayList<>(departments.values());
    }

    public void clear() {
        departments.clear();
    }
}
