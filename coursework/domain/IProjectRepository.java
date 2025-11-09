import java.util.List;
import java.util.Optional;

/**
 * Defines the contract for data access operations related to Project entities.
 */
public interface IProjectRepository {
    void save(Project project) throws DuplicateEntityException;
    void update(Project project);
    void delete(String projectId);
    Optional<Project> findById(String projectId);
    List<Project> findAll();
}
