package org.awtybots.hoist.frc.sensors.vision

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

import org.awtybots.hoist.frc.math.*
import kotlin.random.Random

class VirtualCamera : Camera {
    override val mountingHeight = 0.50
    override val mountingAngle = 30.0
    
    override val hasVisibleTarget = true
    override val xOffset: Double? = 0.0
    override val yOffset: Double?
        get() {
            val angle = Random.nextDouble(-25.0, 25.0)
            println("angle: ${angle} degrees")
            return angle
        }
}

class VisionTest {
    @Test
    fun functionalityTest() {
        val camera = VirtualCamera()
        val target = VisionTarget(camera, 2.5)
        println()

        val targetDisplacement = target.targetDisplacement!!
        println("target displacement: ${targetDisplacement}")

        val simulation = Simulation(simulationIterations = 10, simulationStep = 0.05, ballRadius = 0.09, ballMass = 0.14, launchAngle = 55.0)
        val launchVelocity = simulation.findOptimalLaunchVelocity(targetDisplacement)
        val launchVelocityMagnitude = launchVelocity?.magnitude
        println("launch velocity: ${launchVelocityMagnitude} meters/second")

        if(launchVelocity == null) return

        val flywheel = Flywheel(flywheelRadius = 0.05)
        val motorRpm = flywheel.ballVelocityToMotorRpm(launchVelocity)
        println("motor RPM: ${motorRpm} revolutions/minute")
    }
}
