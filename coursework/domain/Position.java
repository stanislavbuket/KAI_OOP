/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Coursework
 */

import java.util.*;

/** Represents a job position within the organization. */
public class Position {
  private final String id;
  private String name;
  private double salary;
  private int workingHoursPerWeek;

  /**
   * Constructs a new Position with a generated ID.
   *
   * @param name The name of the position.
   * @param salary The salary for this position.
   * @param workingHoursPerWeek The standard number of working hours per week.
   */
  public Position(String name, double salary, int workingHoursPerWeek) {
    this.id = UUID.randomUUID().toString();
    this.name = name;
    this.salary = salary;
    this.workingHoursPerWeek = workingHoursPerWeek;
  }

  /**
   * Private constructor for deserialization purposes.
   *
   * @param id The ID of the position.
   * @param name The name of the position.
   * @param salary The salary for this position.
   * @param workingHoursPerWeek The standard number of working hours per week.
   */
  private Position(String id, String name, double salary, int workingHoursPerWeek) {
    this.id = id;
    this.name = name;
    this.salary = salary;
    this.workingHoursPerWeek = workingHoursPerWeek;
  }

  /**
   * Gets the ID of the position.
   *
   * @return The ID of the position.
   */
  public String getId() {
    return id;
  }

  /**
   * Gets the name of the position.
   *
   * @return The name of the position.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the position.
   *
   * @param name The new name of the position.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the salary for this position.
   *
   * @return The salary for this position.
   */
  public double getSalary() {
    return salary;
  }

  /**
   * Sets the salary for this position.
   *
   * @param salary The new salary for this position.
   */
  public void setSalary(double salary) {
    this.salary = salary;
  }

  /**
   * Gets the standard number of working hours per week.
   *
   * @return The standard number of working hours per week.
   */
  public int getWorkingHoursPerWeek() {
    return workingHoursPerWeek;
  }

  /**
   * Sets the standard number of working hours per week.
   *
   * @param workingHoursPerWeek The new standard number of working hours per week.
   */
  public void setWorkingHoursPerWeek(int workingHoursPerWeek) {
    this.workingHoursPerWeek = workingHoursPerWeek;
  }

  /**
   * Calculates the attractiveness of a position based on salary per hour. This ratio is used to
   * find the most attractive positions.
   *
   * @return The ratio of salary to weekly hours.
   */
  public double getAttractivenessRatio() {
    if (workingHoursPerWeek == 0) {
      return 0;
    }
    return salary / workingHoursPerWeek;
  }

  @Override
  public String toString() {
    return "Position{"
        + "id='"
        + id
        + "'"
        + ", name='"
        + name
        + "'"
        + ", salary="
        + salary
        + ", workingHoursPerWeek="
        + workingHoursPerWeek
        + '}';
  }
}
