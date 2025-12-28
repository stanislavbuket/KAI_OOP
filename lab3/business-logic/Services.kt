/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 3 (Task 3.3)
 */

/**
 * Custom exception for business logic operations.
 */
class ServiceException(message: String, cause: Throwable? = null) : Exception(message, cause)

/**
 * Provides services for handling entities.
 */
class EntityService<T>(private val context: EntityContext<T>) {

    fun getAll(): List<T> {
        return try {
            context.load()
        } catch (e: Exception) {
            throw ServiceException("Failed to load entities", e)
        }
    }

    fun saveAll(data: List<T>) {
        try {
            context.save(data)
        } catch (e: Exception) {
            throw ServiceException("Failed to save entities", e)
        }
    }

    /**
     * Finds 5th-year students who served in the army.
     */
    fun findFifthYearStudentsWhoServed(): List<Student> {
        return getAll().filterIsInstance<Student>()
            .filter { it.course == 5 && it.hasServedInArmy }
    }

    fun configure(provider: DataProvider<T>, filePath: String) {
        context.setProvider(provider)
        context.setFilePath(filePath)
    }
}