package frc.robot.subsystems.algae;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.HardwareMap;

/**
 * Controls the algae intake motor and pivot mechanism.
 */
public class AlgaeEndeffactorSubsystem extends SubsystemBase {
  private final SparkMax algaeMotor = new SparkMax(HardwareMap.IT_ALGAE, MotorType.kBrushless);
  private final SparkMax algaePivotMotor = new SparkMax(HardwareMap.IT_ALGAE_PIVOT, MotorType.kBrushless);
  private final double zero = 0.54;

  private final DutyCycleEncoder algaeDutyCycleEncoder = new DutyCycleEncoder(7);

  private final PIDController pid = new PIDController(6, 0, 0);

  public AlgaeEndeffactorSubsystem() {}

  /**
   * Moves the algae pivot toward a given duty cycle target.
   *
   * @param target desired position in duty cycle units
   */
  public void algaeSetPoint(double target) {
    double currentPosition = algaeDutyCycleEncoder.get() - zero;
    double output = pid.calculate(currentPosition, target);
    output = output * algaePivotMotor.getBusVoltage() * -1;

    SmartDashboard.putNumber("target algae", target);
    SmartDashboard.putNumber("cur algae angle", currentPosition);
    SmartDashboard.putNumber("algae ouput", output);

    algaePivotMotor.setVoltage(output);
  }

  /**
   * Applies direct voltage control to the pivot motor.
   *
   * @param volt voltage to apply
   */
  public void setVolt(double volt) {
    algaePivotMotor.setVoltage(volt);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("dio 7 algae", algaeDutyCycleEncoder.get() - zero);
  }

  public void algaePivotStop() {
    algaePivotMotor.setVoltage(0);
  }

  public void setSpeed(double volt) {
    algaeMotor.setVoltage(volt);
  }
}
