/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 1 (Task 3.1)
 */

/**
 * Provides business logic and services related to the university.
 * Acts as an intermediary between the UI and the data repository.
 *
 * @property repository The data repository for accessing person data.
 */
class UniversityService(private val repository: UniversityRepository) {
    /**
     * Finds all 5th-year students who have served in the army.
     *
     * @return An array of [Student] objects matching the criteria.
     */
    fun findFifthYearStudentsWhoServed(): Array<Student> {
        val result = mutableListOf<Student>()
        repository.processPeople { person ->
            if (person is Student && person.course == 5 && person.hasServedInArmy) {
                result.add(person)
            }
        }
        return result.toTypedArray()
    }
}