package org.awtybots.hoist.frc

class Logger(private val writeToFile: Boolean) {
    fun debug(category: String, message: String) = log(category, "Debug", message)

    fun warn(category: String, message: String) = log(category, "Warn", message)

    fun error(category: String, message: String) = log(category, "Error", message)

    private fun log(category: String, mode: String, message: String) {
        val date: String = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        val outputString: String = "[$date] [$category] [$mode] $message"
        if (!writeToFile) {
            println(outputString)
        } else {
            File("../../../../../../logs/log.txt").printWriter().use { out -> history.forEach {
                out.println(outputString)
            } }
        }
    }
}
