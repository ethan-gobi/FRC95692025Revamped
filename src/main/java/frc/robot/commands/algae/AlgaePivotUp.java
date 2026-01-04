package frc.robot.commands.algae;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.algae.AlgaeEndeffactorSubsystem;

/**
 * Moves the algae pivot to the upper position.
 */
public class AlgaePivotUp extends Command {

  private final AlgaeEndeffactorSubsystem algaeEndeffactorSubsystem;

  public AlgaePivotUp(AlgaeEndeffactorSubsystem algaeEndeffactorSubsystem) {
    this.algaeEndeffactorSubsystem = algaeEndeffactorSubsystem;
    addRequirements(algaeEndeffactorSubsystem);
  }

  @Override
  public void execute() {
    algaeEndeffactorSubsystem.algaeSetPoint(-0.25);
  }

  @Override
  public void end(boolean interrupted) {
    algaeEndeffactorSubsystem.algaePivotStop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
