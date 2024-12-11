package tasks

import utils.readInput

object Task07 : Task {

    override fun partA(): Long {
        return parseInput().filter { row ->
            row.second.mapIndexed { index, start ->
                val values = mutableSetOf(start)
                run loop@{
                    row.second.subList(index + 1, row.second.size).forEach {
                        values.addAll(
                            values.map { value -> setOf(value + it, value * it).filterNot { it > row.first } }.flatten()
                        )
                    }
                    if (values.contains(row.first)) return@loop
                }
                values
            }.any { it.contains(row.first) }
        }.sumOf { it.first }
    }

    override fun partB(): Long {
        // NOT CORRECT AND SLOW :(
        return parseInput().filter { row ->
            row.second.mapIndexed { index, start ->
                val values = mutableSetOf(start)
                run loop@{
                    row.second.subList(index + 1, row.second.size).forEach {
                        values.addAll(
                            values.map { value -> setOf(it, value + it, value * it, "$value$it".toLong()).filterNot { it > row.first } }.flatten()
                        )
                    }
                    if (values.contains(row.first)) return@loop
                }
                values
            }.any { it.contains(row.first) }
        }.sumOf { it.first }
    }

    private fun parseInput() =
        readInput("task07.txt").split("\n")
            .map { row ->
                row.split(":")
                    .let { (expected, values) ->
                        expected.toLong() to values.trim().split(" ").map { it.trim().toLong() }
                    }
            }
}