package frc.robot.commands.algae;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.algae.AlgaeEndeffactorSubsystem;

/**
 * Runs the algae intake motors outward to eject game pieces.
 */
public class AlgaeEject extends Command {

  private final AlgaeEndeffactorSubsystem algaeEndeffactorSubsystem;

  public AlgaeEject(AlgaeEndeffactorSubsystem algaeEndeffactorSubsystem) {
    this.algaeEndeffactorSubsystem = algaeEndeffactorSubsystem;
    addRequirements(algaeEndeffactorSubsystem);
  }

  @Override
  public void execute() {
    algaeEndeffactorSubsystem.setSpeed(12);
  }

  @Override
  public void end(boolean interrupted) {
    algaeEndeffactorSubsystem.setSpeed(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
