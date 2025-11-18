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
import java.util.stream.Collectors;

/**
 * Implements the IEmployeeRepository interface using a JSON file for persistence.
 * This class manages the serialization and deserialization of Employee objects.
 */
public class EmployeeRepository implements IEmployeeRepository {
    private static final String FILE_PATH = "employees.json";
    private final Map<String, Employee> employees;
    private final Gson gson;

    /**
     * Constructs a new EmployeeRepository and loads existing data from the JSON file.
     */
    public EmployeeRepository() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.employees = new ConcurrentHashMap<>();
        loadFromFile();
    }

    @Override
    public void save(Employee employee) throws DuplicateEntityException {
        if (employees.containsKey(employee.getId())) {
            throw new DuplicateEntityException("Error: Employee with ID " + employee.getId() + " already exists.");
        }
        employees.put(employee.getId(), employee);
        saveToFile();
    }

    @Override
    public void update(Employee employee) {
        if (employees.containsKey(employee.getId())) {
            employees.put(employee.getId(), employee);
            saveToFile();
        }
    }

    @Override
    public void delete(String employeeId) {
        if (employees.containsKey(employeeId)) {
            employees.remove(employeeId);
            saveToFile();
        }
    }

    @Override
    public Optional<Employee> findById(String employeeId) {
        return Optional.ofNullable(employees.get(employeeId));
    }

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(employees.values());
    }

    @Override
    public List<Employee> findByDepartmentId(String departmentId) {
        return employees.values().stream()
                .filter(employee -> employee.getDepartment() != null && employee.getDepartment().getId().equals(departmentId))
                .collect(Collectors.toList());
    }

    /**
     * Serializes the current map of employees to the JSON file.
     */
    private synchronized void saveToFile() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(employees, writer);
        } catch (IOException e) {
            System.err.println("Error saving employees to file: " + e.getMessage());
        }
    }

    /**
     * Deserializes employee data from the JSON file into the in-memory map.
     * If the file does not exist, it starts with an empty collection.
     */
    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("Employee data file not found. Starting with an empty list.");
            return;
        }

        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<Map<String, Employee>>() {}.getType();
            Map<String, Employee> loadedEmployees = gson.fromJson(reader, type);
            if (loadedEmployees != null) {
                employees.putAll(loadedEmployees);
            }
        } catch (IOException e) {
            System.err.println("Error loading employees from file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error parsing employee data file. The file might be corrupt: " + e.getMessage());
        }
    }
}
