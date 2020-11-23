package org.awtybots.hoist.frc.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.awtybots.hoist.frc.Controller
import org.awtybots.hoist.frc.ControllerValues

open class StreamCommand: CommandBase() {

  public var controller: Controller? = null
  private var firstRun: Boolean = false

  override fun execute() = streamExecute(
    this.controller?.getControllerValues()
  )

  open fun streamExecute(controllerValues: ControllerValues?) {
    if (firstRun) {
      System.err.println("Override the streamExecute() method in the StreamCommand!")
      firstRun = false
    }
  }

}
