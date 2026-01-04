package frc.robot.commands.algae;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.algae.AlgaeEndeffactorSubsystem;

/**
 * Lowers the algae pivot to the resting position.
 */
public class AlgaePivotDown extends Command {

  private final AlgaeEndeffactorSubsystem algaeEndeffactorSubsystem;

  public AlgaePivotDown(AlgaeEndeffactorSubsystem algaeEndeffactorSubsystem) {
    this.algaeEndeffactorSubsystem = algaeEndeffactorSubsystem;
    addRequirements(algaeEndeffactorSubsystem);
  }

  @Override
  public void execute() {
    algaeEndeffactorSubsystem.algaeSetPoint(0);
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
