package org.awtybots.hoist.frc

class Logger(var writeToFile: Boolean) {
    fun debug(category: String, message: String) {
        log(category, "Debug", message)
    }

    fun warn(category: String, message: String) {
        log(category, "Warn", message) }

    fun error(category: String, message: String) {
        log(category, "Error", message)
    }

    private fun log(category: String, mode: String, message: String) {
        println("$category -> $mode -> $message")
    }
}
