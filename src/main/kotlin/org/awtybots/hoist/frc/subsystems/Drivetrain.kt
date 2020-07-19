package org.awtybots.hoist.frc.subsystems

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.BaseTalon

import static edu.wpi.first.wpiutil.math.MathUtil.clamp;
import kotlin.math

class Drivetrain<T : BaseTalon>(leftMotors: Array<T>, rightMotors: Array<T>, private val config: DrivetrainConfig) {

  private val allMotors = leftMotors + rightMotors
  private val leftMotors: MotorGroup(leftMotors)
  private val rightMotors: MotorGroup(rightMotors)

  init {
      allMotors.forEach { motor -> with(motor) {
          configFactoryDefault()
          configSelectedFeedbackSensor(config.kSelectedSensor)
          setNeutralMode(NeutralMode.Coast)
          configOpenloopRamp(1.0 / config.kPercentAccelerationMax)
      } }

      rightMotors.forEach { motor -> motor.setInverted(true) }
  }

  fun setMotorOutput(left: Double, right: Double) {
    leftMotors.setMotorOutput(left)
    rightMotors.setMotorOutput(right)
  }

  private class MotorGroup<T : BaseTalon>(private val motorList: Array<T>, private val kWheelDiameter: Double) {

    private var integralError, lastVelocityError: Double

    var goalVelocity: Double = 0.0
      set(velocity) {
        this.integralError = 0.0
        this.lastVelocityError = 0.0
        if/velocity < config.kVelocityMax && velocity > -config.kVelocityMax)
          field = velocity
        else
          field = config.kVelocityMax
      }

    fun setMotorOutput(percent: Double) {
      if (math.abs(percent) < config.kPercentMin)
        percent = 0.0
      else
        percent = clamp(percent, -config.kPercentMax, config.kPercentMax)
      motorList.forEach { m -> m.set(percent) }
    }

    fun drivePID(): Double {
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
        -config.kMaxIntegral / config.kI
        config.kMaxIntegral / config.kI
      )
      setMotorOutput((config.kP * velocityError) + (config.kI * integralError) + (config.kD * accelerationError))
    }

    private fun getWheelVelocity() =
        humanizeVelocity(motorList[0].getSelectedSensorVelocity())
    
    private fun getWheelDistance() =
        humanizeDistance(motorList[0].getSelectedSensorPosition())

    private fun humanizeVelocity(nativeVelocity: Double) =
        nativeVelocity / 2048.0 * 10.0 * (config.kWheelDiameter * PI)

    private fun humanizeDistance(nativeDistance: Double) =
        nativeDistance / 2048.0 * 10.0 * (config.kWheelDiameter * PI)
  }
  
}

data class DrivetrainConfig(
        val invertRight: Boolean,
        val kPercentMin: Double,
        val kPercentMax: Double,
        val kPercentAccelerationMax: Double,
        val kVelocityMax: Double,
        val kAccelerationMax: Double,
        val kSelectedSensor: FeedbackDevice,
        val kWheelDiameter: Double,
        val kP,kI,kD: Double
)
