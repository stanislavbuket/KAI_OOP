/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 5 (Task 3.5)
 */

import java.io.Serializable

abstract class Person(
    open val firstName: String,
    open val lastName: String
) : Serializable {
    override fun toString(): String {
        return "$firstName $lastName"
    }
}

open class Student(
    firstName: String,
    lastName: String,
    val course: Int,
    val studentId: String,
    val hasServedInArmy: Boolean,
    val militaryId: String? = null
) : Person(firstName, lastName) {
    override fun toString(): String {
        return "Student(${super.toString()}, Course: $course, ID: $studentId, Served: $hasServedInArmy, MilID: $militaryId)"
    }
}

interface CanDeclaimPoems {
    fun declaimPoem(): String
}

class Lawyer(
    firstName: String,
    lastName: String,
    val licenseId: String
) : Person(firstName, lastName), CanDeclaimPoems {
    override fun declaimPoem(): String {
        return "To be, or not to be, that is the question..."
    }

    override fun toString(): String {
        return "Lawyer(${super.toString()}, License: $licenseId)"
    }
}

class FootballPlayer(
    firstName: String,
    lastName: String,
    val club: String
) : Person(firstName, lastName) {
    override fun toString(): String {
        return "FootballPlayer(${super.toString()}, Club: $club)"
    }
}
