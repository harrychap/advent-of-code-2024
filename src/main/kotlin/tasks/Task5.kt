package tasks

import utils.readInput

object Task05 : Task {
    private val rules = parseRules()
    private val rows = parseInput()

    override fun partA(): Int = rows.filter { it.isValidRow() }.sumOf { row -> row[row.size / 2] }

    override fun partB(): Int = rows.filter { !it.isValidRow() }.sumOf { row ->
        val mutableRow = row.toMutableList()
        while (!mutableRow.isValidRow()) {
            mutableRow.relevantRules().forEach { rule ->
                if (mutableRow.indexOf(rule.first) > mutableRow.indexOf(rule.second)) {
                    mutableRow.swapElements(mutableRow.indexOf(rule.first), mutableRow.indexOf(rule.second))
                }
            }
        }
        mutableRow[row.size / 2]
    }

    private fun MutableList<Int>.swapElements(idx1: Int, idx2: Int) {
        val t = this[idx1]
        this[idx1] = this[idx2]
        this[idx2] = t
    }

    private fun List<Int>.relevantRules() = rules.filter { this.contains(it.first) && this.contains(it.second) }

    private fun List<Int>.isValidRow(): Boolean = this.all {
        this.relevantRules().all { rule ->
            this.indexOf(rule.first) < this.indexOf(rule.second)
        }
    }

    private fun parseInput() =
        readInput("task05.txt").split("\n\n").last().split("\n").map { it.split(',').map { it.toInt() } }

    private fun parseRules() =
        readInput("task05.txt").split("\n\n").first().split("\n")
            .map { it.split("|").first().toInt() to it.split("|").last().toInt() }
}