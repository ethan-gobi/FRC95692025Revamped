package frc.robot.commands.algae;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.algae.AlgaeEndeffactorSubsystem;

public class AlgaeSeqIntake extends SequentialCommandGroup{
    public AlgaeSeqIntake(AlgaeEndeffactorSubsystem algaeEndeffactorSubsystem){
        addCommands(
            new AlgaeIntake(algaeEndeffactorSubsystem)//.withTimeout(0.2),
            // new AlgaeIntakeStop(algaeEndeffactorSubsystem).withTimeout(0.2)
        );
    }
}
