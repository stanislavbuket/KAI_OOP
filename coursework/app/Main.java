/**
 * The main entry point for the Human Resources Application.
 */
public class Main {
    /**
     * The main method that starts the application.
     * Initializes the repository, service, and UI, then runs the application.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Initialize all repositories
        IEmployeeRepository employeeRepository = new EmployeeRepository();
        IDepartmentRepository departmentRepository = new DepartmentRepository();
        IPositionRepository positionRepository = new PositionRepository();
        IProjectRepository projectRepository = new ProjectRepository();

        // Initialize the service with all repositories
        IHumanResourcesService service = new HumanResourcesService(
                employeeRepository,
                departmentRepository,
                positionRepository,
                projectRepository
        );
        
        ConsoleUI ui = new ConsoleUI(service);
        ui.run();
    }
}
