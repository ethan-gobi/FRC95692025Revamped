package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator.ElevatorSubsystem;

/**
 * Moves the elevator to the L3 scoring position.
 */
public class ElevatorL3Command extends Command {
  private final ElevatorSubsystem elevatorSubsystem;

  public ElevatorL3Command(ElevatorSubsystem elevatorSubsystem) {
    this.elevatorSubsystem = elevatorSubsystem;
    addRequirements(elevatorSubsystem);
  }

  @Override
  public void initialize() {
    elevatorSubsystem.moveToLevel(3);
  }

  @Override
  public void execute() {
    elevatorSubsystem.moveToLevel(3);
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
