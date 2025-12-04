/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 2 (Task 3.2)
 */

import kotlin.math.*

/**
 * Represents a Circle.
 *
 * @property fillColor The color of the circle's interior.
 * @property borderColor The color of the circle's border.
 * @property radius The radius of the circle.
 */
class Circle(
    var fillColor: String,
    var borderColor: String,
    var radius: Double
) : Comparable<Circle> {

    init {
        require(radius > 0) { "Radius must be positive" }
    }

    /**
     * Calculates the area of the circle.
     */
    fun calculateArea(): Double {
        return PI * radius.pow(2)
    }

    /**
     * Calculates the circumference of the circle.
     */
    fun calculateCircumference(): Double {
        return 2 * PI * radius
    }

    /**
     * Prints information about the circle.
     */
    fun printInfo() {
        println("Circle(radius=$radius, fill=$fillColor, border=$borderColor, area=${String.format("%.2f", calculateArea())}, circumference=${String.format("%.2f", calculateCircumference())})")
    }

    /**
     * Compares this circle with another circle based on radius.
     */
    override fun compareTo(other: Circle): Int {
        return this.radius.compareTo(other.radius)
    }

    override fun toString(): String {
        return "Circle(radius=$radius, fill=$fillColor)"
    }
}