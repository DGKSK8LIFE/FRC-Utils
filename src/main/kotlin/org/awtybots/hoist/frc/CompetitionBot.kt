package org.awtybots.hoist.frc

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

open object CompetitionBot(
    
): TimedRobot() {

    // make subsystems public properties of this class?
    // what else?

    fun addAutonOption(name: String, cmd: Command) {
    }
  
    override fun robotInit() {
        // create subsystem instances? how? (they're gonna be singletons i know that much
        // create auton selector on smartdashboard (list of something? enums?)
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
