import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SimulationManager {
    static private Elevator elevatorController;
    static ElevButtons elevatorButtons;
    static JButton startButton;
//    private Summoner summoner;
    static protected boolean simulationRunning = false;

    public SimulationManager(Elevator elevator,JButton start){
        elevatorController = elevator;
        startButton = start;
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

        Wagonik wagonik = elevatorController.wagonik;
        wagonik.setShouldStop(false);
        wagonik.setDirection(Direction.IDLE);
        wagonik.taskSet.clear();
        wagonik.currentPassengers.clear();
        wagonik.updateButtonsState();
        wagonik.shouldEndDetectedAt = -1;
    }
    // debug - delete later
    static public void stopSimulation(){
        simulationRunning=false;
        elevatorButtons.setLogMessage("Simulation ended");
        elevatorController.wagonik.setShouldStop(true);
        elevatorController.wagonik.setDirection(Direction.IDLE);
        elevatorController.wagonik.updateButtonsState();
        startButton.setEnabled(true);
    }

    static public ArrayList<Passenger> populatePassengersOnFloor(Floor currentFloor){
        int amount = (int)(Math.random() * 6);
        ArrayList<Passenger> result = new ArrayList<>();
        for(int i=0; i<amount; i++){
            result.add(new Passenger(currentFloor));
        }
        return result;
    }
}
