package org.awtybots.frc.botplus

import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard

open class CompetitionBot: TimedRobot() {

    private val autonSelector = SendableChooser<Command>()
    private var autonCommand: Command? = null

    private val logger = Logger("CompetitionBot")

    override fun robotInit() {
        addAutonOptions()
        SmartDashboard.putData(autonSelector)
    }

    override fun robotPeriodic() = CommandScheduler.getInstance().run()
  
    override fun disabledInit() = Logger.saveToFile()

    // auton

    fun addAutonOption(name: String, command: Command) = autonSelector.addOption(name, command)

    fun addAutonDefault(name: String, command: Command) = autonSelector.setDefaultOption(name, command)

    open fun addAutonOptions() = logger.warn("Override the addAutonOptions() method in CompetitionBot!")

    override fun autonomousInit() {
        autonCommand = autonSelector.getSelected()
        autonCommand?.schedule()
    }

    // teleop

    open fun bindIO() = logger.warn("Override the bindIO() method in CompetitionBot!")

    override fun teleopInit() {
        autonCommand?.cancel()
        bindIO()
    }

}
