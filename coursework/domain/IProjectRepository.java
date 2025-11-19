/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Coursework
 */

import java.util.*;

/**
 * Defines the contract for data access operations related to Project entities. This interface is
 * part of the domain layer and is implemented in the data-access layer.
 */
public interface IProjectRepository {
  /**
   * Saves a new project.
   *
   * @param project The project to save.
   * @throws DuplicateEntityException If a project with the same name already exists.
   */
  void save(Project project) throws DuplicateEntityException;

  /**
   * Updates an existing project.
   *
   * @param project The project to update.
   */
  void update(Project project);

  /**
   * Deletes a project by its ID.
   *
   * @param projectId The ID of the project to delete.
   */
  void delete(String projectId);

  /**
   * Finds a project by its ID.
   *
   * @param projectId The ID of the project to find.
   * @return An Optional containing the project if found, or an empty Optional otherwise.
   */
  Optional<Project> findById(String projectId);

  /**
   * Finds all projects.
   *
   * @return A list of all projects.
   */
  List<Project> findAll();
}
