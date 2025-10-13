/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 1 (Task 3.1)
 */

import java.io.File
import java.io.IOException

/**
 * Manages data persistence for university-related persons.
 * Handles saving to and reading from a file with a custom format.
 *
 * @property filePath The path to the file used for data storage.
 */
class UniversityRepository(private val filePath: String) {

    /**
     * Saves a collection of [Person] objects to the file, overwriting existing content.
     * The data is stored in a custom text format.
     *
     * @param people An array of [Person] objects to save.
     */
    fun saveAll(people: Array<Person>) {
        val file = File(filePath)
        file.bufferedWriter().use { writer ->
            people.forEach { person ->
                val typeName = when (person) {
                    is Student -> "Student"
                    is FootballPlayer -> "FootballPlayer"
                    is Lawyer -> "Lawyer"
                    else -> "Person"
                }
                val objectName = "${person.firstName}${person.lastName}"
                writer.write("$typeName $objectName\n")
                writer.write("{\n")
                when (person) {
                    is Student -> {
                        writer.write("  \"firstName\": \"${person.firstName}\",\n")
                        writer.write("  \"lastName\": \"${person.lastName}\",\n")
                        writer.write("  \"course\": ${person.course},\n")
                        writer.write("  \"studenId\": \"${person.studenId}\",\n")
                        person.militaryId?.let { writer.write("  \"militaryId\": \"$it\",\n") }
                    }

                    is FootballPlayer -> {
                        writer.write("  \"firstName\": \"${person.firstName}\",\n")
                        writer.write("  \"lastName\": \"${person.lastName}\",\n")
                        writer.write("  \"club\": \"${person.club}\",\n")
                        writer.write("  \"possition\": \"${person.possition}\",\n")
                    }

                    is Lawyer -> {
                        writer.write("  \"firstName\": \"${person.firstName}\",\n")
                        writer.write("  \"lastName\": \"${person.lastName}\",\n")
                        writer.write("  \"licenseId\": \"${person.licenseId}\",\n")
                    }
                }
                writer.write("}\n")
            }
        }
        println("Data saved to $filePath")
    }

    /**
     * Reads the data file and processes each person object sequentially without loading all of them into memory.
     *
     * @param onPerson A lambda function to be executed for each [Person] object found in the file.
     */
    fun processPeople(onPerson: (Person) -> Unit) {
        val file = File(filePath)
        if (!file.exists()) {
            return
        }

        val lines = file.readLines()
        var i = 0
        while (i < lines.size) {
            val typeLine = lines[i]
            if (typeLine.isBlank() || i + 1 >= lines.size || lines[i + 1] != "{") {
                i++
                continue
            }

            val typeName = typeLine.split(" ")[0]
            val properties = mutableMapOf<String, String>()
            i += 2 // Move to first property line

            while (i < lines.size && lines[i] != "}") {
                val line = lines[i].trim().removeSuffix(",")
                val parts = line.split(":", limit = 2)
                if (parts.size == 2) {
                    val key = parts[0].trim().removeSurrounding("\"")
                    val value = parts[1].trim().removeSurrounding("\"")
                    properties[key] = value
                }
                i++
            }

            try {
                val person = createPerson(typeName, properties)
                onPerson(person)
            } catch (e: Exception) {
                println("Failed to parse person: ${e.message}")
            }

            i++ // Move past "}"
        }
    }

    private fun createPerson(typeName: String, properties: Map<String, String>): Person {
        val firstName = properties["firstName"] ?: throw IOException("Missing firstName")
        val lastName = properties["lastName"] ?: throw IOException("Missing lastName")

        return when (typeName) {
            "Student" -> Student(
                firstName,
                lastName,
                properties["course"]?.toInt() ?: 0,
                properties["studenId"] ?: "",
                properties["militaryId"]
            )

            "FootballPlayer" -> FootballPlayer(
                firstName,
                lastName,
                properties["club"] ?: "",
                properties["possition"] ?: ""
            )

            "Lawyer" -> Lawyer(
                firstName,
                lastName,
                properties["licenseId"] ?: ""
            )

            else -> throw IOException("Unknown person type: $typeName")
        }
    }
}