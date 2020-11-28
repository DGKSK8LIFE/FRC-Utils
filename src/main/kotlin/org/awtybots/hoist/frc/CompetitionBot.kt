package org.awtybots.hoist.frc

import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard

open class CompetitionBot: TimedRobot() {

    private val autonOptions: MutableMap<String, Command> = mutableMapOf()
    private var autonSelector = SendableChooser<Command>()
    private var autonCommand: Command? = null

    fun addAutonOption(name: String, cmd: Command) = autonOptions.put(name,cmd)
  
    override fun robotInit() {
        if (autonOptions.isEmpty()) println("No autonomous commands were set")
        else { // TODO maybe make the first one default?
            autonOptions.forEach { (name,cmd) ->
                autonSelector.addOption(name, cmd)
            }
            SmartDashboard.putData(autonSelector)
        }
    }

    override fun robotPeriodic() = CommandScheduler.getInstance().run()

    override open fun disabledInit() { }

    open fun bindIO() { }

    override fun autonomousInit() {
        autonCommand = autonSelector.getSelected()
        autonCommand?.schedule()
    }

    override fun teleopInit() = autonCommand?.cancel()

}
