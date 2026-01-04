package frc.robot.commands.algae;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.algae.AlgaeEndeffactorSubsystem;

/**
 * Holds the algae intake motor at zero output.
 */
public class AlgaeIntakeStop extends Command {
  private final AlgaeEndeffactorSubsystem algaeSubsystem;

  public AlgaeIntakeStop(AlgaeEndeffactorSubsystem algaeSubsystem) {
    this.algaeSubsystem = algaeSubsystem;
    addRequirements(algaeSubsystem);
  }

  @Override
  public void initialize() {
    algaeSubsystem.setSpeed(0);
  }

  @Override
  public void execute() {
    algaeSubsystem.setSpeed(0);
  }

  @Override
  public void end(boolean interrupted) {
    algaeSubsystem.setSpeed(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
