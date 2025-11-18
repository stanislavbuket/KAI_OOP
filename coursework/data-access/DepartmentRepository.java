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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implements the IDepartmentRepository interface using a JSON file for persistence.
 * This class manages the serialization and deserialization of Department objects.
 */
public class DepartmentRepository implements IDepartmentRepository {
    private static final String FILE_PATH = "departments.json";
    private final Map<String, Department> departments;
    private final Gson gson;

    /**
     * Constructs a new DepartmentRepository and loads existing data from the JSON file.
     */
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

    /**
     * Serializes the current map of departments to the JSON file.
     */
    private synchronized void saveToFile() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(departments, writer);
        } catch (IOException e) {
            System.err.println("Error saving departments to file: " + e.getMessage());
        }
    }

    /**
     * Deserializes department data from the JSON file into the in-memory map.
     * If the file does not exist, it starts with an empty collection.
     */
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
