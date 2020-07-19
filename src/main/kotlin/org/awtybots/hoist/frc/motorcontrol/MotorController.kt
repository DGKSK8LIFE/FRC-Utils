package org.awtybots.hoist.frc.motorcontrol

internal interface MotorController {
    fun setVoltage(volts: Double)
    fun setRPM(rpm: Double)
    fun getRPM(): Double
}