/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 1 (Task 3.1)
 */

/**
 * Handles all console input and output for the application.
 *
 * @property service The business logic service.
 * @property repository The data repository.
 */
class ConsoleUI(private val service: UniversityService, private val repository: UniversityRepository) {
    /**
     * Runs the main console application logic.
     * This includes finding and displaying students and demonstrating polymorphism.
     */
    fun run() {
        println("\n--- Finding 5th-year students who served in the army ---")
        val foundStudents = service.findFifthYearStudentsWhoServed()
        displayStudents(foundStudents)

        println("\n--- Demonstrating additional skills ---")
        repository.processPeople { person ->
            if (person is CanRecitePoetry) {
                person.recitePoem()
            }
        }
    }

    private fun displayStudents(students: Array<Student>) {
        if (students.isEmpty()) {
            println("No such students found")
        } else {
            println("Found ${students.size} students(s)")
            for (student in students) {
                println(
                    " - ${student.firstName} ${student.lastName}, " +
                            "Student ID: ${student.studenId}, " +
                            "Military ID: ${student.militaryId}"
                )
            }
        }
    }
}