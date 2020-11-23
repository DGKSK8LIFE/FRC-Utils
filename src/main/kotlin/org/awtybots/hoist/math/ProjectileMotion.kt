package org.awtybots.hoist.math

import kotlin.math.*

typealias Force = Vector2
typealias Acceleration = Vector2
typealias Velocity = Vector2
typealias Position = Vector2

/**
 * Projectile motion simulation that accounts for drag. Use this to find the optimal launch velocity to reach a certain position.
 * 
 * @property[simulationStep] the time delta for the simulation (seconds); lower numbers will mean a more accurate result that takes longer to process
 * @property[simulationIterations] the amount of times the simulation is repeated to fine-tune the launch velocity; each additional iteration halves the uncertainty
 * @property[ballRadius] the radius of the ball (meters)
 * @property[ballMass] the mass of the ball (kilograms)
 * @property[launchAngle] the launch angle of the shooter (degrees); 0 is horizontal and 90 is straight up
 * @property[maxSpeed] the maximum speed that the shooter can launch a ball at (meters/second)
 */
class Simulation(
    val simulationStep: Double = 0.05,
    val simulationIterations: Int = 10,
    val ballRadius: Double,
    val ballMass: Double,
    val launchAngle: Double,
    val maxSpeed: Double = 50.0,
    val debugMode: Boolean = false
) {

    /**
     * Finds the optimal launch velocity to reach the specified goal position.
     * 
     * @param[goalPosition] the goal position of the launch as a 2-dimensional vector (meters); x is horizontal distance and y is altitude
     * 
     * @return the optimal launch velocity to reach the specified goal position (or null if impossible)
     */
    fun findOptimalLaunchVelocity(goalPosition: Position): Velocity? {
        var maxVelocity: Velocity = Velocity(r = maxSpeed, theta = launchAngle)
        var minVelocity: Velocity = Velocity()

        val resultYMax = runSimulation(goalPosition, maxVelocity)
        if (resultYMax < goalPosition.y) return null

        // binary search for goldilocks velocity
        // uncertainty on this velocity is +/- maxVelocity / (2 ^ simulationIterations)
        for (i in 1..simulationIterations) {
            val middleVelocity = (minVelocity + maxVelocity) / 2.0
            val resultY = runSimulation(goalPosition, middleVelocity)
            if (resultY > goalPosition.y) {
                maxVelocity = middleVelocity
            } else {
                minVelocity = middleVelocity
            }
        }

        return (minVelocity + maxVelocity) / 2.0
    }

    private fun runSimulation(goalPosition: Position, launchVelocity: Velocity): Double {
        var position = Position()
        var lastPosition = Position()
        var velocity = launchVelocity
        var time = 0.0

        debugln()
        debugln("LAUNCH @ ${velocity}")
        debugln()
        while (position.x < goalPosition.x && time < 3.0) {
            lastPosition = position.clone()
            position += velocity * simulationStep

            val netForce: Force = gravityForce + calculateDragForce(velocity)
            val acceleration: Acceleration = netForce / ballMass

            velocity += acceleration * simulationStep
            time += simulationStep
            
            debugln("time: ${time}s")
            debugln("pos: ${position}")
            debugln("vel: ${velocity}")
            debugln("acc: ${acceleration}")
            debugln("net: ${netForce}")
            debugln()
        }

        if(position.x >= goalPosition.x) {
            val alpha = (goalPosition.x - lastPosition.x) / (position.x - lastPosition.x)
            val intersectY = (position.y - lastPosition.y) * alpha + lastPosition.y
            debugln("intersects goal X at Y ${intersectY}")

            return intersectY
        }

        return -1.0
    }

    // GRAVITY
    val g = -9.81
    val gravityForce = Force(0.0, ballMass * g)

    // DRAG
    // Drag Force = 1/2 * p * C * A * v^2

    // p = density of fluid
    val dragP = 1.225
    // C = drag coefficient (might need to be measured)
    val dragC = 0.47
    // A = cross sectional area
    val dragA = ballRadius * ballRadius * PI

    // Drag Force = M * v^2
    // M = 1/2 * p * C * A
    val dragM = 0.5 * dragP * dragC * dragA

    private fun calculateDragForce(velocity: Velocity): Force {
        val v = velocity.magnitude
        val dragForceMagnitude = dragM * v * v
        val dragForceTheta = velocity.theta + 180
        return Force(r = dragForceMagnitude, theta = dragForceTheta)
    }

    private fun debugln() {
        if(debugMode) println()
    }
    private fun debugln(any: Any) {
        if(debugMode) println(any)
    }
}
