package org.awtybots.hoist.frc

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

open class CompetitionBot(
    
): TimedRobot() {
  
    override fun robotInit() { }

    override fun robotPeriodic() = CommandScheduler.getInstance().run()

    override fun disabledInit() { }

    override fun autonomousInit() {
    }

    override fun teleopInit() {
        
    }

}
