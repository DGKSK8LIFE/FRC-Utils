package org.awtybots.frc.botplus.math

import kotlin.math.PI
import kotlin.math.tan
import org.awtybots.frc.botplus.sensors.vision.Camera

/**
 * A utility class that abstracts vision target math.
 *
 * @property[camera] the camera being used to locate this vision target
 * @property[targetHeight] how high above the ground the center of the vision target is (meters)
 * @property[targetOffset] how high above the center of the vision target the actual goal is (meters)
 */
class VisionTarget(val camera: Camera, val targetHeight: Double, val targetOffset: Double = 0.0) {
    val targetDisplacement: Position?
        get() {
            val yAngle = camera.yOffset
            if (!camera.hasVisibleTarget || yAngle == null)
                return null

            // tangent = opposite / adjacent
            // opposite = vertical distance = height difference
            // adjacent = horizontal distance = opposite / tangent

            val opposite = targetHeight - camera.mountingHeight
            val tangent = tan((camera.mountingAngle + yAngle) * PI / 180.0)
            val adjacent = opposite / tangent

            return Position(adjacent, targetHeight + targetOffset - camera.mountingHeight)
        }
}
