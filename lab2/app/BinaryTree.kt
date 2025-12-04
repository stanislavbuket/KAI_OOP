/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Lab: 2 (Task 3.2)
 */

import java.util.*

/**
 * A generic Binary Tree implementation.
 *
 * @param T The type of elements in the tree. Must be comparable.
 */
class BinaryTree<T : Comparable<T>> : Iterable<T> {

    private var root: Node<T>? = null

    /**
     * Represents a node in the binary tree.
     */
    private data class Node<T>(
        var value: T,
        var left: Node<T>? = null,
        var right: Node<T>? = null
    )

    /**
     * Adds an element to the binary tree.
     */
    fun add(element: T) {
        root = addRecursive(root, element)
    }

    private fun addRecursive(current: Node<T>?, value: T): Node<T> {
        if (current == null) {
            return Node(value)
        }

        if (value < current.value) {
            current.left = addRecursive(current.left, value)
        } else if (value > current.value) {
            current.right = addRecursive(current.right, value)
        }

        return current
    }

    /**
     * Returns an iterator that traverses the tree in Preorder (Root, Left, Right).
     */
    override fun iterator(): Iterator<T> {
        return PreorderIterator(root)
    }

    /**
     * Iterator implementation for Preorder traversal.
     */
    private inner class PreorderIterator(root: Node<T>?) : Iterator<T> {
        private val stack = Stack<Node<T>>()

        init {
            if (root != null) {
                stack.push(root)
            }
        }

        override fun hasNext(): Boolean {
            return !stack.isEmpty()
        }

        override fun next(): T {
            if (!hasNext()) throw NoSuchElementException()

            val current = stack.pop()

            current.right?.let { stack.push(it) }
            current.left?.let { stack.push(it) }

            return current.value
        }
    }
}