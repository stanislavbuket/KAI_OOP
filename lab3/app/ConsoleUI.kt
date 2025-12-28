/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 3 (Task 3.3)
 */

import java.util.Scanner

/**
 * Handles user interaction.
 */
class ConsoleUI(
    private val circleService: EntityService<Circle>,
    private val personService: EntityService<Person>
) {
    private val scanner = Scanner(System.`in`)

    fun run() {
        while (true) {
            println("\n--- Lab 3 Menu ---")
            println("1. Part 1: Circles (Serialization Demo)")
            println("2. Part 2: Students (3-Tier Architecture)")
            println("0. Exit")
            print("Select part: ")

            try {
                when (scanner.nextLine()) {
                    "1" -> runPart1()
                    "2" -> runPart2()
                    "0" -> return
                    else -> println("Invalid option")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    private fun runPart1() {
        val circles = listOf(
            Circle("Red", "Black", 5.0),
            Circle("Blue", "White", 3.2),
            Circle("Green", "Gray", 7.5),
            Circle("Yellow", "Purple", 2.0),
            Circle("Orange", "Cyan", 4.4),
            Circle("Black", "Red", 6.1)
        )

        println("\n--- Part 1: Serialization of Circles ---")
        println("Choose serialization type:")
        println("1. Binary")
        println("2. JSON")
        println("3. XML")
        println("4. Custom")
        print("Choice: ")

        val provider: DataProvider<Circle> = when (scanner.nextLine()) {
            "1" -> BinaryDataProvider()
            "2" -> JsonDataProvider()
            "3" -> XmlDataProvider()
            "4" -> CustomDataProvider()
            else -> {
                println("Invalid choice, using Binary")
                BinaryDataProvider()
            }
        }

        circleService.saveAll(circles)
        
        println("Serializing to 'circles.data'...")
        provider.serialize(circles, "circles.data")
        
        println("Deserializing from 'circles.data'...")
        val restored = provider.deserialize("circles.data")
        
        println("Restored circles:")
        restored.forEach { println(it) }
    }

    private fun runPart2() {
        println("\n--- Part 2: 3-Tier Architecture (Students) ---")
        println("1. Show all people")
        println("2. Find 5th-year students who served")
        println("3. Save sample data")
        println("4. Change serialization settings")
        println("0. Back")
        print("Choice: ")

        when (scanner.nextLine()) {
            "1" -> personService.getAll().forEach { println(it) }
            "2" -> {
                val found = personService.findFifthYearStudentsWhoServed()
                if (found.isEmpty()) println("None found")
                else found.forEach { println(it) }
            }
            "3" -> {
                val sampleData = listOf(
                    Student("Ivan", "Petrenko", 5, "KB123", "VO123456"),
                    Student("Maria", "Ivanenko", 4, "IT456", null),
                    Student("Petro", "Sydorenko", 5, "SA789", "VO654321"),
                    FootballPlayer("Andriy", "Shevchenko", "AC Milan", "Forward"),
                    Lawyer("Valeriy", "Zaluzhnyi", "LAW-001")
                )
                personService.saveAll(sampleData)
                println("Sample data saved.")
            }
            "4" -> {
                println("Select serialization type: 1. Binary, 2. JSON, 3. XML")
                val provider: DataProvider<Person> = when(scanner.nextLine()) {
                    "2" -> PersonJsonDataProvider()
                    "3" -> PersonXmlDataProvider()
                    else -> BinaryDataProvider()
                }
                print("Enter filename: ")
                val filename = scanner.nextLine()
                
                personService.configure(provider, filename)
                println("Settings updated.")
            }
            "0" -> return
        }
    }
}
