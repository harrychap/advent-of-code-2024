package utils

import java.io.File

fun readInput(task: String): String =
    File("src/main/input/$task").inputStream().bufferedReader().use { it.readText() }.trimIndent()
