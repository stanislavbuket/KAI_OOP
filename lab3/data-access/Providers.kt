/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 3 (Task 3.3)
 */

import java.io.*

/**
 * Interface for data persistence providers.
 */
interface DataProvider<T> {
    fun serialize(data: List<T>, filePath: String)
    fun deserialize(filePath: String): List<T>
}

/**
 * Binary serialization provider.
 */
class BinaryDataProvider<T : Serializable> : DataProvider<T> {
    @Suppress("UNCHECKED_CAST")
    override fun serialize(data: List<T>, filePath: String) {
        ObjectOutputStream(FileOutputStream(filePath)).use { it.writeObject(data) }
    }

    @Suppress("UNCHECKED_CAST")
    override fun deserialize(filePath: String): List<T> {
        val file = File(filePath)
        if (!file.exists()) return emptyList()
        ObjectInputStream(FileInputStream(filePath)).use { return it.readObject() as List<T> }
    }
}

/**
 * JSON-like serialization for Circles.
 */
class JsonDataProvider : DataProvider<Circle> {
    override fun serialize(data: List<Circle>, filePath: String) {
        File(filePath).bufferedWriter().use { writer ->
            data.forEach { circle ->
                writer.write("{\n")
                writer.write("  \"fillColor\": \"${circle.fillColor}\",\n")
                writer.write("  \"borderColor\": \"${circle.borderColor}\",\n")
                writer.write("  \"radius\": ${circle.radius}\n")
                writer.write("}\n")
            }
        }
    }

    override fun deserialize(filePath: String): List<Circle> {
        val file = File(filePath)
        if (!file.exists()) return emptyList()
        val result = mutableListOf<Circle>()
        val lines = file.readLines()
        var i = 0
        while (i < lines.size) {
            if (lines[i].trim() == "{") {
                val props = mutableMapOf<String, String>()
                i++
                while (i < lines.size && lines[i].trim() != "}") {
                    val line = lines[i].trim().removeSuffix(",")
                    val parts = line.split(":", limit = 2)
                    if (parts.size == 2) {
                        props[parts[0].trim().removeSurrounding("\"")] = parts[1].trim().removeSurrounding("\"")
                    }
                    i++
                }
                result.add(Circle(
                    props["fillColor"] ?: "",
                    props["borderColor"] ?: "",
                    props["radius"]?.toDoubleOrNull() ?: 1.0
                ))
            }
            i++
        }
        return result
    }
}

/**
 * XML serialization for Circles.
 */
class XmlDataProvider : DataProvider<Circle> {
    override fun serialize(data: List<Circle>, filePath: String) {
        File(filePath).bufferedWriter().use { writer ->
            writer.write("<Circles>\n")
            data.forEach { circle ->
                writer.write("  <Circle>\n")
                writer.write("    <fillColor>${circle.fillColor}</fillColor>\n")
                writer.write("    <borderColor>${circle.borderColor}</borderColor>\n")
                writer.write("    <radius>${circle.radius}</radius>\n")
                writer.write("  </Circle>\n")
            }
            writer.write("</Circles>\n")
        }
    }

    override fun deserialize(filePath: String): List<Circle> {
        val file = File(filePath)
        if (!file.exists()) return emptyList()
        val result = mutableListOf<Circle>()
        val text = file.readText()
        val circleRegex = Regex("<Circle>(.*?)</Circle>", RegexOption.DOT_MATCHES_ALL)
        circleRegex.findAll(text).forEach { match ->
            val content = match.groupValues[1]
            val fill = Regex("<fillColor>(.*?)</fillColor>").find(content)?.groupValues?.get(1) ?: ""
            val border = Regex("<borderColor>(.*?)</borderColor>").find(content)?.groupValues?.get(1) ?: ""
            val radius = Regex("<radius>(.*?)</radius>").find(content)?.groupValues?.get(1)?.toDoubleOrNull() ?: 1.0
            result.add(Circle(fill, border, radius))
        }
        return result
    }
}

/**
 * Custom text format serialization for Circles.
 */
class CustomDataProvider : DataProvider<Circle> {
    override fun serialize(data: List<Circle>, filePath: String) {
        File(filePath).bufferedWriter().use { writer ->
            data.forEach { circle ->
                writer.write("CIRCLE|${circle.fillColor}|${circle.borderColor}|${circle.radius}\n")
            }
        }
    }

    override fun deserialize(filePath: String): List<Circle> {
        val file = File(filePath)
        if (!file.exists()) return emptyList()
        return file.readLines().mapNotNull { line ->
            val parts = line.split("|")
            if (parts.size == 4 && parts[0] == "CIRCLE") {
                Circle(parts[1], parts[2], parts[3].toDoubleOrNull() ?: 1.0)
            } else null
        }
    }
}

/**
 * JSON-like serialization for Persons.
 */
class PersonJsonDataProvider : DataProvider<Person> {
    override fun serialize(data: List<Person>, filePath: String) {
        File(filePath).bufferedWriter().use { writer ->
            data.forEach { person ->
                val typeName = person.javaClass.simpleName
                writer.write("$typeName {\n")
                writer.write("  \"firstName\": \"${person.firstName}\",\n")
                writer.write("  \"lastName\": \"${person.lastName}\",\n")
                when (person) {
                    is Student -> {
                        writer.write("  \"course\": ${person.course},\n")
                        writer.write("  \"studentId\": \"${person.studentId}\",\n")
                        person.militaryId?.let { writer.write("  \"militaryId\": \"$it\",\n") }
                    }
                    is FootballPlayer -> {
                        writer.write("  \"club\": \"${person.club}\",\n")
                        writer.write("  \"position\": \"${person.position}\",\n")
                    }
                    is Lawyer -> {
                        writer.write("  \"licenseId\": \"${person.licenseId}\",\n")
                    }
                }
                writer.write("}\n")
            }
        }
    }

    override fun deserialize(filePath: String): List<Person> {
        val file = File(filePath)
        if (!file.exists()) return emptyList()
        val result = mutableListOf<Person>()
        val lines = file.readLines()
        var i = 0
        while (i < lines.size) {
            val typeLine = lines[i].trim()
            if (typeLine.endsWith("{")) {
                val typeName = typeLine.split(" ")[0]
                val props = mutableMapOf<String, String>()
                i++
                while (i < lines.size && lines[i].trim() != "}") {
                    val line = lines[i].trim().removeSuffix(",")
                    val parts = line.split(":", limit = 2)
                    if (parts.size == 2) {
                        props[parts[0].trim().removeSurrounding("\"")] = parts[1].trim().removeSurrounding("\"")
                    }
                    i++
                }
                val firstName = props["firstName"] ?: ""
                val lastName = props["lastName"] ?: ""
                when (typeName) {
                    "Student" -> result.add(Student(firstName, lastName, props["course"]?.toInt() ?: 0, props["studentId"] ?: "", props["militaryId"]))
                    "FootballPlayer" -> result.add(FootballPlayer(firstName, lastName, props["club"] ?: "", props["position"] ?: ""))
                    "Lawyer" -> result.add(Lawyer(firstName, lastName, props["licenseId"] ?: ""))
                }
            }
            i++
        }
        return result
    }
}

/**
 * XML serialization for Persons.
 */
class PersonXmlDataProvider : DataProvider<Person> {
    override fun serialize(data: List<Person>, filePath: String) {
        File(filePath).bufferedWriter().use { writer ->
            writer.write("<People>\n")
            data.forEach { person ->
                val typeName = person.javaClass.simpleName
                writer.write("  <$typeName>\n")
                writer.write("    <firstName>${person.firstName}</firstName>\n")
                writer.write("    <lastName>${person.lastName}</lastName>\n")
                when (person) {
                    is Student -> {
                        writer.write("    <course>${person.course}</course>\n")
                        writer.write("    <studentId>${person.studentId}</studentId>\n")
                        person.militaryId?.let { writer.write("    <militaryId>$it</militaryId>\n") }
                    }
                    is FootballPlayer -> {
                        writer.write("    <club>${person.club}</club>\n")
                        writer.write("    <position>${person.position}</position>\n")
                    }
                    is Lawyer -> {
                        writer.write("    <licenseId>${person.licenseId}</licenseId>\n")
                    }
                }
                writer.write("  </$typeName>\n")
            }
            writer.write("</People>\n")
        }
    }

    override fun deserialize(filePath: String): List<Person> {
        val file = File(filePath)
        if (!file.exists()) return emptyList()
        val result = mutableListOf<Person>()
        val text = file.readText()
        val types = listOf("Student", "FootballPlayer", "Lawyer")
        types.forEach { typeName ->
            val regex = Regex("<$typeName>(.*?)</$typeName>", RegexOption.DOT_MATCHES_ALL)
            regex.findAll(text).forEach { match ->
                val content = match.groupValues[1]
                val first = Regex("<firstName>(.*?)</firstName>").find(content)?.groupValues?.get(1) ?: ""
                val last = Regex("<lastName>(.*?)</lastName>").find(content)?.groupValues?.get(1) ?: ""
                when (typeName) {
                    "Student" -> result.add(Student(first, last, 
                        Regex("<course>(.*?)</course>").find(content)?.groupValues?.get(1)?.toInt() ?: 0,
                        Regex("<studentId>(.*?)</studentId>").find(content)?.groupValues?.get(1) ?: "",
                        Regex("<militaryId>(.*?)</militaryId>").find(content)?.groupValues?.get(1)
                    ))
                    "FootballPlayer" -> result.add(FootballPlayer(first, last,
                        Regex("<club>(.*?)</club>").find(content)?.groupValues?.get(1) ?: "",
                        Regex("<position>(.*?)</position>").find(content)?.groupValues?.get(1) ?: ""
                    ))
                    "Lawyer" -> result.add(Lawyer(first, last,
                        Regex("<licenseId>(.*?)</licenseId>").find(content)?.groupValues?.get(1) ?: ""
                    ))
                }
            }
        }
        return result
    }
}