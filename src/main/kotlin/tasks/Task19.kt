package tasks

import utils.readInput

object Task19 : Task {
    private val availableTowels = parseInput().first().split(", ")
    private val possibleCombinations = parseInput().last().split("\n")

    override fun partA(): Int {
        return possibleCombinations.count { combination ->
            val stringMap: MutableMap<String, Long> = mutableMapOf()
            calcString(combination, stringMap) > 0
        }
    }

    override fun partB(): Long {
        return possibleCombinations.sumOf { combination ->
            val stringMap: MutableMap<String, Long> = mutableMapOf()
            calcString(combination, stringMap)
        }
    }

    private fun calcString(currentString: String, stringMap: MutableMap<String, Long>): Long {
        if (currentString.isEmpty()) return 1
        val validTowels = availableTowels.filter { currentString.startsWith(it) }
        return stringMap.getOrPut(currentString) {
            validTowels.sumOf {
                calcString(currentString.removePrefix(it), stringMap)
            }
        }
    }

    private fun parseInput() =
        readInput("task19.txt").split("\n\n")

}