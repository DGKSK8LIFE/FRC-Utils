package org.awtybots.hoist.math

import kotlin.math.*

typealias Force = Vector2
typealias Acceleration = Vector2
typealias Velocity = Vector2
typealias Position  =Vector2

class Simulation(val simulationStep: Double = 0.1, val simulationIterations: Int = 10, val ballRadius: Double, val ballMass: Double, val launchAngle: Double, val maxSpeed: Double = 50.0) {

    fun findOptimalLaunchVelocity(goalPosition: Position) : Velocity? {
        var maxVelocity: Velocity = Velocity(r = maxSpeed, theta = launchAngle)
        var minVelocity: Velocity = Velocity()

        val resultYMax = runSimulation(goalPosition, maxVelocity)
        if(resultYMax < goalPosition.y) return null

        // binary search for goldilocks velocity
        // uncertainty on this velocity is +/- maxVelocity / (2 ^ simulationIterations)
        for(i in 1..simulationIterations) {
            val middleVelocity = (minVelocity + maxVelocity) / 2.0
            val resultY = runSimulation(goalPosition, middleVelocity)
            if(resultY > goalPosition.y) {
                maxVelocity = middleVelocity
            } else {
                minVelocity = middleVelocity
            }
        }

        return (minVelocity + maxVelocity) / 2.0
    }

    // goal position (x, y)
    // runs simulation until goal x is reached
    // returns simulation y
    private fun runSimulation(goalPosition: Position, launchVelocity: Velocity) : Double {
        var position = Position()
        var velocity = launchVelocity

        while(position.x < goalPosition.x && sign(velocity.x) == sign(position.x)) {
            position += velocity * simulationStep

            val netForce: Force = gravityForce + calculateDragForce(velocity)
            val acceleration: Acceleration = netForce / ballMass

            velocity += acceleration * simulationStep
        }

        return position.y
    }

    /// GRAVITY ///
    val g = -9.81
    val gravityForce = Force(0.0, ballMass * g)


    /// DRAG ///
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

    private fun calculateDragForce(velocity: Velocity) : Force {
        val dragForceMagnitude = dragM * velocity.magnitude.pow(2)
        val dragForceTheta = velocity.theta + 180
        return Force(dragForceMagnitude, dragForceTheta, polar = true)
    }
}