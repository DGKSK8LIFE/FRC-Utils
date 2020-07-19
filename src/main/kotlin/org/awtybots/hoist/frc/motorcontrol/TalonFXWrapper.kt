package org.awtybots.hoist.frc.motorcontrol

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.can.TalonFX

class TalonFXWrapper(id: Int) : MotorController, TalonFX(id) {
  private val kTicksPerRev = 2048
  init {
      super.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor)
  }

  override fun setVoltage(volts: Double) {
    super.set(ControlMode.PercentOutput, volts/12)
  } // TODO Voltage compensation

  override fun setRPM(rpm: Double) { // TODO ensure that PID is configured / add PID slot support
    super.set(ControlMode.Velocity, rpmToNativeUnits(rpm))
  }

  override fun getRPM(): Double =
          nativeUnitsToRPM(super.getSelectedSensorVelocity().toDouble())

  private fun nativeUnitsToRPM(native: Double): Double =
          native / kTicksPerRev * 10 * 60

  private fun rpmToNativeUnits(rpm: Double): Double =
          rpm / 60 / 10 * kTicksPerRev
}
