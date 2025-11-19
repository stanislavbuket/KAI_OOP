/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Coursework
 */
import java.util.*;

/**
 * Defines the contract for the business logic service that manages human resources operations. This
 * interface orchestrates the interactions between the presentation layer and the data access layer.
 */
public interface IHumanResourcesService {

  // Employee management

  /**
   * Hires a new employee.
   *
   * @param employee The employee to hire.
   * @throws DuplicateEntityException If an employee with the same ID already exists.
   */
  void hireEmployee(Employee employee) throws DuplicateEntityException;

  /**
   * Updates an existing employee's information.
   *
   * @param employee The employee with updated information.
   */
  void updateEmployee(Employee employee);

  /**
   * Deletes an employee.
   *
   * @param employeeId The ID of the employee to delete.
   */
  void deleteEmployee(String employeeId);

  /**
   * Gets an employee by their ID.
   *
   * @param employeeId The ID of the employee to retrieve.
   * @return An Optional containing the employee if found, or an empty Optional otherwise.
   */
  Optional<Employee> getEmployeeById(String employeeId);

  /**
   * Gets a list of all employees.
   *
   * @return A list of all employees.
   */
  List<Employee> getAllEmployees();

  /**
   * Gets a list of all employees sorted by first name.
   *
   * @return A sorted list of all employees.
   */
  List<Employee> getAllEmployeesSortedByFirstName();

  /**
   * Gets a list of all employees sorted by last name.
   *
   * @return A sorted list of all employees.
   */
  List<Employee> getAllEmployeesSortedByLastName();

  /**
   * Gets a list of all employees sorted by salary.
   *
   * @return A sorted list of all employees.
   */
  List<Employee> getAllEmployeesSortedBySalary();

  // Department management

  /**
   * Adds a new department.
   *
   * @param department The department to add.
   * @throws DuplicateEntityException If a department with the same name already exists.
   */
  void addDepartment(Department department) throws DuplicateEntityException;

  /**
   * Updates an existing department's information.
   *
   * @param department The department with updated information.
   */
  void updateDepartment(Department department);

  /**
   * Deletes a department.
   *
   * @param departmentId The ID of the department to delete.
   */
  void deleteDepartment(String departmentId);

  /**
   * Gets a department by its ID.
   *
   * @param departmentId The ID of the department to retrieve.
   * @return An Optional containing the department if found, or an empty Optional otherwise.
   */
  Optional<Department> getDepartmentById(String departmentId);

  /**
   * Gets a list of all departments.
   *
   * @return A list of all departments.
   */
  List<Department> getAllDepartments();

  /**
   * Gets a list of employees in a specific department.
   *
   * @param departmentId The ID of the department.
   * @return A list of employees in the specified department.
   */
  List<Employee> getEmployeesByDepartment(String departmentId);

  /**
   * Gets a list of employees in a department, sorted by position.
   *
   * @param departmentId The ID of the department.
   * @return A sorted list of employees in the department.
   */
  List<Employee> getEmployeesByDepartmentSortedByPosition(String departmentId);

  /**
   * Gets a list of employees in a department, sorted by the total cost of their projects.
   *
   * @param departmentId The ID of the department.
   * @return A sorted list of employees in the department.
   */
  List<Employee> getEmployeesByDepartmentSortedByProjectCost(String departmentId);

  // Position management

  /**
   * Adds a new position.
   *
   * @param position The position to add.
   * @throws DuplicateEntityException If a position with the same name already exists.
   */
  void addPosition(Position position) throws DuplicateEntityException;

  /**
   * Updates an existing position's information.
   *
   * @param position The position with updated information.
   */
  void updatePosition(Position position);

  /**
   * Deletes a position.
   *
   * @param positionId The ID of the position to delete.
   */
  void deletePosition(String positionId);

  /**
   * Gets a position by its ID.
   *
   * @param positionId The ID of the position to retrieve.
   * @return An Optional containing the position if found, or an empty Optional otherwise.
   */
  Optional<Position> getPositionById(String positionId);

  /**
   * Gets a list of all positions.
   *
   * @return A list of all positions.
   */
  List<Position> getAllPositions();

  /**
   * Gets a list of the most attractive positions based on salary-to-hours ratio.
   *
   * @param count The number of positions to retrieve.
   * @return A list of the most attractive positions.
   */
  List<Position> getMostAttractivePositions(int count);

  /**
   * Gets the most profitable employee for a given position.
   *
   * @param positionId The ID of the position.
   * @return An Optional containing the most profitable employee if found, or an empty Optional
   *     otherwise.
   */
  Optional<Employee> getMostProfitableEmployeeForPosition(String positionId);

  // Project management

  /**
   * Adds a new project.
   *
   * @param project The project to add.
   * @throws DuplicateEntityException If a project with the same name already exists.
   */
  void addProject(Project project) throws DuplicateEntityException;

  /**
   * Updates an existing project's information.
   *
   * @param project The project with updated information.
   */
  void updateProject(Project project);

  /**
   * Deletes a project.
   *
   * @param projectId The ID of the project to delete.
   */
  void deleteProject(String projectId);

  /**
   * Gets a project by its ID.
   *
   * @param projectId The ID of the project to retrieve.
   * @return An Optional containing the project if found, or an empty Optional otherwise.
   */
  Optional<Project> getProjectById(String projectId);

  /**
   * Gets a list of all projects.
   *
   * @return A list of all projects.
   */
  List<Project> getAllProjects();

  /**
   * Assigns a project to an employee.
   *
   * @param employeeId The ID of the employee.
   * @param projectId The ID of the project.
   */
  void assignProjectToEmployee(String employeeId, String projectId);

  /**
   * Removes a project from an employee.
   *
   * @param employeeId The ID of the employee.
   * @param projectId The ID of the project.
   */
  void removeProjectFromEmployee(String employeeId, String projectId);

  // Search

  /**
   * Searches for employees by a keyword.
   *
   * @param keyword The keyword to search for.
   * @return A list of employees matching the keyword.
   */
  List<Employee> searchEmployeesByKeyword(String keyword);

  /**
   * Searches for projects by a keyword.
   *
   * @param keyword The keyword to search for.
   * @return A list of projects matching the keyword.
   */
  List<Project> searchProjectsByKeyword(String keyword);

  /**
   * Performs a global search across all entities by a keyword.
   *
   * @param keyword The keyword to search for.
   * @return A map where keys are entity types and values are lists of matching entities.
   */
  Map<String, List<?>> searchAllByKeyword(String keyword);

  /**
   * Performs an advanced search for employees with multiple criteria.
   *
   * @param lastName The last name to search for.
   * @param salaryAccountNumber The salary account number to search for.
   * @return A list of employees matching the criteria.
   */
  List<Employee> searchEmployeesAdvanced(String lastName, String salaryAccountNumber);
}
