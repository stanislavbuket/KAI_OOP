/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Coursework
 */

import java.util.*;

/** Represents a project that employees can participate in. */
public class Project {
  private final String id;
  private String name;
  private double cost;

  /**
   * Constructs a new Project with a generated ID.
   *
   * @param name The name of the project.
   * @param cost The budget or cost of the project.
   */
  public Project(String name, double cost) {
    this.id = UUID.randomUUID().toString();
    this.name = name;
    this.cost = cost;
  }

  /**
   * Private constructor for deserialization purposes.
   *
   * @param id The ID of the project.
   * @param name The name of the project.
   * @param cost The budget or cost of the project.
   */
  private Project(String id, String name, double cost) {
    this.id = id;
    this.name = name;
    this.cost = cost;
  }

  /**
   * Gets the ID of the project.
   *
   * @return The ID of the project.
   */
  public String getId() {
    return id;
  }

  /**
   * Gets the name of the project.
   *
   * @return The name of the project.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the project.
   *
   * @param name The new name of the project.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the budget or cost of the project.
   *
   * @return The budget or cost of the project.
   */
  public double getCost() {
    return cost;
  }

  /**
   * Sets the budget or cost of the project.
   *
   * @param cost The new budget or cost of the project.
   */
  public void setCost(double cost) {
    this.cost = cost;
  }

  @Override
  public String toString() {
    return "Project{"
        + "id='"
        + id
        + "'"
        + ", name='"
        + name
        + "'"
        + ", cost="
        + cost
        + '}';
  }
}
