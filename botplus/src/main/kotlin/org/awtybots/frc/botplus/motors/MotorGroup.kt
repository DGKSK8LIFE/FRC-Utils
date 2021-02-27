package org.awtybots.frc.botplus.motors

import com.ctre.phoenix.motorcontrol.ControlMode
import edu.wpi.first.wpiutil.math.MathUtil.clamp
import kotlin.math.PI
import org.awtybots.frc.botplus.config.DriveConfig

class MotorGroup<T : Motor>(
    val motorList: Array<T>,
    private val config: DriveConfig
) {
    private var integralError: Double = 0.0
    private var lastVelocityError: Double = 0.0

    private var pidEnabled = false

    var goalVelocity: Double = 0.0
        set(velocity) {
            pidEnabled = true
            this.integralError = 0.0
            this.lastVelocityError = 0.0
            field = velocity
        }

    var motorOutput: Double = 0.0
        set(percent) {
            pidEnabled = false
            motorList.forEach { m -> m.motorController.set(ControlMode.PercentOutput, percent) }
            field = percent
        }

    fun drivePID() {
        var currentVelocity = getWheelVelocity()
        var goalAcceleration = goalVelocity - currentVelocity
        var velocityError = clamp(
            goalAcceleration,
            -config.kAccelerationMax * 0.02,
            config.kAccelerationMax * 0.02
        )
        var accelerationError = (velocityError - lastVelocityError) / 0.02
        lastVelocityError = velocityError
        integralError = clamp(
            integralError + (velocityError * 0.02),
            -config.kMaxIntegral / config.kI,
            config.kMaxIntegral / config.kI
        )
        motorOutput = (config.kP * velocityError) + (config.kI * integralError) + (config.kD * accelerationError)
    }

    fun periodic() {
        if (pidEnabled)
            drivePID()
    }

    private fun getWheelVelocity() =
            humanizeVelocity(motorList[0].motorController.getSelectedSensorVelocity().toDouble())

    private fun getWheelDistance() =
            humanizeDistance(motorList[0].motorController.getSelectedSensorPosition().toDouble())

    private fun humanizeVelocity(nativeVelocity: Double): Double =
            nativeVelocity / 2048.0 * 10.0 * (config.kWheelDiameter * PI)

    private fun humanizeDistance(nativeDistance: Double): Double =
            nativeDistance / 2048.0 * 10.0 * (config.kWheelDiameter * PI)
}
