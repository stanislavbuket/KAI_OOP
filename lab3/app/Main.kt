/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 3 (Task 3.3)
 */

/**
 * The main entry point of the application.
 */
fun main() {
    val circleContext = EntityContext<Circle>(BinaryDataProvider())
    circleContext.setFilePath("circles.bin")
    
    val personContext = EntityContext<Person>(BinaryDataProvider())
    personContext.setFilePath("people.bin")

    val circleService = EntityService(circleContext)
    val personService = EntityService(personContext)

    val ui = ConsoleUI(circleService, personService)
    ui.run()
}
