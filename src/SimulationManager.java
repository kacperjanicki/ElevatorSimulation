import java.util.ArrayList;

public class SimulationManager {
    private Elevator elevatorController;
    static protected boolean simulationRunning = false;

    public SimulationManager(Elevator elevator){
        this.elevatorController = elevator;

    }

    public void startSimulation(){
        simulationRunning=true;
        ArrayList<Floor> floors = elevatorController.floors;

        for(Floor f: floors){
            f.passengers = populatePassengersOnFloor();
            f.updatePassengers();
        }
        elevatorController.wagonik.shouldStop = false;
        elevatorController.start();
    }
    // debug - delete later
    public void stopSimulation(){
        simulationRunning=false;
        elevatorController.wagonik.shouldStop = true;
    }

    static public ArrayList<Passenger> populatePassengersOnFloor(){
        int amount = (int)(Math.random() * 6);
        ArrayList<Passenger> result = new ArrayList<>();
        for(int i=0; i<amount; i++){
            result.add(new Passenger());
        }
        return result;
    };
}
