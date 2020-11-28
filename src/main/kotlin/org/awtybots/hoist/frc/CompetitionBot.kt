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

    fun addAutonOption(name: String, cmd: Command) {
        if (cmd != null && name != null)
            autonOptions.put(name,cmd)
    }
  
    override fun robotInit() {
        // create auton selector on smartdashboard (list of something? enums?)
        var autonSelector = SendableChooser<Command>() // TODO THIS IS BROKEN
        if (!autonOptions.isEmpty())
            for ((name,cmd) in autonOptions) {
                autonSelector.addOption(name, cmd)
            }
    }

    override fun robotPeriodic() = CommandScheduler.getInstance().run()

    override open fun disabledInit() { /* whatever let them override it */ }

    override fun autonomousInit() {
        // start selected auton command
    }

    override fun teleopInit() {
        // cancel auton command
        // bind OI
        // set default command for driving subsytem
    }

}
