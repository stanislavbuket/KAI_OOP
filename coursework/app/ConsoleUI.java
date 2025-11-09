import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Handles all console-based user interaction for the application.
 */
public class ConsoleUI {
    private final IHumanResourcesService service;
    private final Scanner scanner;

    public ConsoleUI(IHumanResourcesService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("Welcome to the Human Resources Application!");
        while (true) {
            printMainMenu();
            int choice = getIntInput();
            switch (choice) {
                case 1:
                    employeeManagementMenu();
                    break;
                case 2:
                    departmentManagementMenu();
                    break;
                case 3:
                    positionManagementMenu();
                    break;
                case 4:
                    projectManagementMenu();
                    break;
                case 5:
                    searchMenu();
                    break;
                case 6:
                    System.out.println("Exiting application. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Employee Management");
        System.out.println("2. Department Management");
        System.out.println("3. Position Management");
        System.out.println("4. Project Management");
        System.out.println("5. Search & Reports");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    // --- Employee Management ---
    private void employeeManagementMenu() {
        while (true) {
            System.out.println("\n--- Employee Management ---");
            System.out.println("1. Add New Employee");
            System.out.println("2. View Employee Details");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. List Employees");
            System.out.println("6. Manage Employee's Projects");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();
            switch (choice) {
                case 1: handleAddEmployee(); break;
                case 2: handleViewEmployee(); break;
                case 3: handleUpdateEmployee(); break;
                case 4: handleDeleteEmployee(); break;
                case 5: listEmployeesMenu(); break;
                case 6: handleEmployeeProjectManagement(); break;
                case 7: return;
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleAddEmployee() {
        try {
            System.out.print("Enter Employee ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter Salary Account Number: ");
            String accNumber = scanner.nextLine();
            System.out.print("Enter Work Experience (in years): ");
            int exp = getIntInput();

            Department dept = selectEntity(service.getAllDepartments(), "Department");
            if (dept == null) return;
            Position pos = selectEntity(service.getAllPositions(), "Position");
            if (pos == null) return;

            if (id.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                System.out.println("Error: Fields cannot be empty.");
                return;
            }

            Employee newEmployee = new Employee(id, firstName, lastName, accNumber, exp, dept, pos);
            service.hireEmployee(newEmployee);
            System.out.println("Employee added successfully!");

        } catch (DuplicateEntityException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void handleViewEmployee() {
        System.out.print("Enter Employee ID to view: ");
        String id = scanner.nextLine();
        Optional<Employee> empOpt = service.getEmployeeById(id);
        if (empOpt.isPresent()) {
            Employee emp = empOpt.get();
            System.out.println("--- Employee Details ---");
            System.out.println("ID: " + emp.getId());
            System.out.println("Name: " + emp.getFirstName() + " " + emp.getLastName());
            System.out.println("Salary Account: " + emp.getSalaryAccountNumber());
            System.out.println("Experience: " + emp.getWorkExperienceYears() + " years");
            System.out.println("Department: " + emp.getDepartment().getName());
            System.out.println("Position: " + emp.getPosition().getName());
            System.out.println("Salary: " + emp.getPosition().getSalary());
            System.out.println("Projects (" + emp.getProjects().size() + "):");
            if (emp.getProjects().isEmpty()) {
                System.out.println("  No projects assigned.");
            } else {
                emp.getProjects().forEach(p -> System.out.println("  - " + p.getName()));
            }
        } else {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }

    private void handleUpdateEmployee() {
        System.out.print("Enter Employee ID to update: ");
        String id = scanner.nextLine();
        Optional<Employee> empOpt = service.getEmployeeById(id);

        if (!empOpt.isPresent()) {
            System.out.println("Employee with ID " + id + " not found.");
            return;
        }

        Employee emp = empOpt.get();
        System.out.println("Updating employee: " + emp.getFirstName() + " " + emp.getLastName());
        System.out.print("Enter new First Name (or press Enter to keep '" + emp.getFirstName() + "'): ");
        String firstName = scanner.nextLine();
        if (!firstName.isEmpty()) emp.setFirstName(firstName);

        System.out.print("Enter new Last Name (or press Enter to keep '" + emp.getLastName() + "'): ");
        String lastName = scanner.nextLine();
        if (!lastName.isEmpty()) emp.setLastName(lastName);

        System.out.print("Enter new Salary Account Number (or press Enter to keep '" + emp.getSalaryAccountNumber() + "'): ");
        String accNumber = scanner.nextLine();
        if (!accNumber.isEmpty()) emp.setSalaryAccountNumber(accNumber);

        System.out.print("Enter new Work Experience (or press Enter to keep '" + emp.getWorkExperienceYears() + "'): ");
        String expStr = scanner.nextLine();
        if (!expStr.isEmpty()) emp.setWorkExperienceYears(Integer.parseInt(expStr));

        System.out.println("Select new Department (or press Enter to keep '" + emp.getDepartment().getName() + "')");
        Department newDept = selectEntity(service.getAllDepartments(), "Department", true);
        if (newDept != null) emp.setDepartment(newDept);

        System.out.println("Select new Position (or press Enter to keep '" + emp.getPosition().getName() + "')");
        Position newPos = selectEntity(service.getAllPositions(), "Position", true);
        if (newPos != null) emp.setPosition(newPos);

        service.updateEmployee(emp);
        System.out.println("Employee updated successfully!");
    }

    private void handleDeleteEmployee() {
        System.out.print("Enter Employee ID to delete: ");
        String id = scanner.nextLine();
        if (service.getEmployeeById(id).isPresent()) {
            service.deleteEmployee(id);
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }

    private void listEmployeesMenu() {
        System.out.println("\n--- List Employees ---");
        System.out.println("1. List Unsorted");
        System.out.println("2. List Sorted by First Name");
        System.out.println("3. List Sorted by Last Name");
        System.out.println("4. List Sorted by Salary");
        System.out.print("Enter your choice: ");

        int choice = getIntInput();
        List<Employee> employees;
        switch (choice) {
            case 1: employees = service.getAllEmployees(); break;
            case 2: employees = service.getAllEmployeesSortedByFirstName(); break;
            case 3: employees = service.getAllEmployeesSortedByLastName(); break;
            case 4: employees = service.getAllEmployeesSortedBySalary(); break;
            default: System.out.println("Invalid choice."); return;
        }
        
        System.out.println("\n--- All Employees ---");
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            employees.forEach(System.out::println);
        }
    }

    private void handleEmployeeProjectManagement() {
        Employee employee = selectEntity(service.getAllEmployees(), "Employee");
        if (employee == null) return;

        while (true) {
            System.out.println("\nManaging projects for: " + employee.getFirstName() + " " + employee.getLastName());
            System.out.println("1. Assign Project");
            System.out.println("2. Remove Project");
            System.out.println("3. Back");
            System.out.print("Enter choice: ");
            int choice = getIntInput();

            if (choice == 1) {
                Project project = selectEntity(service.getAllProjects(), "Project");
                if (project != null) {
                    service.assignProjectToEmployee(employee.getId(), project.getId());
                    System.out.println("Project assigned.");
                }
            } else if (choice == 2) {
                Project project = selectEntity(employee.getProjects(), "Project to remove");
                if (project != null) {
                    service.removeProjectFromEmployee(employee.getId(), project.getId());
                    System.out.println("Project removed.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    // --- Department Management ---
    private void departmentManagementMenu() {
        while (true) {
            System.out.println("\n--- Department Management ---");
            System.out.println("1. Add New Department");
            System.out.println("2. List Departments");
            System.out.println("3. Update Department");
            System.out.println("4. Delete Department");
            System.out.println("5. View Employees in Department");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();
            switch (choice) {
                case 1: handleAddDepartment(); break;
                case 2: service.getAllDepartments().forEach(System.out::println); break;
                case 3: handleUpdateDepartment(); break;
                case 4: handleDeleteDepartment(); break;
                case 5: handleViewEmployeesInDepartment(); break;
                case 6: return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void handleAddDepartment() {
        try {
            System.out.print("Enter new department name: ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                service.addDepartment(new Department(name));
                System.out.println("Department added.");
            }
        } catch (DuplicateEntityException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void handleUpdateDepartment() {
        Department dept = selectEntity(service.getAllDepartments(), "Department to update");
        if (dept == null) return;
        System.out.print("Enter new name (or press Enter to keep '" + dept.getName() + "'): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            dept.setName(name);
            service.updateDepartment(dept);
            System.out.println("Department updated.");
        }
    }

    private void handleDeleteDepartment() {
        Department dept = selectEntity(service.getAllDepartments(), "Department to delete");
        if (dept == null) return;
        // Check if any employee is in this department
        if (!service.getEmployeesByDepartment(dept.getId()).isEmpty()) {
            System.out.println("Cannot delete department. It has employees assigned to it.");
            return;
        }
        service.deleteDepartment(dept.getId());
        System.out.println("Department deleted.");
    }

    private void handleViewEmployeesInDepartment() {
        Department dept = selectEntity(service.getAllDepartments(), "Department");
        if (dept == null) return;

        System.out.println("\n--- Employees in " + dept.getName() + " ---");
        System.out.println("1. List Unsorted");
        System.out.println("2. Sort by Position");
        System.out.println("3. Sort by Total Project Cost");
        System.out.print("Enter choice: ");
        int choice = getIntInput();

        List<Employee> employees;
        switch(choice) {
            case 1: employees = service.getEmployeesByDepartment(dept.getId()); break;
            case 2: employees = service.getEmployeesByDepartmentSortedByPosition(dept.getId()); break;
            case 3: employees = service.getEmployeesByDepartmentSortedByProjectCost(dept.getId()); break;
            default: System.out.println("Invalid choice."); return;
        }

        if (employees.isEmpty()) {
            System.out.println("No employees found in this department.");
        } else {
            employees.forEach(System.out::println);
        }
    }

    // --- Position Management ---
    private void positionManagementMenu() {
         while (true) {
            System.out.println("\n--- Position Management ---");
            System.out.println("1. Add New Position");
            System.out.println("2. List Positions");
            System.out.println("3. Update Position");
            System.out.println("4. Delete Position");
            System.out.println("5. View Top 5 Most Attractive Positions");
            System.out.println("6. Find Most Profitable Employee for a Position");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();
            switch (choice) {
                case 1: handleAddPosition(); break;
                case 2: service.getAllPositions().forEach(System.out::println); break;
                case 3: handleUpdatePosition(); break;
                case 4: handleDeletePosition(); break;
                case 5: handleViewMostAttractivePositions(); break;
                case 6: handleFindMostProfitableEmployee(); break;
                case 7: return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void handleAddPosition() {
        try {
            System.out.print("Enter new position name: ");
            String name = scanner.nextLine();
            System.out.print("Enter salary: ");
            double salary = getDoubleInput();
            System.out.print("Enter working hours per week: ");
            int hours = getIntInput();
            if (!name.isEmpty()) {
                service.addPosition(new Position(name, salary, hours));
                System.out.println("Position added.");
            }
        } catch (DuplicateEntityException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void handleUpdatePosition() {
        Position pos = selectEntity(service.getAllPositions(), "Position to update");
        if (pos == null) return;
        
        System.out.print("Enter new name (or press Enter to keep '" + pos.getName() + "'): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) pos.setName(name);

        System.out.print("Enter new salary (or press Enter to keep '" + pos.getSalary() + "'): ");
        String salaryStr = scanner.nextLine();
        if (!salaryStr.isEmpty()) pos.setSalary(Double.parseDouble(salaryStr));

        System.out.print("Enter new working hours (or press Enter to keep '" + pos.getWorkingHoursPerWeek() + "'): ");
        String hoursStr = scanner.nextLine();
        if (!hoursStr.isEmpty()) pos.setWorkingHoursPerWeek(Integer.parseInt(hoursStr));

        service.updatePosition(pos);
        System.out.println("Position updated.");
    }

    private void handleDeletePosition() {
        Position pos = selectEntity(service.getAllPositions(), "Position to delete");
        if (pos == null) return;
        // Check if any employee has this position
        String posId = pos.getId();
        if (service.getAllEmployees().stream().anyMatch(e -> e.getPosition().getId().equals(posId))) {
            System.out.println("Cannot delete position. It is assigned to one or more employees.");
            return;
        }
        service.deletePosition(posId);
        System.out.println("Position deleted.");
    }

    private void handleViewMostAttractivePositions() {
        System.out.println("\n--- Top 5 Most Attractive Positions (Salary/Hour Ratio) ---");
        List<Position> positions = service.getMostAttractivePositions(5);
        if (positions.isEmpty()) {
            System.out.println("No positions found.");
        } else {
            positions.forEach(p -> System.out.printf("%s (Ratio: %.2f)\n", p, p.getAttractivenessRatio()));
        }
    }

    private void handleFindMostProfitableEmployee() {
        Position pos = selectEntity(service.getAllPositions(), "Position");
        if (pos == null) return;

        Optional<Employee> empOpt = service.getMostProfitableEmployeeForPosition(pos.getId());
        if (empOpt.isPresent()) {
            Employee emp = empOpt.get();
            double ratio = emp.getWorkExperienceYears() > 0 ? emp.getTotalProjectsCost() / emp.getWorkExperienceYears() : 0;
            System.out.println("Most profitable employee for " + pos.getName() + " is: ");
            System.out.println(emp);
            System.out.printf("Profitability Ratio (Project Cost / Experience): %.2f\n", ratio);
        } else {
            System.out.println("No employee found for this position or not enough data to calculate profitability.");
        }
    }

    // --- Project Management ---
    private void projectManagementMenu() {
        while (true) {
            System.out.println("\n--- Project Management ---");
            System.out.println("1. Add New Project");
            System.out.println("2. List Projects");
            System.out.println("3. Update Project");
            System.out.println("4. Delete Project");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();
            switch (choice) {
                case 1: handleAddProject(); break;
                case 2: service.getAllProjects().forEach(System.out::println); break;
                case 3: handleUpdateProject(); break;
                case 4: handleDeleteProject(); break;
                case 5: return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void handleAddProject() {
        try {
            System.out.print("Enter new project name: ");
            String name = scanner.nextLine();
            System.out.print("Enter project cost: ");
            double cost = getDoubleInput();
            if (!name.isEmpty()) {
                service.addProject(new Project(name, cost));
                System.out.println("Project added.");
            }
        } catch (DuplicateEntityException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void handleUpdateProject() {
        Project proj = selectEntity(service.getAllProjects(), "Project to update");
        if (proj == null) return;
        
        System.out.print("Enter new name (or press Enter to keep '" + proj.getName() + "'): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) proj.setName(name);

        System.out.print("Enter new cost (or press Enter to keep '" + proj.getCost() + "'): ");
        String costStr = scanner.nextLine();
        if (!costStr.isEmpty()) proj.setCost(Double.parseDouble(costStr));

        service.updateProject(proj);
        System.out.println("Project updated.");
    }

    private void handleDeleteProject() {
        Project proj = selectEntity(service.getAllProjects(), "Project to delete");
        if (proj == null) return;
        // Check if project is assigned to any employee
        String projId = proj.getId();
        if (service.getAllEmployees().stream().anyMatch(e -> e.getProjects().stream().anyMatch(p -> p.getId().equals(projId)))) {
            System.out.println("Cannot delete project. It is assigned to one or more employees. Please unassign it first.");
            return;
        }
        service.deleteProject(projId);
        System.out.println("Project deleted.");
    }

    // --- Search & Reports ---
    private void searchMenu() {
        while (true) {
            System.out.println("\n--- Search & Reports ---");
            System.out.println("1. Search Employees by Keyword");
            System.out.println("2. Search Projects by Keyword");
            System.out.println("3. Advanced Employee Search");
            System.out.println("4. Global Search by Keyword");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();
            switch (choice) {
                case 1: handleSearchEmployees(); break;
                case 2: handleSearchProjects(); break;
                case 3: handleAdvancedSearch(); break;
                case 4: handleGlobalSearch(); break;
                case 5: return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void handleSearchEmployees() {
        System.out.print("Enter keyword to search employees (ID, first/last name): ");
        String keyword = scanner.nextLine();
        List<Employee> results = service.searchEmployeesByKeyword(keyword);
        System.out.println("\n--- Search Results ---");
        if (results.isEmpty()) {
            System.out.println("No employees found matching '" + keyword + "'.");
        } else {
            results.forEach(System.out::println);
        }
    }

    private void handleSearchProjects() {
        System.out.print("Enter keyword to search projects (name): ");
        String keyword = scanner.nextLine();
        List<Project> results = service.searchProjectsByKeyword(keyword);
        System.out.println("\n--- Search Results ---");
        if (results.isEmpty()) {
            System.out.println("No projects found matching '" + keyword + "'.");
        } else {
            results.forEach(System.out::println);
        }
    }

    private void handleAdvancedSearch() {
        System.out.print("Enter Last Name (or press Enter to skip): ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Salary Account Number (or press Enter to skip): ");
        String accNumber = scanner.nextLine();

        List<Employee> results = service.searchEmployeesAdvanced(
            lastName.isEmpty() ? null : lastName,
            accNumber.isEmpty() ? null : accNumber
        );

        System.out.println("\n--- Advanced Search Results ---");
        if (results.isEmpty()) {
            System.out.println("No employees found with specified criteria.");
        } else {
            results.forEach(System.out::println);
        }
    }

    private void handleGlobalSearch() {
        System.out.print("Enter keyword for global search: ");
        String keyword = scanner.nextLine();
        Map<String, List<?>> results = service.searchAllByKeyword(keyword);
        System.out.println("\n--- Global Search Results ---");
        boolean found = false;
        for (Map.Entry<String, List<?>> entry : results.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                found = true;
                System.out.println("--- Found in " + entry.getKey() + " ---");
                entry.getValue().forEach(System.out::println);
            }
        }
        if (!found) {
            System.out.println("No results found matching '" + keyword + "'.");
        }
    }

    // --- Helper Methods ---
    private <T> T selectEntity(List<T> entities, String entityName) {
        return selectEntity(entities, entityName, false);
    }

    private <T> T selectEntity(List<T> entities, String entityName, boolean optional) {
        if (entities.isEmpty()) {
            System.out.println("No " + entityName + "s available.");
            return null;
        }

        System.out.println("Select a " + entityName + ":");
        for (int i = 0; i < entities.size(); i++) {
            Object entity = entities.get(i);
            String name;
            if (entity instanceof Employee) {
                name = ((Employee) entity).getFirstName() + " " + ((Employee) entity).getLastName();
            } else if (entity instanceof Department) {
                name = ((Department) entity).getName();
            } else if (entity instanceof Position) {
                name = ((Position) entity).getName();
            } else if (entity instanceof Project) {
                name = ((Project) entity).getName();
            } else {
                name = entity.toString();
            }
            System.out.println((i + 1) + ". " + name);
        }
        if (optional) {
            System.out.println("0. Skip / No change");
        }
        System.out.print("Enter choice: ");
        int choice = getIntInput();
        if (optional && choice == 0) {
            return null;
        }
        if (choice > 0 && choice <= entities.size()) {
            return entities.get(choice - 1);
        }
        System.out.println("Invalid choice.");
        return null;
    }

    private int getIntInput() {
        try {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) return -1; // Treat empty input as invalid
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }

    private double getDoubleInput() {
        try {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) return -1.0;
            return Double.parseDouble(line);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1.0;
        }
    }
}