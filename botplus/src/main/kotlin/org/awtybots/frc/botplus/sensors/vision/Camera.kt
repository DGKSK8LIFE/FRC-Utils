package org.awtybots.frc.botplus.sensors.vision

interface Camera {
    val mountingHeight: Double
    val mountingAngle: Double

    val hasVisibleTarget: Boolean
    val xOffset: Double?
    val yOffset: Double?
}