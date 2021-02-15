package org.awtybots.frc.botplus.subsystems

import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.BaseTalon
import edu.wpi.first.wpiutil.math.MathUtil.clamp
import org.awtybots.frc.botplus.motors.MotorGroup
import org.awtybots.frc.botplus.config.DriveConfig
import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpilibj2.command.SubsystemBase
import kotlin.math.*

class Drivetrain<T : BaseTalon>(
    private val config: DriveConfig,
    private val leftMotors: MotorGroup<T>,
    private val rightMotors: MotorGroup<T>
) : SubsystemBase() {

  init {
    val allMotors = MotorGroup(leftMotors.motorList + rightMotors.motorList, config)
    allMotors.motorList.forEach { motor ->
      motor.configFactoryDefault()
      motor.configSelectedFeedbackSensor(config.kSelectedSensor)
      motor.setNeutralMode(NeutralMode.Coast)
      motor.configOpenloopRamp(1.0 / config.kPercentAccelerationMax)
    }

    rightMotors.motorList.forEach { motor -> motor.setInverted(true) }

    CommandScheduler.getInstance().registerSubsystem(this);
  }

  override fun periodic() {
    leftMotors.periodic()
    rightMotors.periodic()
  }

  fun setMotorOutput(left: Double, right: Double) {
    leftMotors.motorOutput = outputDeadzone(left)
    rightMotors.motorOutput = outputDeadzone(right)
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
      clamp(x, -1.0, 1.0) * config.kPercentMax
  }
}
