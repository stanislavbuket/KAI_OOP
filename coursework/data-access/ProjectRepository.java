/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Coursework
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.*;

/**
 * Implements the IProjectRepository interface using a JSON file for persistence. This class
 * manages the serialization and deserialization of Project objects.
 */
public class ProjectRepository implements IProjectRepository {
  private static final String FILE_PATH = "projects.json";
  private final Map<String, Project> projects;
  private final Gson gson;

  /** Constructs a new ProjectRepository and loads existing data from the JSON file. */
  public ProjectRepository() {
    this.gson = new GsonBuilder().setPrettyPrinting().create();
    this.projects = new ConcurrentHashMap<>();
    loadFromFile();
  }

  @Override
  public void save(Project project) throws DuplicateEntityException {
    if (projects.values().stream()
        .anyMatch(p -> p.getName().equalsIgnoreCase(project.getName()))) {
      throw new DuplicateEntityException(
          "Error: Project with name '" + project.getName() + "' already exists.");
    }
    projects.put(project.getId(), project);
    saveToFile();
  }

  @Override
  public void update(Project project) {
    if (projects.containsKey(project.getId())) {
      projects.put(project.getId(), project);
      saveToFile();
    }
  }

  @Override
  public void delete(String projectId) {
    if (projects.containsKey(projectId)) {
      projects.remove(projectId);
      saveToFile();
    }
  }

  @Override
  public Optional<Project> findById(String projectId) {
    return Optional.ofNullable(projects.get(projectId));
  }

  @Override
  public List<Project> findAll() {
    return new ArrayList<>(projects.values());
  }

  /** Serializes the current map of projects to the JSON file. */
  private synchronized void saveToFile() {
    try (Writer writer = new FileWriter(FILE_PATH)) {
      gson.toJson(projects, writer);
    } catch (IOException e) {
      System.err.println("Error saving projects to file: " + e.getMessage());
    }
  }

  /**
   * Deserializes project data from the JSON file into the in-memory map. If the file does not
   * exist, it starts with an empty collection.
   */
  private void loadFromFile() {
    File file = new File(FILE_PATH);
    if (!file.exists()) {
      return;
    }
    try (Reader reader = new FileReader(file)) {
      Type type = new TypeToken<Map<String, Project>>() {}.getType();
      Map<String, Project> loaded = gson.fromJson(reader, type);
      if (loaded != null) {
        projects.putAll(loaded);
      }
    } catch (IOException e) {
      System.err.println("Error loading projects from file: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("Error parsing projects file: " + e.getMessage());
    }
  }
}
