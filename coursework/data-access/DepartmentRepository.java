import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implements the IDepartmentRepository interface using a JSON file for persistence.
 */
public class DepartmentRepository implements IDepartmentRepository {
    private static final String FILE_PATH = "departments.json";
    private final Map<String, Department> departments;
    private final Gson gson;

    public DepartmentRepository() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.departments = new ConcurrentHashMap<>();
        loadFromFile();
    }

    @Override
    public void save(Department department) throws DuplicateEntityException {
        if (departments.values().stream().anyMatch(d -> d.getName().equalsIgnoreCase(department.getName()))) {
            throw new DuplicateEntityException("Error: Department with name '" + department.getName() + "' already exists.");
        }
        departments.put(department.getId(), department);
        saveToFile();
    }

    @Override
    public void update(Department department) {
        if (departments.containsKey(department.getId())) {
            departments.put(department.getId(), department);
            saveToFile();
        }
    }

    @Override
    public void delete(String departmentId) {
        if (departments.containsKey(departmentId)) {
            departments.remove(departmentId);
            saveToFile();
        }
    }

    @Override
    public Optional<Department> findById(String departmentId) {
        return Optional.ofNullable(departments.get(departmentId));
    }

    @Override
    public List<Department> findAll() {
        return new ArrayList<>(departments.values());
    }

    private synchronized void saveToFile() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(departments, writer);
        } catch (IOException e) {
            System.err.println("Error saving departments to file: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return; // No file to load, start fresh
        }
        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<Map<String, Department>>() {}.getType();
            Map<String, Department> loaded = gson.fromJson(reader, type);
            if (loaded != null) {
                departments.putAll(loaded);
            }
        } catch (IOException e) {
            System.err.println("Error loading departments from file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error parsing departments file: " + e.getMessage());
        }
    }
}
