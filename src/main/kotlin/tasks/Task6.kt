package tasks

import utils.readInput

object Task06 : Task {
    private val pointList = parseInput()

    override fun partA(): Int = traversedPoints(pointList.startingPoint()).size

    override fun partB(): Int {
        val startingPoint = pointList.startingPoint()
        var loopCount = 0

        val pointsToCheck = traversedPoints(pointList.startingPoint()).map {
            listOf(
                it,
                it.copy(x = it.x + 1),
                it.copy(x = it.x - 1),
                it.copy(x = it.y + 1),
                it.copy(x = it.y - 1)
            )
        }.flatten().toSet()

        pointsToCheck.forEach { point ->
            val mutablePointList = parseInput().toMutableList()
            mutablePointList.remove(point)
            mutablePointList.add(point.copy(char = 'O'))

            var currentPoint = startingPoint
            var direction = currentPoint?.char
            val traversedPoints = mutableMapOf(currentPoint to direction)

            while (currentPoint != null) {
                val nextPoint = mutablePointList.nextPoint(currentPoint, direction)
                if (nextPoint?.char == '#' || nextPoint?.char == 'O') {
                    direction = direction?.nextDirection()
                } else {
                    if (traversedPoints[nextPoint] == direction) {
                        loopCount++
                        currentPoint = null
                    } else {
                        traversedPoints[nextPoint] = direction
                        currentPoint = nextPoint
                    }
                }
            }
        }
        return loopCount
    }

    private fun traversedPoints(startingPoint: Point?): List<Point> {
        var currentPoint = startingPoint
        val traversedPoints = mutableSetOf<Point?>()
        traversedPoints.add(currentPoint)
        var direction = currentPoint?.char

        while (currentPoint != null) {
            val nextPoint = pointList.nextPoint(currentPoint, direction)
            if (nextPoint?.char == '#') {
                direction = direction?.nextDirection()
            } else {
                traversedPoints.add(nextPoint)
                currentPoint = nextPoint
            }
        }

        return traversedPoints.filterNotNull()
    }

    private fun List<Point>.startingPoint() =
        this.find { it.char == '>' || it.char == '<' || it.char == '^' || it.char == 'v' }

    private fun List<Point>.nextPoint(currentPoint: Point, direction: Char?): Point? {
        val x = currentPoint.x
        val y = currentPoint.y
        return when (direction) {
            '>' -> this.firstOrNull { p -> p.x - 1 == x && p.y == y }
            '<' -> this.firstOrNull { p -> p.x + 1 == x && p.y == y }
            '^' -> this.firstOrNull { p -> p.x == x && p.y == y - 1 }
            'v' -> this.firstOrNull { p -> p.x == x && p.y == y + 1 }
            else -> null
        }
    }

    private fun Char.nextDirection(): Char = when (this) {
        '>' -> 'v'
        '<' -> '^'
        '^' -> '>'
        'v' -> '<'
        else -> this
    }

    data class Point(val x: Int, val y: Int, val char: Char)

    private fun parseInput() =
        readInput("task06.txt").split("\n").flatMapIndexed { y, row ->
            row.mapIndexed { x, char ->
                Point(x, y, char)
            }
        }
}
