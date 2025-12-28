/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 5 (Task 3.5)
 */

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach

class StudentServiceTest {

    private lateinit var repository: IRepository
    private lateinit var service: StudentService

    @BeforeEach
    fun setUp() {
        repository = mockk<IRepository>()
        service = StudentService(repository)
    }

    @Test
    fun `countFifthYearArmyStudents returns correct count`() {
        val students = listOf(
            Student("S1", "L1", 5, "ID1", true, "M1"), 
            Student("S2", "L2", 5, "ID2", false),      
            Student("S3", "L3", 4, "ID3", true, "M2"), 
            Student("S4", "L4", 5, "ID4", true, "M3"), 
            Lawyer("Lawyer", "1", "L1")                
        )
        
        every { repository.getAll() } returns students

        val result = service.countFifthYearArmyStudents()

        assertEquals(2, result, "Should find exactly 2 students matching criteria")
        
        verify(exactly = 1) { repository.getAll() }
    }

    @Test
    fun `countFifthYearArmyStudents returns zero for empty list`() {
        every { repository.getAll() } returns emptyList()

        val result = service.countFifthYearArmyStudents()

        assertEquals(0, result)
    }
    
    @Test
    fun `getLawyerPoems returns correct poems`() {
        val people = listOf(
            Lawyer("L1", "Last1", "ID1"),
            Student("S1", "L1", 5, "ID1", true)
        )
        every { repository.getAll() } returns people
        
        val poems = service.getLawyerPoems()
        
        assertEquals(1, poems.size)
        assertEquals("L1 says: To be, or not to be, that is the question...", poems[0])
    }
}
