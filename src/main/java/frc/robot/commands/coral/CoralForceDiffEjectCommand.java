package frc.robot.commands.coral;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.coral.CoralEndeffactorSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.leds.LEDSubsystem;

/**
 * Ejects coral using differential wheel speeds while displaying a warning LED color.
 */
public class CoralForceDiffEjectCommand extends Command {
  private final CoralEndeffactorSubsystem coralEndeffactorSubsystem;
  private final ElevatorSubsystem elevatorSubsystem;
  private final LEDSubsystem ledSubsystem;

  public CoralForceDiffEjectCommand(
      CoralEndeffactorSubsystem coralEndeffactorSubsystem,
      ElevatorSubsystem elevatorSubsystem,
      LEDSubsystem ledSubsystem) {
    this.coralEndeffactorSubsystem = coralEndeffactorSubsystem;
    this.elevatorSubsystem = elevatorSubsystem;
    this.ledSubsystem = ledSubsystem;
    addRequirements(coralEndeffactorSubsystem, ledSubsystem);
  }

  @Override
  public void initialize() {
    ledSubsystem.setSolidColor(Color.kRed);
  }

  @Override
  public void execute() {
    coralEndeffactorSubsystem.setVoltDiff(5.5);
  }

  @Override
  public void end(boolean interrupted) {
    coralEndeffactorSubsystem.stop();
    ledSubsystem.runDefaultColor();
  }

  @Override
  public boolean isFinished() {
    return coralEndeffactorSubsystem.beamBroken2() || elevatorSubsystem.getLevel() == 0;
  }
}
