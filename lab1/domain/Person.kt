/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 1 (Task 3.1)
 */

/**
 * Base abstract class for all persons in the system.
 *
 * @property firstName The first name of the person.
 * @property lastName The last name of the person.
 */
abstract class Person (
    var firstName: String,
    var lastName: String,
)

/**
 * An interface representing the ability of a person to recite poetry.
 */
interface CanRecitePoetry {
    /**
     * Performs the action of reciting a poem.
     */
    fun recitePoem()
}

/**
 * Represents a student, inheriting from [Person].
 *
 * @property course The student's year of study.
 * @property studenId The student's unique ID. Must match the format `^[A-Z]{2}\\d{3,}`.
 * @property militaryId The student's military ID, if available. Must match the format `^[A-Z]{2}\\d{6}`.
 */
class Student(
    firstName: String,
    lastName: String,
    val course: Int,
    val studenId: String,
    val militaryId: String?
) : Person(firstName, lastName) {
    /**
     * A computed property that is true if the student has a military ID.
     */
    val hasServedInArmy: Boolean
        get() = militaryId != null

    init {
        require(studenId.matches(Regex("^[A-Z]{2}\\d{3,}"))) { "Invalid student ID format: $studenId" }
        militaryId?.let {
            require(it.matches(Regex("^[A-Z]{2}\\d{6}"))) { "Invalid military ID format: $it" }
        }
    }
}

/**
 * Represents a football player, inheriting from [Person].
 *
 * @property club The football club the player belongs to.
 * @property possition The player's position on the team.
 */
class FootballPlayer(
    firstName: String,
    lastName: String,
    val club: String,
    val possition: String
) : Person(firstName, lastName)

/**
 * Represents a lawyer, inheriting from [Person] and implementing [CanRecitePoetry].
 *
 * @property licenseId The lawyer's unique license ID.
 */
class Lawyer(
    firstName: String,
    lastName: String,
    val licenseId: String,
) : Person(firstName, lastName), CanRecitePoetry {
    /**
     * Prints a message indicating that the lawyer is reciting a poem.
     */
    override fun recitePoem() {
        println("$firstName $lastName, a lawyer, is reciting a poem beautifully")
    }
}