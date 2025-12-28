/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 3 (Task 3.3)
 */

import java.io.Serializable
import kotlin.math.PI

/**
 * Represents a Circle entity.
 */
class Circle(
    var fillColor: String,
    var borderColor: String,
    var radius: Double
) : Serializable {

    init {
        require(radius > 0) { "Radius must be positive" }
    }

    /**
     * Calculates the area of the circle.
     */
    fun calculateArea(): Double = PI * radius * radius

    /**
     * Calculates the circumference of the circle.
     */
    fun calculateCircumference(): Double = 2 * PI * radius

    override fun toString(): String {
        val area = "%.2f".format(calculateArea())
        return "Circle(fillColor='$fillColor', borderColor='$borderColor', radius=$radius, area=$area)"
    }
}

/**
 * Base abstract class for all persons.
 */
abstract class Person(
    var firstName: String,
    var lastName: String
) : Serializable

/**
 * Interface for entities that can recite poetry.
 */
interface CanRecitePoetry {
    fun recitePoem()
}

/**
 * Represents a student.
 */
class Student(
    firstName: String,
    lastName: String,
    var course: Int,
    var studentId: String,
    var militaryId: String? = null
) : Person(firstName, lastName) {

    val hasServedInArmy: Boolean
        get() = militaryId != null

    init {
        require(studentId.matches(Regex("^[A-Z]{2}\\d{3,}"))) { "Invalid student ID format" }
        militaryId?.let {
            require(it.matches(Regex("^[A-Z]{2}\\d{6}"))) { "Invalid military ID format" }
        }
    }

    override fun toString(): String {
        return "Student($firstName $lastName, Course: $course, ID: $studentId, Military ID: ${militaryId ?: "N/A"})"
    }
}

/**
 * Represents a football player.
 */
class FootballPlayer(
    firstName: String,
    lastName: String,
    var club: String,
    var position: String
) : Person(firstName, lastName) {

    override fun toString(): String {
        return "FootballPlayer($firstName $lastName, Club: $club, Position: $position)"
    }
}

/**
 * Represents a lawyer.
 */
class Lawyer(
    firstName: String,
    lastName: String,
    var licenseId: String
) : Person(firstName, lastName), CanRecitePoetry {

    override fun recitePoem() {
        println("$firstName $lastName, a lawyer, is reciting a poem beautifully")
    }

    override fun toString(): String {
        return "Lawyer($firstName $lastName, License: $licenseId)"
    }
}