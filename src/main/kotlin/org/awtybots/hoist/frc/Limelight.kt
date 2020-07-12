package org.awtybots.hoist.frc

import java.util.Optional

import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.networktables.NetworkTableType
import edu.wpi.first.networktables.NetworkTableValue

class Limelight(val mountingHeight: Double, val mountingAngle: Double, val skewAngle: Double) {

  private val netTable = NetworkTableInstance.getDefault().getTable("limelight")

  fun hasVisibleTarget(): Boolean {
    val res: Optional<Double> = getValue(TableEntry.HasValidTargets)
    return res.isPresent && res.get() == 1.0
  }

  /** Get the horizontal angle from crosshair to center of target
   * @return Angle between -29.8deg and 29.8deg
   */
  fun getXOffset(): Optional<Double> = getValue(TableEntry.TargetXOffset)

  /** Get the vertical angle from crosshair to center of target
   * @return Angle between -24.85deg and 24.85deg
   */
  fun getYOffset(): Optional<Double> = getValue(TableEntry.TargetYOffset)

  /**
   * Get area of target bounding box
   * @return area in percent of image (0-100)
   */
  fun getTargetArea(): Optional<Double> = getValue(TableEntry.TargetArea)

  /**
   * Get the rotation of the target bounding box
   * @return Angle between -90deg to 0deg
   */
  fun getTargetSkew(): Optional<Double> = getValue(TableEntry.TargetSkew)

  /**
   * Get the active pipeline's contribution to overall latency (add at least 11ms
   * to account for image capture latency
   * @return latency in milliseconds
   */
  fun pipelineLatency(): Optional<Double> = getValue(TableEntry.PipelineLatency)

  /**
   * Set active pipeline
   * @param[num] number of Pipeline to set (0..9)
   * @return success
   */
  fun setPipeline(num: Int): Boolean {
    return if (num < 10 && num > -1)
      setNumber(TableEntry.CurrentPipeline, num)
    else
      false.also { println("Limelight.setPipeline: pipeline number must be in range 0..9") }
  }

  /**
   * Enable Driver or Vision Processor mode.
   * @param[driver] true to enable driver mode, false to enable vision processor mode
   * @return success
   */
  fun enableDriverMode(driver: Boolean): Boolean {
    return if(driver)
      setNumber(TableEntry.OperationMode, 1.0)
    else
      setNumber(TableEntry.OperationMode, 0.0)
  }

  /**
   * Set the mode of the LEDs (Default, Off, Blinking, On)
   */
  fun setLEDMode(mode: LEDMode): Boolean = setNumber(TableEntry.LedMode,mode.ordinal)

  /**
   * Gets the value of given entry in Limelight's table.
   * @param[entry] Entry of Limelight's NetworkTable to read from
   * @return [Optional] containing the value of the entry, is empty if
   * entry is not of type [Double][NetworkTableType.kDouble], or if failed to read entry
   */
  private fun getValue(entry: TableEntry): Optional<Double> {
    val result: NetworkTableValue = netTable.getEntry( entry.getter ).value

    return if (result.type == NetworkTableType.kDouble)
      Optional.of(result.double)
    else
      Optional.empty()
  }

  /**
   * @param[entry] Entry of Limelight's [NetworkTable] to write to
   * @param[value] The number to write into the given entry
   * @return <code>false</code> if given [TableEntry] has no
   * setter or if failed to write number to table.
   * <code>true</code> on success.
   */
  private fun setNumber(entry: TableEntry, value: Number): Boolean {
    return if (entry.setter != "")
      netTable .getEntry(entry.setter).setNumber(value)
    else {
      false.also { println("No setter available for TableEntry") }
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
   * A series of enums used within the Limelight class to access
   * various entries in the Limelight's [NetworkTable].
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
    LedMode("LEDMode", "LEDMode")
  }
}
