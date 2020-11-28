package org.awtybots.hoist.frc

import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard

open class CompetitionBot: TimedRobot() {

    private var autonSelector = SendableChooser<Command>()
    private var autonCommand: Command? = null

    open fun addAutonOptions() = println("Please override addAutonOptions in CompetitionBot")

    open fun bindIO() = println("Please override bindIO in CompetitionBot")
  
    override open fun disabledInit() { }

    fun addAutonOption(name: String, cmd: Command) = autonSelector.addOption(name,cmd)

    fun addAutonDefault(name: String, cmd: Command) = autonSelector.setDefaultOption(name,cmd)

    override fun robotInit() {
        addAutonOptions()
        SmartDashboard.putData(autonSelector) // TODO check if this is empty before sending it
    }

    override fun robotPeriodic() = CommandScheduler.getInstance().run()

    override fun autonomousInit() {
        autonCommand = autonSelector.getSelected()
        autonCommand?.schedule()
    }

    override fun teleopInit() {
        autonCommand?.cancel()
        bindIO()
    }

}
