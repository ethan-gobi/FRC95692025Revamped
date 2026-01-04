package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator.ElevatorSubsystem;

/**
 * Moves the elevator to the L1 scoring position.
 */
public class ElevatorL1Command extends Command {
  private final ElevatorSubsystem elevatorSubsystem;

  public ElevatorL1Command(ElevatorSubsystem elevatorSubsystem) {
    this.elevatorSubsystem = elevatorSubsystem;
    addRequirements(elevatorSubsystem);
  }

  @Override
  public void initialize() {
    elevatorSubsystem.moveToLevel(1);
  }

  @Override
  public void execute() {
    elevatorSubsystem.moveToLevel(1);
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
