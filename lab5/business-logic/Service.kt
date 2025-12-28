/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 5 (Task 3.5)
 */

class StudentService(private val repository: IRepository) {

    fun getAllPeople(): List<Person> {
        return repository.getAll()
    }

    fun savePeople(people: List<Person>) {
        repository.saveAll(people)
    }

    fun countFifthYearArmyStudents(): Int {
        val people = repository.getAll()
        return people.filterIsInstance<Student>()
            .count { it.course == 5 && it.hasServedInArmy }
    }

    fun getLawyerPoems(): List<String> {
        return repository.getAll()
            .filterIsInstance<Lawyer>()
            .map { "${it.firstName} says: ${it.declaimPoem()}" }
    }
}
