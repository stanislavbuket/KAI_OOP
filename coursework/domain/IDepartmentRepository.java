/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Coursework
 */

import java.util.List;
import java.util.Optional;

/**
 * Defines the contract for data access operations related to Department entities.
 * This interface is part of the domain layer and is implemented in the data-access layer.
 */
public interface IDepartmentRepository {
    void save(Department department) throws DuplicateEntityException;
    void update(Department department);
    void delete(String departmentId);
    Optional<Department> findById(String departmentId);
    List<Department> findAll();
}
