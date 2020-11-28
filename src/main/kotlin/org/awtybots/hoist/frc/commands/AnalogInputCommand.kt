package org.awtybots.hoist.frc.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.awtybots.hoist.frc.commands.Controller
import org.awtybots.hoist.frc.commands.ControllerValues

open class AnalogInputCommand: CommandBase() {

  lateinit var controller: Controller
  private var firstRun: Boolean = true

  override fun execute() = analogExecute(
    this.controller.getControllerValues()
  )

  open fun analogExecute(controllerValues: ControllerValues?) {
    if (firstRun) {
      System.err.println("Override the analogExecute() method in the AnalogInputCommand!")
      firstRun = false
    }
  }

}
