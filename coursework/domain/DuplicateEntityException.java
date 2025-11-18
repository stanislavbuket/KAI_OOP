/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Coursework
 */

/**
 * Custom exception thrown when an attempt is made to add an entity
 * with a property that must be unique (e.g., ID or name) and already exists.
 */
public class DuplicateEntityException extends Exception {
    /**
     * Constructs a new DuplicateEntityException with the specified detail message.
     * @param message The detail message.
     */
    public DuplicateEntityException(String message) {
        super(message);
    }
}
