/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 5 (Task 3.5)
 */

import java.util.Scanner

class ConsoleUI(private val service: StudentService) {
    private val scanner = Scanner(System.`in`)

    fun run() {
        while (true) {
            println("\n--- Lab 5 (Testing) Menu ---")
            println("1. Show all people")
            println("2. Count 5th-year students (Army)")
            println("3. Lawyer Poems")
            println("4. Generate Sample Data")
            println("0. Exit")
            print("> ")

            when (scanner.nextLine()) {
                "1" -> showAll()
                "2" -> showCount()
                "3" -> showPoems()
                "4" -> generateData()
                "0" -> return
                else -> println("Invalid option")
            }
        }
    }

    private fun showAll() {
        try {
            val people = service.getAllPeople()
            if (people.isEmpty()) println("List is empty.")
            else people.forEach { println(it) }
        } catch (e: Exception) {
            println("Error loading data: ${e.message}")
        }
    }

    private fun showCount() {
        try {
            val count = service.countFifthYearArmyStudents()
            println("Number of 5th-year students who served in army: $count")
        } catch (e: Exception) {
            println("Error processing data: ${e.message}")
        }
    }

    private fun showPoems() {
        try {
            service.getLawyerPoems().forEach { println(it) }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    private fun generateData() {
        val sample = listOf(
            Student("Ivan", "Petrenko", 5, "S1", true, "M1"),
            Student("Petro", "Ivanov", 5, "S2", false),
            Student("Oleg", "Sidorov", 4, "S3", true, "M2"),
            Student("Anna", "Koval", 5, "S4", true, "M3"),
            Lawyer("Saul", "Goodman", "L1"),
            FootballPlayer("Andriy", "Shevchenko", "Dynamo")
        )
        try {
            service.savePeople(sample)
            println("Sample data generated and saved successfully.")
        } catch (e: Exception) {
            println("Failed to save data: ${e.message}")
        }
    }
}
