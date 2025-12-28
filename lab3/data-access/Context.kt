/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 3 (Task 3.3)
 */

import java.io.File

/**
 * Manages the context for entities and their persistence.
 */
class EntityContext<T>(private var provider: DataProvider<T>) {
    private var filePath: String = "data.bin"

    fun setProvider(newProvider: DataProvider<T>) {
        this.provider = newProvider
    }

    fun setFilePath(path: String) {
        this.filePath = path
    }

    fun save(data: List<T>) {
        provider.serialize(data, filePath)
    }

    fun load(): List<T> {
        return provider.deserialize(filePath)
    }

    fun deleteFile() {
        File(filePath).delete()
    }
}
