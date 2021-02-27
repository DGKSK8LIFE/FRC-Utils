package org.awtybots.frc.botplus.math

import org.junit.jupiter.api.Test

class ProjectileMotionTest() {
        @Test
        fun functionalityTest() {
                val powerPortPos = Position(10.0, 2.5)
                val simulation = Simulation(simulationIterations = 20, simulationStep = 0.01, ballRadius = 0.09, ballMass = 0.14, launchAngle = 55.0)
                val optimalLaunchVelocity = simulation.findOptimalLaunchVelocity(powerPortPos)?.magnitude
                println("\noptimal launch velocity : $optimalLaunchVelocity meters/second")
        }

        @Test
        fun speedTest() {
                val simulation = Simulation(simulationIterations = 10, ballRadius = 0.09, ballMass = 0.14, launchAngle = 30.0)

                val count = 100
                val startTime = System.currentTimeMillis()
                repeat(count) {
                        simulation.findOptimalLaunchVelocity(Position(10.0, 2.0))
                }
                val endTime = System.currentTimeMillis()

                val avgTime = (endTime - startTime).toDouble() / count.toDouble()
                println("\naverage simulation time: $avgTime milliseconds")
        }
}
