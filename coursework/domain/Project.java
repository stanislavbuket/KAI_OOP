/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Coursework
 */

import java.util.UUID;

/**
 * Represents a project that employees can participate in.
 */
public class Project {
    private final String id;
    private String name;
    private double cost;

    /**
     * Constructs a new Project with a generated ID.
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
     */
    private Project(String id, String name, double cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + "'" +
                ", name='" + name + "'" +
                ", cost=" + cost +
                '}';
    }
}
