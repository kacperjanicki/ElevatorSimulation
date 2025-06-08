import java.util.ArrayList;

public class SimulationManager {
    private Elevator elevatorController;
    private boolean simulationRunning = false;

    public SimulationManager(Elevator elevator){
        this.elevatorController = elevator;
    }

    public void startSimulation(){
        simulationRunning=true;
        elevatorController.start();
    }

}
