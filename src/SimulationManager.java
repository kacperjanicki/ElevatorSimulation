import java.util.ArrayList;

public class SimulationManager {
    private Elevator elevatorController;
    private boolean simulationRunning = false;

    public SimulationManager(Elevator elevator){
        this.elevatorController = elevator;
    }

    public void startSimulation(){
        simulationRunning=true;
        elevatorController.wagonik.shouldStop = false;
        elevatorController.start();
    }
    // debug - delete later
    public void stopSimulation(){
        simulationRunning=false;
        elevatorController.wagonik.shouldStop = true;
    }

}
