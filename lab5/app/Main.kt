/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 5 (Task 3.5)
 */

fun main() {
    val repository = FileRepository("data.bin")
    val service = StudentService(repository)
    val ui = ConsoleUI(service)
    
    ui.run()
}
