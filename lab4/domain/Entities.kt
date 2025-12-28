/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 4 (Task 3.4)
 */

/**
 * Base class for event arguments, mimicking .NET EventArgs.
 */
open class EventArgs

/**
 * Event arguments for queue overflow event.
 *
 * @property item The item that failed to be added to the queue.
 * @property capacity The maximum capacity of the queue.
 */
class QueueOverflowEventArgs(
    val item: Char,
    val capacity: Int
) : EventArgs()
