/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 5 (Task 3.5)
 */

import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

interface IRepository {
    fun getAll(): List<Person>
    fun saveAll(people: List<Person>)
}

class FileRepository(private val filename: String) : IRepository {
    
    override fun getAll(): List<Person> {
        val file = File(filename)
        if (!file.exists()) return emptyList()

        file.inputStream().use { fis ->
            ObjectInputStream(fis).use { ois ->
                @Suppress("UNCHECKED_CAST")
                return ois.readObject() as List<Person>
            }
        }
    }

    override fun saveAll(people: List<Person>) {
        File(filename).outputStream().use { fos ->
            ObjectOutputStream(fos).use { oos ->
                oos.writeObject(people)
            }
        }
    }
}
