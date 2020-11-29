package org.awtybots.hoist.frc

class Logger(private val writeToFile: Boolean) {
    fun debug(category: String, message: String) = log(category, "Debug", message)

    fun warn(category: String, message: String) = log(category, "Warn", message)

    fun error(category: String, message: String) = log(category, "Error", message)

    private fun log(category: String, mode: String, message: String) {
        if (!writeToFile) {
            val date = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
            println("[$date] [$category] [$mode] $message")
        }
    }
}
