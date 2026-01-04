package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator.ElevatorSubsystem;

/**
 * Cycles the elevator through predefined height setpoints.
 */
public class ElevatorCycle extends Command {
  private final ElevatorSubsystem elevatorSubsystem;
  private final int direction;

  /**
   * Creates a cycling command that moves the elevator up or down one level.
   *
   * @param elevatorSubsystem elevator being controlled
   * @param direction +1 to raise, -1 to lower
   */
  public ElevatorCycle(ElevatorSubsystem elevatorSubsystem, int direction) {
    this.elevatorSubsystem = elevatorSubsystem;
    this.direction = direction;
    addRequirements(elevatorSubsystem);
  }

  @Override
  public void initialize() {
    int targetLevel = elevatorSubsystem.clampLevel(elevatorSubsystem.getLevel() + direction);
    elevatorSubsystem.setLevel(targetLevel);
    Command nextCommand = createLevelCommand(targetLevel);
    nextCommand.schedule();
  }

  private Command createLevelCommand(int targetLevel) {
    return switch (targetLevel) {
      case 3 -> new ElevatorL3Command(elevatorSubsystem);
      case 2 -> new ElevatorL2Command(elevatorSubsystem);
      case 1 -> new ElevatorL1Command(elevatorSubsystem);
      default -> new ElevatorZeroCommand(elevatorSubsystem);
    };
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
