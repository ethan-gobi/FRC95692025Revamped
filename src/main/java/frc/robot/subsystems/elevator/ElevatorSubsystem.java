package frc.robot.subsystems.elevator;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.ElevatorConstants;
import frc.robot.constants.Constants.HardwareMap;

/**
 * Controls the elevator motors and tracks the current height level.
 *
 * <p>Subsystem visuals: <a href="https://docs.wpilib.org/en/stable/_images/pid-elevator-diagram.png">WPILib
 * elevator schematic</a>.
 */
public class ElevatorSubsystem extends SubsystemBase {

  private final SparkMax elevatorMotorLeft = new SparkMax(HardwareMap.IT_ELEVATOR_L, MotorType.kBrushless);
  private final SparkMax elevatorMotorRight = new SparkMax(HardwareMap.IT_ELEVATOR_R, MotorType.kBrushless);

  private final RelativeEncoder leftMotorEncoder = elevatorMotorLeft.getEncoder();
  private final RelativeEncoder rightMotorEncoder = elevatorMotorRight.getEncoder();

  private final ElevatorFeedforward feedforward = new ElevatorFeedforward(0.2, 0.24, 8.97, 0.02);
  private final PIDController pid = new PIDController(0.05, 0, 0);

  private double currentLeft;
  private double currentRight;
  private int level = 1;

  public ElevatorSubsystem() {
    elevatorMotorLeft.setInverted(true);
    leftMotorEncoder.setPosition(0);
    rightMotorEncoder.setPosition(0);
  }

  /**
   * Updates the internal level field. The value is clamped to the valid range.
   *
   * @param currentLevel desired level index
   */
  public void setLevel(int currentLevel) {
    level = clampLevel(currentLevel);
  }

  /**
   * Returns the last stored level setting.
   *
   * @return elevator level index
   */
  public int getLevel() {
    return level;
  }

  /**
   * Clamps a provided level to the valid range of 0 to 3.
   *
   * @param requestedLevel desired level
   * @return clamped level
   */
  public int clampLevel(int requestedLevel) {
    return MathUtil.clamp(requestedLevel, 0, 3);
  }

  @Override
  public void periodic() {
    currentLeft = leftMotorEncoder.getPosition();
    currentRight = rightMotorEncoder.getPosition();
    SmartDashboard.putNumber("cur level", level);
  }

  /**
   * Drives the elevator to a target encoder position using PID + feedforward.
   *
   * @param target desired encoder ticks
   */
  public void setPoint(double target) {
    double pidOutputLeft = pid.calculate(currentLeft, target);
    double pidOutputRight = pid.calculate(currentRight, target);
    double feedforwardOutput = feedforward.calculate(0, 0);

    double leftVoltage = MathUtil.clamp(pidOutputLeft + feedforwardOutput, -6, 6);
    double rightVoltage = MathUtil.clamp(pidOutputRight + feedforwardOutput, -6, 6);

    elevatorMotorLeft.setVoltage(leftVoltage);
    elevatorMotorRight.setVoltage(rightVoltage);
  }

  /**
   * Returns the average encoder position for both motors.
   *
   * @return average encoder ticks
   */
  public double getPoint() {
    return (currentLeft + currentRight) / 2;
  }

  /**
   * Directly sets a voltage to both elevator motors.\n   *
   * @param volt voltage to apply
   */
  public void setVolt(double volt) {
    elevatorMotorLeft.setVoltage(volt);
    elevatorMotorRight.setVoltage(volt);
  }

  /** Stops motor output. */
  public void stop() {
    elevatorMotorLeft.set(0);
    elevatorMotorRight.set(0);
  }

  /**
   * Moves the elevator to a named setpoint and updates the stored level.
   *
   * @param targetLevel level to move toward
   */
  public void moveToLevel(int targetLevel) {
    setLevel(targetLevel);
    if (targetLevel == 3) {
      setPoint(ElevatorConstants.L3);
    } else if (targetLevel == 2) {
      setPoint(ElevatorConstants.L2);
    } else if (targetLevel == 1) {
      setPoint(ElevatorConstants.L1);
    } else {
      setPoint(ElevatorConstants.zerod);
    }
  }
}
