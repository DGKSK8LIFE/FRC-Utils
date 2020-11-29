package org.awtybots.hoist.frc

class Logger(var writeToFile: Boolean) {
    fun debug(message: String) {
        log("Debug", message)
    }

    fun warn(message: String) {
        log("Warn", message) }

    fun error(message: String) {
        log("Error", message)
    }

    private fun log(mode: String, message: String) {
        println("$mode -> $message")
    }
}
