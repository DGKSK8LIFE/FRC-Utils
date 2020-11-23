package org.awtybots.hoist.frc.math

import kotlin.math.PI

/**
 * A utility class that abstracts the flywheel math.
 * 
 * @property[flywheelRadius] the radius of the flywheel that comes into contact with the ball (meters)
 * @property[gearRatio] the ratio between motor revolutions and flywheel revolutions
 * @property[efficiency] the percentage of flywheel motion that gets successfully transferred to the ball (from 0 to 1)
 */
class Flywheel(
    flywheelRadius: Double,
    gearRatio: Double = 1.0,
    efficiency: Double = 0.95
) {

    /*
    
    MATH EXPLANATION
    
    ball velocity = motor RPM / 60 * gear ratio * flywheel circumference * efficiency
                  = motor RPM * factor
    
    factor = gear ratio * flywheel circumference / 60 * efficiency

    */

    private val factor = gearRatio * (flywheelRadius * PI * 2.0) / 60.0 * efficiency
    
    //val maxBallVelocity = motor.maxRpm * factor

    /**
     * Finds the motor RPM necessary to launch a ball at a given velocity.
     * 
     * @param[velocity] a Vector2 with speeds for x and y (meters/second)
     * 
     * @return the motor RPM (revolutions per minute)
     */
    fun ballVelocityToMotorRpm(velocity: Velocity) : Double = velocity.magnitude / factor

}