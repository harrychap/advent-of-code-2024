package tasks

import utils.readInput

object Task10 : Task {
    private val map = parseInput()
    private val startingPoints = map.filter { it.value == 0 }

    override fun partA(): Int = startingPoints.map { (startingPoint, _) ->
        var nextSteps = listOf(startingPoint)
        (1..9).forEach { i ->
            nextSteps = nextSteps.calcNextSteps(i)
        }
        nextSteps.filter { map[it] == 9 }.toSet().size
    }.sum()

    override fun partB(): Int = startingPoints.map { (startingPoint, _) ->
        var nextSteps = listOf(startingPoint)
        (1..9).forEach { i ->
            nextSteps = nextSteps.calcNextSteps(i)
        }
        nextSteps.filter { map[it] == 9 }.size
    }.sum()


    private fun List<Point>.calcNextSteps(currentIndex: Int) = this.flatMap { step ->
        listOf(
            step.nextUp(),
            step.nextDown(),
            step.nextRight(),
            step.nextLeft()
        ).filter { it.second == currentIndex }.map { it.first }
    }

    private fun Point.nextRight(): Pair<Point, Int?> {
        val nextPoint = Point(this.x + 1, this.y)
        return Pair(nextPoint, map[nextPoint])
    }

    private fun Point.nextLeft(): Pair<Point, Int?> {
        val nextPoint = Point(this.x - 1, this.y)
        return Pair(nextPoint, map[nextPoint])
    }

    private fun Point.nextUp(): Pair<Point, Int?> {
        val nextPoint = Point(this.x, this.y - 1)
        return Pair(nextPoint, map[nextPoint])
    }

    private fun Point.nextDown(): Pair<Point, Int?> {
        val nextPoint = Point(this.x, this.y + 1)
        return Pair(nextPoint, map[nextPoint])
    }

    private data class Point(val x: Int, val y: Int)

    private fun parseInput() = readInput("task10.txt").split("\n").flatMapIndexed { y, row ->
        row.mapIndexed { x, char ->
            Point(x, y) to char.digitToIntOrNull()
        }
    }.associate { it.first to it.second }
}