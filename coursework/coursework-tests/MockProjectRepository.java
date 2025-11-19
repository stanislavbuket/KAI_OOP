import java.util.*;
import java.util.concurrent.*;

/** A mock implementation of the IProjectRepository for testing purposes. */
public class MockProjectRepository implements IProjectRepository {
  private final Map<String, Project> projects = new ConcurrentHashMap<>();

  /** {@inheritDoc} */
  @Override
  public void save(Project project) throws DuplicateEntityException {
    if (projects.values().stream()
        .anyMatch(p -> p.getName().equalsIgnoreCase(project.getName()))) {
      throw new DuplicateEntityException("Duplicate project name");
    }
    projects.put(project.getId(), project);
  }

  /** {@inheritDoc} */
  @Override
  public void update(Project project) {
    projects.put(project.getId(), project);
  }

  /** {@inheritDoc} */
  @Override
  public void delete(String projectId) {
    projects.remove(projectId);
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Project> findById(String projectId) {
    return Optional.ofNullable(projects.get(projectId));
  }

  /** {@inheritDoc} */
  @Override
  public List<Project> findAll() {
    return new ArrayList<>(projects.values());
  }

  /** Clears all projects from the repository. */
  public void clear() {
    projects.clear();
  }
}
