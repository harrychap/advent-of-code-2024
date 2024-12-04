package tasks

import utils.readInput

object Task02 : Task {
    private fun List<Int>.isAllZero() = this.any { it == 0 }
    private fun List<Int>.anyAboveThree() = this.any { it > 3 }
    private fun List<Int>.anyBelowThree() = this.any {  it < 3 }
    private fun List<Int>.allAboveOrBelowZero() = (this.all { it > 0 } || this.all { it < 0 })

    override fun partA(): Int =
        parseInput().map { report -> report.windowed(2).map { window -> (window.first() - window.last()) } }
            .count { report ->
                !report.any { it == 0 } && !report.any { it > 3 } && !report.any { it < -3 } && (report.all { it > 0 } || report.all { it < 0 })
            }

    override fun partB(): Int = 0


    private fun parseInput() =
        readInput("task02.txt").split("\n").map { line -> line.split(" ").map { it.toInt() } }
}