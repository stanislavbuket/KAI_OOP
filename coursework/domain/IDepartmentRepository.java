import java.util.List;
import java.util.Optional;

/**
 * Defines the contract for data access operations related to Department entities.
 */
public interface IDepartmentRepository {
    void save(Department department) throws DuplicateEntityException;
    void update(Department department);
    void delete(String departmentId);
    Optional<Department> findById(String departmentId);
    List<Department> findAll();
}
