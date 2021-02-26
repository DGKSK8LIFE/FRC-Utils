package org.awtybots.frc.botplus.subsystems

import com.ctre.phoenix.motorcontrol.NeutralMode
import edu.wpi.first.wpiutil.math.MathUtil.clamp
import org.awtybots.frc.botplus.motors.MotorGroup
import org.awtybots.frc.botplus.motors.Motor
import org.awtybots.frc.botplus.config.DriveConfig
import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpilibj2.command.SubsystemBase
import kotlin.math.*

open class Drivetrain<T : Motor>(
    private val config: DriveConfig,
    private val leftMotors: MotorGroup<T>,
    private val rightMotors: MotorGroup<T>
) : SubsystemBase() {

  init {
    val allMotors = MotorGroup(leftMotors.motorList + rightMotors.motorList, config)
    allMotors.motorList.forEach { motor ->
      motor.motorController.configFactoryDefault()
      motor.motorController.configSelectedFeedbackSensor(config.kSelectedSensor)
      motor.motorController.setNeutralMode(NeutralMode.Coast)
      motor.motorController.configOpenloopRamp(1.0 / config.kPercentAccelerationMax)
    }

    rightMotors.motorList.forEach { motor -> motor.motorController.setInverted(true) }

    CommandScheduler.getInstance().registerSubsystem(this);
  }

  override fun periodic() {
    leftMotors.periodic()
    rightMotors.periodic()
  }

  fun setMotorRawOutput(left: Double, right: Double) {
    leftMotors.motorOutput = outputDeadzone(left)
    rightMotors.motorOutput = outputDeadzone(right)
  }

  fun setMotorVelocityOutput(left: Double, right: Double) {
    leftMotors.goalVelocity = left * config.kVelocityMax
    rightMotors.goalVelocity = right * config.kVelocityMax
  }

  fun kill() {
    setMotorVelocityOutput(0.0, 0.0)
    setMotorRawOutput(0.0, 0.0)
  }

  fun softStop() = setMotorVelocityOutput(0.0, 0.0)

  fun resetSensors() {
    leftMotors.motorList.forEach { m -> m.motorController.setSelectedSensorPosition(0) }
    rightMotors.motorList.forEach { m -> m.motorController.setSelectedSensorPosition(0) }
  }
  private fun outputDeadzone(x: Double): Double {
    return if (abs(x * config.kPercentMax) < config.kPercentMin)
      0.0
    else 
      clamp(x, -1.0, 1.0) * config.kPercentMax
  }
}
