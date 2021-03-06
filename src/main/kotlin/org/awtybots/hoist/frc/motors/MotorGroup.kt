package org.awtybots.hoist.frc.motors

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.BaseTalon
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.NeutralMode
import edu.wpi.first.wpiutil.math.MathUtil.clamp
import kotlin.math.*

import org.awtybots.hoist.frc.config.DriveConfig

class MotorGroup<T : BaseTalon>(
  val motorList: Array<T>,
  private val config: DriveConfig
) {
  private var integralError: Double = 0.0
  private var lastVelocityError: Double = 0.0

  var goalVelocity: Double = 0.0
    set(velocity) {
      this.integralError = 0.0
      this.lastVelocityError = 0.0
      field = velocity
    }

  fun setMotorOutput(percent: Double) {
    motorList.forEach { m -> m.set(ControlMode.PercentOutput, percent) }
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
    setMotorOutput((config.kP * velocityError) + (config.kI * integralError) + (config.kD * accelerationError))
  }

  private fun getWheelVelocity() =
      humanizeVelocity(motorList[0].getSelectedSensorVelocity().toDouble())
  
  private fun getWheelDistance() =
      humanizeDistance(motorList[0].getSelectedSensorPosition().toDouble())

  private fun humanizeVelocity(nativeVelocity: Double): Double =
      nativeVelocity / 2048.0 * 10.0 * (config.kWheelDiameter * PI)

  private fun humanizeDistance(nativeDistance: Double): Double =
      nativeDistance / 2048.0 * 10.0 * (config.kWheelDiameter * PI)
}
