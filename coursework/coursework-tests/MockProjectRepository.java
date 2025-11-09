import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MockProjectRepository implements IProjectRepository {
    private final Map<String, Project> projects = new ConcurrentHashMap<>();

    @Override
    public void save(Project project) throws DuplicateEntityException {
        if (projects.values().stream().anyMatch(p -> p.getName().equalsIgnoreCase(project.getName()))) {
            throw new DuplicateEntityException("Duplicate project name");
        }
        projects.put(project.getId(), project);
    }

    @Override
    public void update(Project project) {
        projects.put(project.getId(), project);
    }

    @Override
    public void delete(String projectId) {
        projects.remove(projectId);
    }

    @Override
    public Optional<Project> findById(String projectId) {
        return Optional.ofNullable(projects.get(projectId));
    }

    @Override
    public List<Project> findAll() {
        return new ArrayList<>(projects.values());
    }

    public void clear() {
        projects.clear();
    }
}
