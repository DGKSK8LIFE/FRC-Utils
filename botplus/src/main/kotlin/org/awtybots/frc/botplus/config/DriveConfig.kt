package org.awtybots.frc.botplus.config

import com.ctre.phoenix.motorcontrol.FeedbackDevice

data class DriveConfig(
    val invertRight: Boolean,
    val kPercentMin: Double,
    val kPercentMax: Double,
    val kPercentAccelerationMax: Double,
    val kVelocityMax: Double,
    val kAccelerationMax: Double,
    val kSelectedSensor: FeedbackDevice,
    val kWheelDiameter: Double,
    val kP: Double,
    val kI: Double,
    val kD: Double,
    val kMaxIntegral: Double
)
