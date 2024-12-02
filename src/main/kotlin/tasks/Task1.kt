package tasks

import utils.readInput
import kotlin.math.absoluteValue

object Task01 : Task {
    private val list1 = parseInput().map { it.first() }.sortedBy { it }
    private val list2 = parseInput().map { it.last() }.sortedBy { it }

    override fun partA(): Int =
        List(list1.size) { index -> list1[index] to list2[index] }.sumOf { (it.first - it.second).absoluteValue }

    override fun partB(): Int =
        List(list1.size) { index -> list1[index] to list2[index] }.sumOf { pair -> pair.first * list2.count { it == pair.first } }

    private fun parseInput() =
        readInput("task01.txt").split("\n").map { line -> line.split("   ").map { it.toInt() } }

}