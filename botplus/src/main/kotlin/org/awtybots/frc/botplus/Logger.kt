package org.awtybots.frc.botplus

import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val dir = "logs"

class Logger(private val category: String) {

        fun debug(message: String) = log("DEBUG", message)
        fun warn(message: String) = log("WARN", message)
        fun error(message: String) = log("ERROR", message)
        fun saveToFile() = Companion.saveToFile()

        private fun log(mode: String, message: String) = Companion.log(category, mode, message)

        companion object {
                private val formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd hh-mm-ss")
                private val history = mutableListOf<String>()

                private fun log(category: String, mode: String, message: String) {
                        val date = LocalDateTime.now().format(formatter)
                        val outputString = "[$date] [$category] [$mode] $message"
                        println(outputString)
                        history.add(outputString)
                }

                fun saveToFile() {
                        val timestamp = LocalDateTime.now().format(formatter)
                        File("./$dir").mkdir()
                        File("./$dir/log $timestamp.txt").printWriter().use { out -> history.forEach { out.println(it) } }
                        history.clear()
                }
        }
}
