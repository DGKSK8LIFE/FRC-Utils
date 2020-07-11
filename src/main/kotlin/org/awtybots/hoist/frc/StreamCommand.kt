package org.awtybots.hoist.frc

import edu.wpi.first.wpilibj2.command.CommandBase
import org.awtybots.hoist.frc.Controller
import org.awtybots.hoist.frc.ControllerValues

open class StreamCommand: CommandBase() {

  private var controller: Controller? = null
  private var firstRun: Boolean = false

  fun setController(_controller: Controller?) {
    this.controller = _controller
  }

  override fun execute() = streamExecute(this.controller?.getControllerValues())

  open fun streamExecute(controllerValues: ControllerValues?) {
    if (firstRun) {
      println("Override the streamExecute() method in the StreamCommand!")
      firstRun = false
    }
  }

}
