package tasks

import utils.readInput

object Task09 : Task {
    private val input = parseInput()
    private val flatInput = input.flatten()

    override fun partA(): Long {
        val desiredLength = flatInput.filterNotNull().size
        val reversed = flatInput.filterNotNull().asReversed().toMutableList()

        return flatInput.map { value ->
            value?.toLong() ?: reversed.removeFirst().toLong()
        }.subList(0, desiredLength).mapIndexed { index, i -> index * i }.sum()
    }

    override fun partB(): Long {
        val mutableList = input.toMutableList()
        val reversed = mutableList.filterNot { it.contains(null) || it.isEmpty() }.asReversed().toMutableList()

        reversed.forEach { chunk ->
            val remainingEmptySpaces = mutableList.filter { it.contains(null) }

            if (remainingEmptySpaces.any { it.size >= chunk.size }) {
                val currentChunkIndex = mutableList.indexOfFirst { it == chunk }
                mutableList[currentChunkIndex] = (1..chunk.size).map { null }

                val newChunkIndex = mutableList.indexOfFirst { it.size >= chunk.size && it.contains(null) }

                if (mutableList[newChunkIndex].size == chunk.size) {
                    mutableList[newChunkIndex] = chunk
                } else {
                    val blankSpaces = mutableList[newChunkIndex].size - chunk.size
                    val blankList = (1..blankSpaces).map { null }

                    mutableList[newChunkIndex] = chunk
                    mutableList.add(newChunkIndex + 1, blankList)
                }
            }
        }

        return mutableList.flatten().mapIndexed { index, i ->
            index.toLong() * (i?.toLong() ?: 0)
        }.sum()
    }

    private fun Int.isEven(): Boolean = this % 2 == 0

    private fun parseInput(): List<List<Int?>> {
        var count = 0
        return readInput("task09.txt").map { it.digitToInt() }.mapIndexed { index, value ->
            val list = mutableListOf<Int?>()
            if (!(index + 1).isEven()) {
                repeat(value) {
                    list.add(count)
                }.also { ++count }
            } else {
                repeat(value) {
                    list.add(null)
                }
            }
            list.toList()
        }
    }
}