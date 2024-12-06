package tasks

import utils.readInput

object Task03 : Task {

    private val pattern = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()

    override fun partA(): Int = parseInput()
        .calcMatches()

    override fun partB(): Int = parseInput()
        .split("do()")
        .map { it.split("don't()").first() }
        .sumOf { line -> line.calcMatches() }

    private fun String.calcMatches(): Int = pattern.findAll(this).map { match ->
        match.destructured.component1().toInt() * match.destructured.component2().toInt()
    }.sum()

    private fun parseInput() =
        readInput("task03.txt")
}