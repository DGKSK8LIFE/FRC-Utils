package org.awtybots.hoist.frc.sensors.vision

import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.networktables.NetworkTableType
import edu.wpi.first.networktables.NetworkTableValue

import org.awtybots.hoist.frc.Logger

/**
  Limelight interface.

  @property[mountingHeight] how far above the ground the Limelight is (meters)
  @property[mountingAngle] how much the Limelight is tilted upwards (degrees)
 */
class Limelight(override val mountingHeight: Double, override val mountingAngle: Double) : Camera {

  private val netTable = NetworkTableInstance.getDefault().getTable("limelight")
  private val logger = Logger("Limelight")

  override val hasVisibleTarget: Boolean
    get() {
      val res: Double? = getValue(TableEntry.HasValidTargets)
      return res != null && res == 1.0
    }

  /**
   * the horizontal angle (between -29.8deg and 29.8deg) from crosshair to center of target
   */
  override val xOffset: Double?
    get() = getValue(TableEntry.TargetXOffset)

  /**
   * the vertical angle (between -24.85deg and 24.85deg) from crosshair to center of target
   */
  override val yOffset: Double?
    get() = getValue(TableEntry.TargetYOffset)

  /**
   * area of target bounding box as percent of image (0-100)
   */
  val targetArea: Double?
    get() = getValue(TableEntry.TargetArea)

  /**
   * the rotation (between -90deg to 0deg) of the target bounding box
   */
  val getTargetSkew: Double?
    get() = getValue(TableEntry.TargetSkew)

  /**
   * the active pipeline's contribution to overall latency (milliseconds, add at least 11ms
   * to account for image capture latency)
   */
  val pipelineLatency: Double?
    get() = getValue(TableEntry.PipelineLatency)

  /**
   * current camera pipeline (integer from 0 to 9, inclusive)
   */
  var pipeline: Int?
    set(value) {
      if(value != null && value < 10 && value > -1)
        setValue(TableEntry.CurrentPipeline, value)
      else
        logger.error("Pipeline number must be in range 0..9")
    }
    get() = getValue(TableEntry.CurrentPipeline)?.toInt()

  /**
   * true is driver mode, false is vision processing mode
   */
  var driverMode: Boolean
    get() = getValue(TableEntry.OperationMode) == 1.0
    set(value) {
      setValue(TableEntry.OperationMode, if(value) 1.0 else 0.0)
    }

  /**
   * the current mode of the LEDs (Default, Off, Blinking, On)
   */
  var ledMode: LEDMode?
    set(value) {
      if(value != null)
        setValue(TableEntry.LEDMode, value.ordinal)
    }
    get() {
      val ordinal = getValue(TableEntry.LEDMode)
      if(ordinal == null) return null

      val ordinalInt = ordinal.toInt()
      if(ordinalInt >= 0 && ordinalInt < LEDMode.values().size)
        return LEDMode.values()[ordinalInt]
      else
        return null
    }

  /**
   * Gets the value of given entry in Limelight's table.
   * @param[entry] Entry of Limelight's NetworkTable to read from
   * @return [Optional] containing the value of the entry, is empty if
   * entry is not of type [Double][NetworkTableType.kDouble], or if failed to read entry
   */
  private fun getValue(entry: TableEntry): Double? {
    val result: NetworkTableValue = netTable.getEntry( entry.getter ).value
    return if (result.type == NetworkTableType.kDouble)
      result.double
    else
      null
  }

  /**
   * @param[entry] Entry of Limelight's [NetworkTable] to write to
   * @param[value] The number to write into the given entry
   * @return <code>false</code> if given [TableEntry] has no
   * setter or if failed to write number to table.
   * <code>true</code> on success.
   */
  private fun setValue(entry: TableEntry, value: Number): Boolean {
    return if (entry.setter != "")
      netTable.getEntry(entry.setter).setNumber(value)
    else {
      false.also { logger.error("No setter available for TableEntry $entry") }
    }
  }

  enum class LEDMode {
    /**
     * Use the default mode set in the active Pipeline
     */
    PipelineDefault,
    Off,
    Blinking,
    On
  }

  /**
   * Mappings to the Limelight's [NetworkTable] entries.
   */
  enum class TableEntry(val getter: String, val setter: String = "") {
    HasValidTargets("tv"),
    TargetXOffset("tx"),
    TargetYOffset("ty"),
    TargetArea("ta"),
    TargetSkew("ts"),
    PipelineLatency("tl"),
    CurrentPipeline("getpipe", "pipeline"),
    OperationMode("camMode", "camMode"),
    LEDMode("LEDMode", "LEDMode")
  }
}
