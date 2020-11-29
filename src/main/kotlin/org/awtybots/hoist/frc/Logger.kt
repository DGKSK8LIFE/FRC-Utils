package org.awtybots.hoist.frc

class Logger(var writeToFile: Boolean) {
    fun Debug(message: String) {
        Log("Debug", message)
    }

    fun Warn(message: String) {
        Log("Warn", message) }

    fun Error(message: String) {
        Log("Error", message)
    }

    private fun Log(mode: String, message: String) {
        println("""
            <---------------------------------->
                    $mode -> $message
            <---------------------------------->
        """)
    }
}
