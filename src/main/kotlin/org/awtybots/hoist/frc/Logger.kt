package org.awtybots.hoist.frc

class Logger(var writeToFile: Boolean) {
    fun Log(loggingMode: String, message: String) {
        if (!writeToFile) {
            when (loggingMode.toLowerCase()) {
                "debug" -> println("debug: ${message}")
                "warn" -> println("warn: ${message}")
                "error" -> println("error: ${message}")
                else -> {
                    println("Invalid Logging Mode")
                }
            }
        }
    }
}