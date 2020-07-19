package org.awtybots.hoist.frc.subsytems

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.NeutralMode
import org.awtybots.hoist.frc.motorcontrol.MotorController

class Drivetrain<T : MotorController>(private val leftMotors: Array<T>, private val rightMotors: Array<T>, private val config: DrivetrainConfig) {

    private val allMotors = leftMotors + rightMotors

    init {
        allMotors.forEach { motor -> with(motor) {
            configFactoryDefault()
            configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor)
            setNeutralMode(NeutralMode.Coast)
            configOpenloopRamp(1.0 / config.kPercentAccelerationMax)
        } }
        rightMotors.forEach { motor -> motor.setInverted(true) }
    }
}

data class DrivetrainConfig(
        val invertRight: Boolean,
        val kPercentMin: Double,
        val kPercentMax: Double,
        val kPercentAccelerationMax: Double,
        val kVelocityMax: Double,
        val kAccelerationMax: Double
)
