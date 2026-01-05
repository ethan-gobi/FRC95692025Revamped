package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator.ElevatorSubsystem;

/**
 * Returns the elevator to its zero position.
 */
public class ElevatorZeroCommand extends Command {
  private final ElevatorSubsystem elevatorSubsystem;

  public ElevatorZeroCommand(ElevatorSubsystem elevatorSubsystem) {
    this.elevatorSubsystem = elevatorSubsystem;
    addRequirements(elevatorSubsystem);
  }

  @Override
  public void initialize() {
    elevatorSubsystem.moveToLevel(0);
  }

  @Override
  public void execute() {
    elevatorSubsystem.moveToLevel(0);
  }

  @Override
  public void end(boolean interrupted) {
    elevatorSubsystem.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
