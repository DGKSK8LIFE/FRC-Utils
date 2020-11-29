package org.awtybots.hoist.frc

class Logger(private val writeToFile: Boolean) {
    fun debug(category: String, message: String) = println(log(category, "Debug", message))

    fun warn(category: String, message: String) = println(log(category, "Warn", message))

    fun error(category: String, message: String) = println(log(category, "Error", message))

    private fun log(category: String, mode: String, message: String): String {
        if (!writeToFile) {
            val date = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
            return "$date -> $category -> $mode -> $message"
        }
        return Nothing 
}
