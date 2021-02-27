package org.awtybots.frc.botplus.motors

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.can.BaseMotorController
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.ctre.phoenix.motorcontrol.can.TalonSRX

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

val pro775Specs = MotorSpecs(
    freeSpeed = 18730.0,
    freeCurrent = 0.7,
    maxPower = 347.0,
    stallTorque = 0.71,
    stallCurrent = 134.0,
    feedbackDevice = FeedbackDevice.CTRE_MagEncoder_Relative,
    encoderUnitRatio = 10.0 / 4096.0
)

val bagSpecs = MotorSpecs(
    freeSpeed = 13180.0,
    freeCurrent = 1.8,
    maxPower = 149.0,
    stallTorque = 0.4,
    stallCurrent = 53.0,
    feedbackDevice = FeedbackDevice.CTRE_MagEncoder_Relative,
    encoderUnitRatio = 10.0 / 4096.0
)

val miniCimSpecs = MotorSpecs(
    freeSpeed = 5840.0,
    freeCurrent = 3.0,
    maxPower = 215.0,
    stallTorque = 1.4,
    stallCurrent = 89.0,
    feedbackDevice = FeedbackDevice.CTRE_MagEncoder_Relative,
    encoderUnitRatio = 10.0 / 4096.0
)

// motors

class Falcon500(id: Int, gearRatio: Double = 1.0) : RealMotor(TalonFX(id), gearRatio, falcon500Specs)
class SimulatedFalcon500(gearRatio: Double = 1.0) : Motor(gearRatio, falcon500Specs)

class Pro775(id: Int, gearRatio: Double = 1.0) : RealMotor(TalonSRX(id), gearRatio, pro775Specs)
class SimulatedPro775(gearRatio: Double = 1.0) : Motor(gearRatio, pro775Specs)

class Bag(id: Int, gearRatio: Double = 1.0) : RealMotor(TalonSRX(id), gearRatio, bagSpecs)
class SimulatedBag(gearRatio: Double = 1.0) : Motor(gearRatio, bagSpecs)

class MiniCim(id: Int, gearRatio: Double = 1.0) : RealMotor(TalonSRX(id), gearRatio, miniCimSpecs)
class MiniCimSpecs(gearRatio: Double = 1.0) : Motor(gearRatio, miniCimSpecs)
