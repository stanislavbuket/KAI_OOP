/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 1 (Task 3.1)
 */

/**
 * The main entry point of the application.
 * Sets up the repository, service, and UI, then runs the application.
 */
fun main() {
    val repository = UniversityRepository("university_data.json")
    val service = UniversityService(repository)

    // A sample set of persons to be saved to the data file.
    val peopleToSave = arrayOf(
        Student("Ivan", "Petrenko", 5, "KB123", "VO123456"),
        Student("Maria", "Ivanenko", 4, "IT456", null),
        Student("Petro", "Sydorenko", 5, "SA789", "VO654321"),
        Student("Olena", "Koval", 5, "KB999", null),
        FootballPlayer("Andriy", "Shevchenko", "AC Milan", "Forward"),
        Lawyer("Valeriy", "Zaluzhnyi", "LAW-001")
    )

    repository.saveAll(peopleToSave)

    val ui = ConsoleUI(service, repository)
    ui.run()
}
