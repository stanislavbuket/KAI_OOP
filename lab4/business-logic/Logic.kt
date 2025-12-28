/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 4 (Task 3.4)
 */

/**
 * A functional interface (delegate) for sorting a char array.
 * According to Task 1: "Sort a 1D array of characters in descending order".
 */
fun interface CharArraySorter {
    /**
     * Sorts the given array.
     * @param array The array to sort.
     */
    fun sort(array: CharArray)
}

/**
 * A reusable component modeling a Queue with limited capacity.
 * According to Task 2: "Queue (limited capacity): initialization, placement, removal".
 * Events: "Queue overflow".
 *
 * @property capacity The maximum number of items the queue can hold.
 */
class CustomQueue(private val capacity: Int) {
    private val items = ArrayList<Char>()

    /**
     * Event handler type for overflow events.
     * Matches the signature of a standard EventHandler in .NET but using Kotlin functions.
     * Receives the sender (this queue) and the event arguments.
     */
    var onOverflow: ((CustomQueue, QueueOverflowEventArgs) -> Unit)? = null

    /**
     * Adds an item to the queue.
     * If the queue is full, triggers the [onOverflow] event and does not add the item.
     *
     * @param item The character to add.
     */
    fun enqueue(item: Char) {
        if (items.size >= capacity) {
            // Trigger event if full
            onOverflow?.invoke(this, QueueOverflowEventArgs(item, capacity))
        } else {
            items.add(item)
            println("Enqueued: '$item'. Size: ${items.size}/$capacity")
        }
    }

    /**
     * Removes and returns the first item from the queue.
     *
     * @return The first item, or null if empty.
     */
    fun dequeue(): Char? {
        if (items.isEmpty()) return null
        val item = items.removeAt(0)
        println("Dequeued: '$item'. Size: ${items.size}/$capacity")
        return item
    }

    /**
     * Returns the current number of items.
     */
    fun size(): Int = items.size
}
