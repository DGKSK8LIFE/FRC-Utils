package org.awtybots.frc.botplus

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class LoggerTest {
    @Test
    fun functionalityTest() {
        val loggerA = Logger("categoryA")
        val loggerB = Logger("categoryB")

        loggerA.debug("debug message")
        loggerB.warn("warn message")
        loggerA.error("error message")

        Logger.saveToFile()
    }
}
