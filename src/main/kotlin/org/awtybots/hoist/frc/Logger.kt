package org.awtybots.hoist.frc

class Logger(var writeToFile: Boolean) {
    fun Log(loggingMode: String, data: String) {
        if (writeToFile == false) {
            when (loggingMode.toLowerCase()) {
                "debug" -> println("debug: ${data}")
                "warn" -> println("warn: ${data}")
                "error" -> println("error: ${data}")
                else -> {
                    println("Invalid Logging Mode")
                }
            }
        }
    }
}