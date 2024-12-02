package tasks

import utils.readInput
import kotlin.math.absoluteValue

object Task01 : Task {
    private val list1 = parseInput().map { it.first() }.sortedBy { it }
    private val list2 = parseInput().map { it.last() }.sortedBy { it }
    private val pairs = List(list1.size) { index -> list1[index] to list2[index] }

    override fun partA(): Int =
        pairs.sumOf {(left, right) ->  (left - right).absoluteValue }

    override fun partB(): Int =
        pairs.sumOf { (left, _) -> left * list2.count { it == left } }

    private fun parseInput() =
        readInput("task01.txt").split("\n").map { line -> line.split("   ").map { it.toInt() } }

}