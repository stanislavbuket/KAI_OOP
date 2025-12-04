/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 2 (Task 3.2)
 */

/**
 * Main entry point for Lab 2 demonstration.
 */
fun main() {
    println("--- Task 2: Collections Demo ---")
    collectionsDemo()

    println("\n--- Task 3 & 4: Binary Tree & Iterator Demo ---")
    binaryTreeDemo()
}

fun collectionsDemo() {
    val circles = mutableListOf(
        Circle("Red", "Black", 5.0),
        Circle("Blue", "White", 3.0),
        Circle("Green", "Green", 7.5),
        Circle("Yellow", "Black", 2.0),
        Circle("Purple", "Pink", 4.5),
        Circle("Orange", "Red", 6.0)
    )

    println("Initial list:")
    circles.forEach { it.printInfo() }

    println("\nAdding a new circle:")
    circles.add(Circle("White", "Black", 1.0))
    println("Last element: ${circles.last()}")

    println("\nRemoving the second circle:")
    circles.removeAt(1)
    circles.forEach { println(it) }

    println("\nUpdating the first circle's radius to 10.0:")
    circles[0].radius = 10.0
    circles[0].printInfo()

    println("\nSearching for a circle with radius 7.5:")
    val found = circles.find { it.radius == 7.5 }
    println(found?.toString() ?: "Not found")

    println("\nArray demo:")
    val circleArray = circles.toTypedArray()
    circleArray[0] = Circle("Black", "Black", 99.0)
    println("First element of array: ${circleArray[0]}")
}

fun binaryTreeDemo() {
    val tree = BinaryTree<Circle>()

    val data = listOf(
        Circle("Red", "Black", 5.0),
        Circle("Blue", "White", 3.0),
        Circle("Green", "Green", 7.0),
        Circle("Yellow", "Black", 2.0),
        Circle("Purple", "Pink", 4.0),
        Circle("Orange", "Red", 6.0),
        Circle("Cyan", "Blue", 8.0)
    )

    println("Adding circles to Binary Tree (based on Radius)...")
    data.forEach { tree.add(it) }

    println("\nIterating Tree (Preorder Traversal: Root -> Left -> Right):")
    val iterator = tree.iterator()
    while (iterator.hasNext()) {
        iterator.next().printInfo()
    }

    println("\nSorting the original list using Comparable:")
    val sortedList = data.sorted()
    sortedList.forEach { println(it) }
}