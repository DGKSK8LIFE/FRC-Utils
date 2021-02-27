package org.awtybots.frc.botplus.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.awtybots.frc.botplus.Logger

open class AnalogInputCommand : CommandBase() {

    internal lateinit var controller: Controller
    private var firstRun: Boolean = true

    override fun execute() = analogExecute(
        this.controller.getControllerValues()
    )

    open fun analogExecute(controllerValues: ControllerValues?) {
        if (firstRun) {
            Logger("AnalogInputCommand").warn("Override the analogExecute() method in the AnalogInputCommand")
            firstRun = false
        }
    }
}
