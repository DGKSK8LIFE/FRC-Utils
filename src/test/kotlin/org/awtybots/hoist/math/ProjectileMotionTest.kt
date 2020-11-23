package org.awtybots.hoist.math

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class ProjectileMotionTest() {
    @Test
    fun test1() {
        val simulation = Simulation(simulationIterations = 10, ballRadius = 0.09, ballMass = 0.14, launchAngle = 30.0)
        val optimalLaunchVelocity = simulation.findOptimalLaunchVelocity(Position(10.0, 2.0))
        println()
        println("optimal launch velocity : ${optimalLaunchVelocity}")
    }
}