/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Coursework
 */

import java.util.*;

/** Represents a department within the organization. */
public class Department {
  private final String id;
  private String name;

  /**
   * Constructs a new Department with a generated ID.
   *
   * @param name The name of the department.
   */
  public Department(String name) {
    this.id = UUID.randomUUID().toString();
    this.name = name;
  }

  /**
   * Private constructor for deserialization purposes.
   *
   * @param id The ID of the department.
   * @param name The name of the department.
   */
  private Department(String id, String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * Gets the ID of the department.
   *
   * @return The ID of the department.
   */
  public String getId() {
    return id;
  }

  /**
   * Gets the name of the department.
   *
   * @return The name of the department.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the department.
   *
   * @param name The new name of the department.
   */
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Department{" + "id='" + id + "'" + ", name='" + name + "'" + '}';
  }
}
