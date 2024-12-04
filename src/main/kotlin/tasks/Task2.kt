package tasks

import utils.readInput
import kotlin.math.absoluteValue

object Task02 : Task {

    override fun partA(): Int =
        parseInput().count {
            (it == it.sortedDescending() || it == it.sorted()) && it.zipWithNext()
                .map { pair -> (pair.second - pair.first).absoluteValue }.all { num -> num in 1..3 }
        }


    override fun partB(): Int {
        return parseInput().count {
            List(it.size) { index1 -> it.filterIndexed { index2, _ -> index1 != index2 } }.any { report ->
                (report == report.sortedDescending() || report == report.sorted()) && report.zipWithNext()
                    .map { pair -> (pair.second - pair.first).absoluteValue }.all { num -> num in 1..3 }
            }
        }
    }


    private fun parseInput() =
        readInput("task02.txt").split("\n").map { line -> line.split(" ").map { it.toInt() } }
}