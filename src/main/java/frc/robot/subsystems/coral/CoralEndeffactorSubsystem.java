package frc.robot.subsystems.coral;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.HardwareMap;

/**
 * Controls coral intake motors and beam break detection.
 *
 * <p>Subsystem visuals: <a
 * href="https://www.firstinspires.org/sites/default/files/uploads/resource_library/frc/game-and-season-info/game-day-survey/2025-game-pieces.png">REEFSCAPE
 * coral game piece reference</a>.
 */
public class CoralEndeffactorSubsystem extends SubsystemBase {
  private final SparkMax leftCoralMotor = new SparkMax(HardwareMap.IT_CORAL_L, MotorType.kBrushless);
  private final SparkMax rightCoralMotor = new SparkMax(HardwareMap.IT_CORAL_R, MotorType.kBrushless);

  private double leftDiffRatio = 1;
  private double rightDiffRatio = 0.4;

  private final DigitalInput beamBreak1 = new DigitalInput(HardwareMap.IT_BEAMBREAK1);
  private final DigitalInput beamBreak2 = new DigitalInput(HardwareMap.IT_BEAMBREAK2);

  public CoralEndeffactorSubsystem() {
    leftCoralMotor.setInverted(false);
    rightCoralMotor.setInverted(true);
  }

  public void setVolt(double volt) {
    leftCoralMotor.setVoltage(volt);
    rightCoralMotor.setVoltage(volt);
  }

  public void setVoltDiff(double volt) {
    leftCoralMotor.setVoltage(leftDiffRatio * volt);
    rightCoralMotor.setVoltage(rightDiffRatio * volt);
  }

  public void stop() {
    leftCoralMotor.setVoltage(0);
    rightCoralMotor.setVoltage(0);
  }

  public void diffRight() {
    leftDiffRatio = 0.4;
    rightDiffRatio = 1;
  }

  public void diffLeft() {
    leftDiffRatio = 1;
    rightDiffRatio = 0.4;
  }

  public boolean beamBroken2() {
    return beamBreak2.get();
  }

  public boolean beamBroken1() {
    return beamBreak1.get();
  }

  public boolean isObjectIn() {
    return !beamBroken2() && beamBroken1();
  }
}
