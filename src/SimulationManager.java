import java.awt.*;
import java.util.ArrayList;

public class SimulationManager {
    private Elevator elevatorController;
    static ElevButtons elevatorButtons;
//    private Summoner summoner;
    static protected boolean simulationRunning = false;

    public SimulationManager(Elevator elevator){
        this.elevatorController = elevator;
//        this.summoner = summoner;
    }

    public void startSimulation(){
        simulationRunning=true;
        ArrayList<Floor> floors = elevatorController.floors;

        for(Floor f: floors){
//          Pokazujemy pasażerów jako ikony
            f.passengers = populatePassengersOnFloor(f);

            f.updatePassengers();
//          Pokazujemy Summonera, jeżeli piętro ma oczekujących pasażerów
            if(f.hasAwaitingPassengers()) f.summoner.setVisible(true);
        }
        elevatorController.wagonik.setShouldStop(false);
        elevatorController.start();
        elevatorController.wagonik.updateButtonsState();
    }
    // debug - delete later
    public void stopSimulation(){
        simulationRunning=false;
        elevatorController.wagonik.setShouldStop(true);
        elevatorController.wagonik.setDirection(Direction.IDLE);
        elevatorController.wagonik.updateButtonsState();
    }

    static public ArrayList<Passenger> populatePassengersOnFloor(Floor currentFloor){
        int amount = (int)(Math.random() * 6);
        ArrayList<Passenger> result = new ArrayList<>();
        for(int i=0; i<amount; i++){
            result.add(new Passenger(currentFloor));
        }
        return result;
    };
}
