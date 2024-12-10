package tasks

import utils.readInput

object Task06 : Task {
    private val pointMap = parseInput()

    override fun partA(): Int {
        var point = pointMap.find { it.char != '.' && it.char != '#' }
        var direction = point?.char
        val points = mutableSetOf(point)
        while (point != null) {
            val x = point.x
            val y = point.y
            val nextPoint = when(direction) {
                '>' -> pointMap.firstOrNull { p -> p.x - 1 == x && p.y == y }
                '<' -> pointMap.firstOrNull { p -> p.x + 1 == x && p.y == y }
                '^' -> pointMap.firstOrNull { p -> p.x == x && p.y == y - 1 }
                'v' -> pointMap.firstOrNull { p -> p.x == x && p.y == y + 1 }
                else -> point
            }
            if (nextPoint?.char == '#') {
                when(direction) {
                    '>' -> direction = 'v'
                    '<' ->  direction = '^'
                    '^' -> direction = '>'
                    'v' -> direction = '<'
                }
            } else {
                points.add(nextPoint)
                point = nextPoint
            }
        }

        return points.filterNotNull().size
    }

    override fun partB(): Int {
        var loopCount = 0
        parseInput().indices.forEach { index ->
            val mutablePointMap = parseInput().toMutableList()
            mutablePointMap[index] = mutablePointMap[index].copy(char = 'O')
            var currentPoint = mutablePointMap.find { it.char == '>' || it.char == '<' || it.char == '^' || it.char == 'v'}
            var direction = currentPoint?.char
            val traversedPoints = mutableListOf(currentPoint)
            while (currentPoint != null) {
                val x = currentPoint.x
                val y = currentPoint.y
                val nextPoint = when(direction) {
                    '>' -> mutablePointMap.firstOrNull { p -> p.x - 1 == x && p.y == y }
                    '<' -> mutablePointMap.firstOrNull { p -> p.x + 1 == x && p.y == y }
                    '^' -> mutablePointMap.firstOrNull { p -> p.x == x && p.y == y - 1 }
                    'v' -> mutablePointMap.firstOrNull { p -> p.x == x && p.y == y + 1 }
                    else -> null
                }
                if (nextPoint?.char == '#' || nextPoint?.char == 'O') {
                    when(direction) {
                        '>' -> direction = 'v'
                        '<' ->  direction = '^'
                        '^' -> direction = '>'
                        'v' -> direction = '<'
                    }
                } else {
                    if (traversedPoints.count { it == nextPoint } == 4) {
                            loopCount++
                            currentPoint = null

                    } else {
                        traversedPoints.add(nextPoint)
                        currentPoint = nextPoint
                    }
                }
            }
        }
        return loopCount
    }

    private data class Point(val x: Int, val y: Int, val char: Char)

    private fun parseInput() =
        readInput("task06.txt").split("\n").flatMapIndexed { y, row ->
            row.mapIndexed { x, char ->
                Point(x, y, char)
            }
        }
}
