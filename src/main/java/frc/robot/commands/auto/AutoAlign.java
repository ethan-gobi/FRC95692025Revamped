package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swerve.SwerveSubsystem;

/**
 * Wrapper command that schedules a supplied alignment routine while managing the drive subsystem
 * requirement.
 */
public class AutoAlign extends Command {
  private final SwerveSubsystem swerveSubsystem;
  private final Command alignCommand;

  /**
   * Constructs an AutoAlign wrapper for a provided alignment command.
   *
   * @param swerveSubsystem drive subsystem that will be controlled
   * @param alignCommand command that performs the actual alignment
   */
  public AutoAlign(SwerveSubsystem swerveSubsystem, Command alignCommand) {
    this.swerveSubsystem = swerveSubsystem;
    this.alignCommand = alignCommand;
    addRequirements(swerveSubsystem);
  }

  @Override
  public void initialize() {
    alignCommand.schedule();
  }

  @Override
  public void end(boolean interrupted) {
    if (alignCommand.isScheduled()) {
      alignCommand.cancel();
    }
  }

  @Override
  public boolean isFinished() {
    return !alignCommand.isScheduled();
  }
}
