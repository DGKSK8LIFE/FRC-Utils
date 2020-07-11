package org.awtybots.hoist.frc

import java.util.Optional

import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.networktables.NetworkTableType
import edu.wpi.first.networktables.NetworkTableValue

class Limelight {

  private val netTable = NetworkTableInstance.getDefault().getTable("limelight")

  /**
   * Gets the value of given entry in Limelight's table.
   * @param[entry] Entry of Limelight's NetworkTable to read from
   * @return  [Optional] containing the value of the entry, is empty if
   *      entry is not of type [Double][NetworkTableType.kDouble], or if failed to read entry
   */
  fun getValue(entry: TableEntry): Optional<Double> {
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
   *     setter or if failed to write number to table.
   *     <code>true</code> on success.
   */
  fun setNumber(entry: TableEntry, value: Number): Boolean {
    return if (entry.setter != "")
      netTable.getEntry(entry.setter).setNumber(value)
    else {
      System.err.println("No setter for entry")
      false
    }
  }

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
