/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Coursework
 */

import java.util.*;

/**
 * Defines the contract for data access operations related to Employee entities. This interface is
 * part of the domain layer and is implemented in the data-access layer.
 */
public interface IEmployeeRepository {
  /**
   * Saves a new employee to the repository.
   *
   * @param employee The employee to save.
   * @throws DuplicateEntityException If an employee with the same ID already exists.
   */
  void save(Employee employee) throws DuplicateEntityException;

  /**
   * Updates an existing employee in the repository.
   *
   * @param employee The employee with updated data.
   */
  void update(Employee employee);

  /**
   * Deletes an employee from the repository by their ID.
   *
   * @param employeeId The ID of the employee to delete.
   */
  void delete(String employeeId);

  /**
   * Finds an employee by their ID.
   *
   * @param employeeId The ID of the employee to find.
   * @return An Optional containing the employee if found, or an empty Optional otherwise.
   */
  Optional<Employee> findById(String employeeId);

  /**
   * Retrieves all employees from the repository.
   *
   * @return A list of all employees.
   */
  List<Employee> findAll();

  /**
   * Finds all employees belonging to a specific department.
   *
   * @param departmentId The ID of the department.
   * @return A list of employees in that department.
   */
  List<Employee> findByDepartmentId(String departmentId);
}
