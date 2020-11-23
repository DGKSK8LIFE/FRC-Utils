package org.awtybots.hoist.frc.math

import edu.wpi.first.wpilibj.geometry.Pose2d
import edu.wpi.first.wpilibj.geometry.Translation2d
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import kotlin.math.*

class Vector3(var x: Double = 0.0, var y: Double = 0.0, var z: Double = 0.0) {

  constructor(n: Double): this(n, n, n)
  constructor(b: Vector3): this(b.x, b.y, b.z)
  constructor(pose: Pose2d): this() {
    this.x = pose.translation.x
    this.y = pose.translation.y
  }

  var magnitude: Double
    get() = sqrt(this.x.pow(2.0) + this.y.pow(2.0) + this.z.pow(2.0))
    set(mag) {
      val factor: Double = mag / this.magnitude
      applyFunction { m -> m * factor}
    }

  /// ---- Math ---- ///
  fun normalize() { this.magnitude = 1.0 }

  fun dot(b: Vector3): Double {
    with (this * b) {
      return this.x + this.y + this.z
    }
  }

  fun rotateZ(deg: Double): Vector3 {
    val s: Double = sin(deg * (PI/180))
    val c: Double = cos(deg * (PI/180))
    val x2: Double = x * c - y * s
    val y2: Double = x * s + y * c
    this.x = x2
    this.y = y2
    return this
  }

  fun getZAngle(): Double = floor((atan2(this.y,this.x) * (180/PI)) % 360)

  /// ---- Converters ---- ///
  fun clone(): Vector3 = Vector3(this)

  fun toTranslation2d(): Translation2d = Translation2d(this.x, this.y)

  fun print(name: String): Vector3 {
    SmartDashboard.putString(name, this.toString())
    return this
  }

  override fun toString(): String {
    val rounded = this.clone().applyFunction { n -> round(n * 10.0) / 10 }
    return "Vector3(${rounded.x}, ${rounded.y}, ${rounded.z})"
  }

  /// ---- Operators ---- ///
  operator fun plus(b: Vector3): Vector3 = applyFunctionDouble({ m,n -> m + n }, b)
  operator fun minus(b: Vector3): Vector3 = applyFunctionDouble({ m,n -> m - n }, b)
  operator fun times(b: Vector3): Vector3 = applyFunctionDouble({ m,n -> m * n }, b)
  operator fun div(b: Vector3): Vector3 = applyFunctionDouble({ m,n -> m / n}, b)

  operator fun times(n: Double): Vector3 = applyFunction { m -> m * n}
  operator fun div(n: Double): Vector3 = applyFunction { m -> m / n}

  /// ---- Utilities ---- ////
  fun applyFunction(func: (Double) -> Double): Vector3 {
    x = func(x)
    y = func(y)
    z = func(z)
    return this
  }

  private fun applyFunctionDouble(func: (Double, Double) -> Double, b: Vector3): Vector3 {
    this.x = func(this.x, b.x)
    this.y = func(this.y, b.y)
    this.z = func(this.z, b.z)
    return this
  }

}
