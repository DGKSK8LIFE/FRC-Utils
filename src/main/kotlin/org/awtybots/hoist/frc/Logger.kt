package org.awtybots.hoist.frc

import java.io.File
import java.time.LocalDateTime

class Logger(private val writeToFile: Boolean) {
    fun debug(category: String, message: String) = log(category, "Debug", message)

    fun warn(category: String, message: String) = log(category, "Warn", message)

    fun error(category: String, message: String) = log(category, "Error", message)

    private fun log(category: String, mode: String, message: String) {
        val date: String = LocalDateTime.now()
        val outputString: String = "[$date] [$category] [$mode] $message"
        if (!writeToFile) {
            println(outputString)
        } else {
            File("./log.txt").printWriter().use { out -> history.forEach {
                out.println(outputString)
            } }
        }
    }
}
