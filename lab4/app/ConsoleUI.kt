/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 4 (Task 3.4)
 */

import java.util.Scanner

/**
 * Console User Interface for Lab 4.
 * Demonstrates delegates (lambdas) and events.
 */
class ConsoleUI {
    private val scanner = Scanner(System.`in`)

    /**
     * Runs the main loop of the application.
     */
    fun run() {
        while (true) {
            println("\n--- Lab 4 Variant 4 Menu ---")
            println("1. Task 1: Sort Char Array (Delegate/Lambda)")
            println("2. Task 2 & 3: Queue Overflow (Events)")
            println("0. Exit")
            print("Select option: ")

            try {
                when (scanner.nextLine()) {
                    "1" -> runTask1()
                    "2" -> runTask2()
                    "0" -> return
                    else -> println("Invalid option. Please try again.")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    /**
     * Task 1: Sort a 1D array of characters in descending order using a lambda/delegate.
     */
    private fun runTask1() {
        println("\n--- Task 1: Descending Sort ---")
        print("Enter characters (no spaces, e.g., 'bacd'): ")
        val input = scanner.nextLine()
        val charArray = input.toCharArray()

        println("Original array: ${charArray.joinToString(", ")}")

        val lambdaSorter = CharArraySorter { arr -> arr.sortDescending() }
        lambdaSorter.sort(charArray)
        println("Sorted (Lambda): ${charArray.joinToString(", ")}")

        val anonymousSorter = CharArraySorter(fun(arr: CharArray) {
            arr.sort()
            arr.reverse()
        })
        anonymousSorter.sort(charArray)
        println("Sorted (Anonymous): ${charArray.joinToString(", ")}")
    }

    /**
     * Task 2 & 3: Queue with Overflow Event.
     */
    private fun runTask2() {
        println("\n--- Task 2 & 3: Queue Overflow Event ---")
        print("Enter queue capacity: ")
        val capacityStr = scanner.nextLine()
        val capacity = capacityStr.toIntOrNull() ?: 3
        
        val queue = CustomQueue(capacity)

        // Task 3: Subscribe to the event (create an event handler)
        queue.onOverflow = { sender, args ->
            println("\n[EVENT] !!! Queue Overflow !!!")
            println("Sender: $sender")
            println("Cannot enqueue item: '${args.item}'")
            println("Current capacity limit: ${args.capacity}")
            println("Action: Item rejected.\n")
        }

        println("Queue created with capacity $capacity.")
        println("Enter characters to enqueue (one by one). Type 'pop' to dequeue, 'exit' to finish.")

        while (true) {
            print("> ")
            val command = scanner.nextLine()
            
            if (command == "exit") break
            if (command == "pop") {
                val item = queue.dequeue()
                if (item == null) println("Queue is empty.")
            } else if (command.isNotEmpty()) {
                // Enqueue the first character of the input
                queue.enqueue(command[0])
            }
        }
    }
}
