package org.awtybots.hoist.frc.motorcontrol

import com.ctre.phoenix.ErrorCode
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.NeutralMode

interface MotorController {
    fun setVoltage(volts: Double)
    fun setRPM(rpm: Double)
    fun getRPM(): Double
    fun configFactoryDefault(): ErrorCode
    fun configSelectedFeedbackSensor(dev: FeedbackDevice): ErrorCode
    fun setNeutralMode(mode: NeutralMode)
    fun configOpenloopRamp(secondsFromNeutralToFull: Double): ErrorCode
    fun setInverted(invert: Boolean)
}