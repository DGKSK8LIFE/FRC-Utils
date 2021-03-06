package org.awtybots.hoist.frc.subsystems

import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.BaseTalon
import edu.wpi.first.wpiutil.math.MathUtil.clamp
import org.awtybots.hoist.frc.motors.MotorGroup
import org.awtybots.hoist.frc.config.DriveConfig
import kotlin.math.*

class Drivetrain<T : BaseTalon>(
    private val config: DriveConfig,
    private val leftMotors: MotorGroup<T>,
    private val rightMotors: MotorGroup<T>
) {

  init {
    val allMotors = MotorGroup(leftMotors.motorList + rightMotors.motorList, config)
    allMotors.motorList.forEach { motor ->
      motor.configFactoryDefault()
      motor.configSelectedFeedbackSensor(config.kSelectedSensor)
      motor.setNeutralMode(NeutralMode.Coast)
      motor.configOpenloopRamp(1.0 / config.kPercentAccelerationMax)
    }

    rightMotors.motorList.forEach { motor -> motor.setInverted(true) }
  }

  fun setMotorOutput(left_: Double, right_: Double) {
    val right = outputDeadzone(right_)
    val left  = outputDeadzone(left_)

    leftMotors.setMotorOutput(left * config.kPercentMax)
    rightMotors.setMotorOutput(right * config.kPercentMax)
  }

  fun setGoalVelocity(left: Double, right: Double) {
    leftMotors.goalVelocity = left * config.kVelocityMax
    rightMotors.goalVelocity = right * config.kVelocityMax
  }

  fun kill() {
    setGoalVelocity(0.0, 0.0)
    setMotorOutput(0.0, 0.0)
  }

  fun softStop() = setGoalVelocity(0.0, 0.0)

  fun resetSensors() {
    leftMotors.motorList.forEach { m -> m.setSelectedSensorPosition(0) }
    rightMotors.motorList.forEach { m -> m.setSelectedSensorPosition(0) }
  }
  private fun outputDeadzone(x: Double): Double {
    return if (abs(x) < config.kPercentMin)
      0.0
    else 
      clamp(x, -config.kPercentMax, config.kPercentMax)
  }
}
