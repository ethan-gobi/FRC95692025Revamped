package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.Constants.ElevatorConstnats;
import frc.robot.subsystems.elevator.ElevatorSubsystem;

public class ElevatorL2Command extends Command {
    private final ElevatorSubsystem elevatorSubsystem; 

    public ElevatorL2Command(ElevatorSubsystem elevatorSubsystem){
        this.elevatorSubsystem = elevatorSubsystem;
        addRequirements(elevatorSubsystem);

    }
    
      @Override
    public void initialize(){
        elevatorSubsystem.setLevel(2);
    }

    @Override
    public void execute(){
        elevatorSubsystem.setPoint(ElevatorConstnats.L2);
       
    }
    @Override
    public void end(boolean interrupted){
        elevatorSubsystem.stop();
    }
    @Override
    public boolean isFinished(){
        return false;// Math.abs(900 - elevatorSubsystem.getPoint()) < 20; 
    }
    
}
