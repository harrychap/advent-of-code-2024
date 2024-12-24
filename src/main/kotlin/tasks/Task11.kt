package tasks

import utils.readInput

object Task11 : Task {
    override fun partA(): Long = parseInput().sumOf { countStones(it, 25) }

    override fun partB(): Long = parseInput().sumOf { countStones(it, 75) }

    private fun Long.isEvenDig(): Boolean =
        toString().length % 2 == 0

    private fun Long.split(): List<Long> {
        val s = toString()
        return listOf(
            s.substring(0, s.length / 2).toLong(),
            s.substring(s.length / 2, s.length).toLong()
        )
    }

    private val cache = mutableMapOf<Pair<Long, Int>, Long>()

    private fun countStones(stone: Long, blinksLeft: Int): Long = cache.getOrPut(stone to blinksLeft) {
        if (blinksLeft == 0) 1 else blink(stone).sumOf { countStones(it, blinksLeft - 1) }
    }

    private fun blink(stone: Long): List<Long> {
        return if (stone == 0L) listOf(1L)
        else if (stone.isEvenDig()) {
            stone.split()
        } else {
            listOf(stone * 2024)
        }
    }

    private fun parseInput() = readInput("task11.txt").split(" ").map { it.toLong() }
}