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
 * Defines the contract for data access operations related to Project entities.
 * This interface is part of the domain layer and is implemented in the data-access layer.
 */
public interface IProjectRepository {
    void save(Project project) throws DuplicateEntityException;
    void update(Project project);
    void delete(String projectId);
    Optional<Project> findById(String projectId);
    List<Project> findAll();
}
