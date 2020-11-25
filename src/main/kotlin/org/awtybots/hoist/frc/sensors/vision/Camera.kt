package org.awtybots.hoist.frc.sensors.vision

interface Camera {
    val mountingHeight: Double
    val mountingAngle: Double
    
    val hasVisibleTarget: Boolean
    val xOffset: Double?
    val yOffset: Double?
}