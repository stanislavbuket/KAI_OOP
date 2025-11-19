/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Coursework
 */

/** The main entry point for the Human Resources Application. */
public class Main {
  /**
   * The main method that starts the application. This method is responsible for setting up the
   * dependency injection container by initializing the repositories, injecting them into the
   * service, and finally starting the user interface.
   *
   * @param args Command line arguments (not used).
   */
  public static void main(String[] args) {
    // initialize all repositories (Data Access Layer)
    IEmployeeRepository employeeRepository = new EmployeeRepository();
    IDepartmentRepository departmentRepository = new DepartmentRepository();
    IPositionRepository positionRepository = new PositionRepository();
    IProjectRepository projectRepository = new ProjectRepository();

    // initialize the service with all repositories (Business Logic Layer)
    IHumanResourcesService service =
        new HumanResourcesService(
            employeeRepository, departmentRepository, positionRepository, projectRepository);

    // initialize and run the UI (Presentation Layer)
    ConsoleUI ui = new ConsoleUI(service);
    ui.run();
  }
}

