package tasks

import utils.readInput

object Task04 : Task {
    private val pointMap = parseInput()

    override fun partA(): Int = pointMap
        .filter { it.value == 'X' }
        .map { point ->
            var count = 0
            if (point.key.expandUp(3) == "MAS") ++count
            if (point.key.expandDown(3) == "MAS") ++count
            if (point.key.expandRight(3) == "MAS") ++count
            if (point.key.expandLeft(3) == "MAS") ++count
            if (point.key.expandDownLeft(3) == "MAS") ++count
            if (point.key.expandDownRight(3) == "MAS") ++count
            if (point.key.expandUpLeft(3) == "MAS") ++count
            if (point.key.expandUpRight(3) == "MAS") ++count
            count
        }.sum()


    override fun partB(): Int = pointMap
        .filter { it.value == 'A' }
        .count { point ->
            val leftBottom = "${point.key.expandDownLeft(1)}A${point.key.expandUpRight(1)}"
            val rightBottom = "${point.key.expandDownRight(1)}A${point.key.expandUpLeft(1)}"
            val rightTop = "${point.key.expandUpRight(1)}A${point.key.expandDownLeft(1)}"
            val leftTop = "${point.key.expandUpLeft(1)}A${point.key.expandDownRight(1)}"
            (leftBottom == "MAS" && rightBottom == "MAS") || (leftBottom == "MAS" && leftTop == "MAS") || (rightBottom == "MAS" && rightTop == "MAS") || (leftTop == "MAS" && rightTop == "MAS")
        }

    private fun parseInput() =
        readInput("task04.txt").split("\n").flatMapIndexed { y, row ->
            row.mapIndexed { x, char ->
                Pair(x, y) to char
            }
        }.associateBy({ it.first }, { it.second })

    private fun Pair<Int, Int>.expandRight(expandSize: Int): String =
        (1..expandSize).joinToString("") { size -> "${pointMap[Pair(this.first + size, this.second)]}" }

    private fun Pair<Int, Int>.expandLeft(expandSize: Int): String =
        (1..expandSize).joinToString("") { size -> "${pointMap[Pair(this.first - size, this.second)]}" }

    private fun Pair<Int, Int>.expandUp(expandSize: Int): String =
        (1..expandSize).joinToString("") { size -> "${pointMap[Pair(this.first, this.second + size)]}" }

    private fun Pair<Int, Int>.expandDown(expandSize: Int): String =
        (1..expandSize).joinToString("") { size -> "${pointMap[Pair(this.first, this.second - size)]}" }

    private fun Pair<Int, Int>.expandUpLeft(expandSize: Int): String =
        (1..expandSize).joinToString("") { size -> "${pointMap[Pair(this.first - size, this.second + size)]}" }

    private fun Pair<Int, Int>.expandDownLeft(expandSize: Int): String =
        (1..expandSize).joinToString("") { size -> "${pointMap[Pair(this.first - size, this.second - size)]}" }

    private fun Pair<Int, Int>.expandUpRight(expandSize: Int): String =
        (1..expandSize).joinToString("") { size -> "${pointMap[Pair(this.first + size, this.second + size)]}" }

    private fun Pair<Int, Int>.expandDownRight(expandSize: Int): String =
        (1..expandSize).joinToString("") { size -> "${pointMap[Pair(this.first + size, this.second - size)]}" }

}