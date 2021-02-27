package org.awtybots.frc.botplus.motors

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.can.BaseMotorController
import com.ctre.phoenix.motorcontrol.can.TalonFX

class SimulatedMotorController() : BaseMotorController(0, "Simulated") {
    // TODO add all motor controller features
}

open class Motor(val gearRatio: Double = 1.0, val specs: MotorSpecs) {
    open val motorController: BaseMotorController = SimulatedMotorController()

    val revsPerSecond: Double
        get() {
            return motorController.getSelectedSensorVelocity(0) * specs.encoderUnitRatio * gearRatio
        }

    var revsCompleted: Double
        get() {
            return motorController.getSelectedSensorPosition(0) * specs.encoderUnitRatio * gearRatio
        }
        set(value) {
            motorController.setSelectedSensorPosition((value / gearRatio / specs.encoderUnitRatio).toInt())
        }
}

open class RealMotor(override val motorController: BaseMotorController, gearRatio: Double = 1.0, specs: MotorSpecs) : Motor(gearRatio, specs)

data class MotorSpecs(
    val freeSpeed: Double, // rpm
    val freeCurrent: Double, // amps
    val maxPower: Double, // watts
    val stallTorque: Double, // newton-meters
    val stallCurrent: Double, // amps
    val feedbackDevice: FeedbackDevice,
    val encoderUnitRatio: Double
)

// motor specs

val falcon500Specs = MotorSpecs(
    freeSpeed = 6380.0,
    freeCurrent = 1.5,
    maxPower = 783.0,
    stallTorque = 4.69,
    stallCurrent = 257.0,
    feedbackDevice = FeedbackDevice.IntegratedSensor,
    encoderUnitRatio = 10.0 / 2048.0
)

// motors

class Falcon500(id: Int, gearRatio: Double = 1.0) : RealMotor(TalonFX(id), gearRatio, falcon500Specs)
class SimulatedFalcon500(gearRatio: Double = 1.0) : Motor(gearRatio, falcon500Specs)
