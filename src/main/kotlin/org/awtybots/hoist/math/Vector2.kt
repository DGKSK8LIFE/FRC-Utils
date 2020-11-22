package org.awtybots.hoist.math

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import kotlin.math.*

class Vector2(var x: Double = 0.0, var y: Double = 0.0) {

  constructor(n: Double): this(n, n)
  constructor(b: Vector2): this(b.x, b.y)
  constructor(r: Double, theta: Double, polar: Boolean = true): this() {
    if(polar) {
      this.x = r * cos(theta)
      this.y = r * sin(theta)
    }
  }

  // vector can be represented by polar coords: magnitude * e^(i * theta)

  var magnitude: Double
    get() = sqrt(this.x.pow(2.0) + this.y.pow(2.0))
    set(value) {
      val factor: Double = value / this.magnitude
      applyUnaryFunction { m -> m * factor}
    }

  var theta: Double
    get() = floor((atan2(this.y,this.x) * (180/PI)) % 360)
    set(value) { // this math might be wrong
        val s: Double = sin(value * (PI/180))
        val c: Double = cos(value * (PI/180))
        val x2: Double = x * c - y * s
        val y2: Double = x * s + y * c
        this.x = x2
        this.y = y2
    }

  /// ---- Math ---- ///
  fun normalize() { this.magnitude = 1.0 }

  fun dot(b: Vector2): Double {
    with (this * b) {
      return this.x + this.y
    }
  }

  /// ---- Converters ---- ///
  fun clone(): Vector2 = Vector2(this)

  fun print(name: String): Vector2 {
    SmartDashboard.putString(name, this.toString())
    return this
  }

  override fun toString(): String {
    val rounded = this.clone().applyUnaryFunction { n -> round(n * 10.0) / 10 }
    return "( ${rounded.x}, ${rounded.y} )"
  }

  /// ---- Operators ---- ///
  operator fun plus(b: Vector2): Vector2 = applyBinaryFunction({ m,n -> m + n }, b)
  operator fun minus(b: Vector2): Vector2 = applyBinaryFunction({ m,n -> m - n }, b)
  operator fun times(b: Vector2): Vector2 = applyBinaryFunction({ m,n -> m * n }, b)
  operator fun div(b: Vector2): Vector2 = applyBinaryFunction({ m,n -> m / n}, b)
  operator fun plusAssign(b: Vector2) {
    plus(b)
  }

  operator fun times(n: Double): Vector2 = applyUnaryFunction { m -> m * n}
  operator fun div(n: Double): Vector2 = applyUnaryFunction { m -> m / n}
  operator fun unaryPlus(): Vector2 = this
  operator fun unaryMinus(): Vector2 = applyUnaryFunction { m -> -m }

  /// ---- Utilities ---- ////
  fun applyUnaryFunction(func: (Double) -> Double): Vector2 {
    return Vector2(func(x), func(y))
  }

  private fun applyBinaryFunction(func: (Double, Double) -> Double, b: Vector2): Vector2 {
    return Vector2(func(this.x, b.x), func(this.y, b.y))
  }

}
