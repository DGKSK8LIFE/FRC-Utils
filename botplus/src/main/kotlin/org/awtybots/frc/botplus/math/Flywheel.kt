package org.awtybots.frc.botplus.math

import kotlin.math.PI
import org.awtybots.frc.botplus.motors.Motor

/**
 * A utility class that abstracts the flywheel math.
 *
 * @property[flywheelRadius] the radius of the flywheel that comes into contact with the ball (meters)
 * @property[gearRatio] the ratio between motor revolutions and flywheel revolutions
 * @property[efficiency] the percentage of flywheel motion that gets successfully transferred to the ball (from 0 to 1)
 */
class Flywheel(
    flywheelRadius: Double,
    motor: Motor,
    efficiency: Double = 0.95
) {

    /*

    MATH EXPLANATION

    factor = gear ratio * flywheel circumference * efficiency / 60 / 2
    ball velocity = motor RPM * factor

    */

    private val factor = motor.gearRatio * (flywheelRadius * PI * 2.0) / 60.0 * efficiency / 2.0

    val maxBallVelocity = motor.specs.freeSpeed * factor

    /**
     * Finds the motor RPM necessary to launch a ball at a given velocity.
     *
     * @param[velocity] a Vector2 with speeds for x and y (meters/second)
     *
     * @return the motor RPM (revolutions per minute)
     */
    fun ballVelocityToMotorRpm(velocity: Velocity): Double = velocity.magnitude / factor
}
