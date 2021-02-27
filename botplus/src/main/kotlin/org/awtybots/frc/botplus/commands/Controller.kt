package org.awtybots.frc.botplus.commands

import edu.wpi.first.wpilibj.GenericHID.Hand
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.button.Button
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import kotlin.math.abs
import kotlin.math.sign

class Controller(port: Int) {

    private val controller = XboxController(port)

    private val kDeadzoneStick: Double = 0.2
    private val kDeadzoneTrigger: Double = 0.1

    val btnA = createButton(XboxController.Button.kA.value)
    val btnX = createButton(XboxController.Button.kX.value)
    val btnY = createButton(XboxController.Button.kY.value)
    val btnB = createButton(XboxController.Button.kB.value)
    val bmpL = createButton(XboxController.Button.kBumperLeft.value)
    val bmpR = createButton(XboxController.Button.kBumperRight.value)
    val trgL = Button { getTriggerActive(Hand.kLeft) }
    val trgR = Button { getTriggerActive(Hand.kRight) }

    /**
     * Stream controller inputs to a [command][AnalogInputCommand] continuously
     * @param[command] The command to send the controller inputs to
     */
    fun streamAnalogInputTo(command: AnalogInputCommand) {
        command.controller = this
        command.schedule()
    }

    internal fun getControllerValues(): ControllerValues {
        return ControllerValues().apply {
            leftStickX = getX(Hand.kLeft)
            leftStickY = getY(Hand.kLeft)
            rightStickX = getX(Hand.kRight)
            rightStickY = getY(Hand.kRight)
            leftTrigger = getTrigger(Hand.kLeft)
            rightTrigger = getTrigger(Hand.kRight)
        }
    }

    private fun getTrigger(hand: Hand): Double =
        deadzone(controller.getTriggerAxis(hand), kDeadzoneTrigger)

    private fun getTriggerActive(hand: Hand): Boolean =
        getTrigger(hand) > 0

    private fun getX(hand: Hand): Double =
        deadzone(controller.getX(hand), kDeadzoneStick)

    private fun getY(hand: Hand): Double =
        deadzone(-controller.getY(hand), kDeadzoneStick)

    // --- Utilities --- //
    private fun createButton(buttonID: Int): JoystickButton =
        JoystickButton(this.controller, buttonID)

    private fun deadzone(x: Double, dz: Double): Double {
        return if (abs(x) > dz)
            (x - dz * sign(x)) / (1.0 - dz)
        else
            0.0
    }
}

data class ControllerValues(val defaultValue: Double = 0.0) {
    var leftStickX: Double = defaultValue
    var leftStickY: Double = defaultValue

    var rightStickX: Double = defaultValue
    var rightStickY: Double = defaultValue

    var leftTrigger: Double = defaultValue
    var rightTrigger: Double = defaultValue
}