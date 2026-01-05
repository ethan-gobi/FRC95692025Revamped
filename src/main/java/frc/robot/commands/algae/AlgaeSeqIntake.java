package frc.robot.commands.algae;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.algae.AlgaeEndeffactorSubsystem;

/**
 * Sequential intake routine for algae collection.
 */
public class AlgaeSeqIntake extends SequentialCommandGroup {
  public AlgaeSeqIntake(AlgaeEndeffactorSubsystem algaeEndeffactorSubsystem) {
    addCommands(new AlgaeIntake(algaeEndeffactorSubsystem));
  }
}
