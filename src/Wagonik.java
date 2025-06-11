import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.*;

class Wagonik extends JPanel {
    protected boolean shouldStop;
    protected ArrayList<Floor> floors;
    protected ArrayList<Summoner> summoners;
    protected ArrayList<Passenger> currentPassengers;
    protected Floor currentFloor;
    static protected Direction direction;
    private final int elevCapacity = 5;
    Timer timer;
    // w ActionListenerze(lambdzie) nie mozna zmieniac wartosci inta,
    // wiec dajemy int[]
    int[] previousFloor = {-1};
    protected long waitUntil = -1;

    TreeSet<Floor> taskSet = new TreeSet<>(Comparator.comparingInt(f -> f.floorNum));
    public ElevButtons buttons;

    public Wagonik(ArrayList<Floor> floors, ArrayList<Summoner> summoners,ElevButtons buttons){
        this.shouldStop = false;
        this.floors = floors;
        this.buttons = buttons;
        this.summoners = summoners;
        this.currentFloor = floors.get(floors.size()-1);
        this.currentPassengers = new ArrayList<>();

        setDirection(Direction.IDLE);

        int labelWidth = 20;
        int panelWidth = 200;
        this.setLayout(new FlowLayout(FlowLayout.RIGHT,5,5));
        this.setBounds(100+labelWidth, floors.get(floors.size()-1).getY(), panelWidth, 70);
        this.setBorder(new LineBorder(Color.BLACK, 1));

        // w konstruktorze bo
        timer= new Timer(10,e -> {
            if (waitUntil > 0) {
                if (System.currentTimeMillis() < waitUntil) {
                    return;
                } else {
                    waitUntil = -1;
                    setShouldStop(false);
                    Floor next = getNextTask();
//                    buttons.setLogMessage("Request: "+next.floorNum + " pietro");
                    if (next != null) {
                        Direction newDir;
                        if(next.floorNum > currentFloor.floorNum) newDir = Direction.UP;
                        else if(next.floorNum == currentFloor.floorNum) newDir = Direction.IDLE; // jezeli bedac na 0 wezwie na 0
                        else newDir = Direction.DOWN;
                        setDirection(newDir);
                    } else {
                        setDirection(Direction.IDLE);
                        updateButtonsState();
                        return;
                    }
                }
            }

            summoners.forEach(Summoner::updateDirectionIndicator);
            Point p = this.getLocation();
            Floor newFloor = getCurrentFloor(p.y);
            this.currentFloor = newFloor;
            if(newFloor.floorNum != previousFloor[0] && currentFloor == getNextTask()) stop();

            if(shouldStop){
                setDirection(Direction.IDLE);
                return;
            }
            if(!SimulationManager.simulationRunning) return; // debug
            if (p.y >= 0) {
                int step = direction.getValue();
                int nextY = p.y + step;

                // Sprawdzamy, czy następna pozycja Y to piętro
                Optional<Floor> floorAtNextY = floors.stream()
                        .filter(f -> f.getY() == nextY)
                        .findFirst();

                this.setLocation(p.x, nextY);
                this.repaint();

                // Jeżeli piętro istnieje, aktualizujemy currentFloor
                floorAtNextY.ifPresent(f -> this.currentFloor = f);

                // Jeżeli to piętro jest kolejnym zadaniem, zatrzymujemy windę
                if (floorAtNextY.isPresent() && floorAtNextY.get() == getNextTask()) {
                    stop();
                }
            }

        });
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.green);
        g.fillRect(0,0,getWidth(),getHeight());
    }

    public Floor getCurrentFloor(int currentY){
        return floors.stream()
                .filter(f -> f.getLocation().getY() == currentY)
                .findFirst()
                .orElse(currentFloor);
    }

    public void setShouldStop(boolean value) {
        if (this.shouldStop != value) {
            this.shouldStop = value;
            summoners.forEach(Summoner::updateDirectionIndicator);
        }
    }

    protected void setDirection(Direction dir){
        direction = dir;
        updateButtonsState();
    }

    protected void enterPassengers(){
        final Floor aktualneFloor = this.currentFloor;
        if(!currentFloor.hasAwaitingPassengers()) return;

        Iterator<Passenger> it = aktualneFloor.passengers.iterator();
        // kazda animacja wejscia do windy niech trwa 0,5s zeby bylo plynnie

        Timer timer = new Timer(500,null);
        timer.addActionListener(evt -> {
            if (it.hasNext() && currentPassengers.size() < elevCapacity) {
                Passenger pas = it.next();
                pas.passengerPanel.remove(pas.icon);
                pas.passengerPanel.revalidate();
                pas.passengerPanel.repaint();

                this.add(pas.icon);
                buttons.setLogMessage(pas+"<br>entered the elevator");
                currentPassengers.add(pas);
                it.remove();

                if(!aktualneFloor.hasAwaitingPassengers()) aktualneFloor.summoner.setVisible(false);
                if(currentPassengers.size() == elevCapacity) buttons.setLogMessage("Maximum capacity: 5<br>Wyjdz ktoryms z pasażerów<br> żeby zwolnić miejsce");


                pas.addRemoveListener(this);
                this.revalidate();
                this.repaint();
            }
            else {
                ((Timer) evt.getSource()).stop();
            }
        });
        timer.start();
    }

    protected void updateButtonsState() {
        boolean isWaiting = (waitUntil > System.currentTimeMillis());
        boolean shouldBeActive = SimulationManager.simulationRunning
                && (direction == Direction.IDLE || isWaiting);
        buttons.setActive(shouldBeActive);
    }

    protected void goTo(Floor floor){
        if (!taskSet.contains(floor)) {
            taskSet.add(floor);
        }
        // jesli winda stoi to ruszmay ja
        if (direction == Direction.IDLE) {
            Floor next = getNextTask();
            if (next != null) {
                Direction newDir;
                if(next.floorNum > currentFloor.floorNum) newDir = Direction.UP;
                else if(next.floorNum == currentFloor.floorNum) newDir = Direction.IDLE; // jezeli bedac na 0 wezwie na 0
                else newDir = Direction.DOWN;
                setDirection(newDir);
                move();
                buttons.setLogMessage("Winda jedzie na "+next.floorNum + " pietro");
            }
        }

    }

    private Floor getNextTask() {
        if (taskSet.isEmpty()) return null;

        return taskSet.stream()
                .min(Comparator.comparingInt(f -> Math.abs(f.floorNum - currentFloor.floorNum)))
                .orElse(null);
    }

    protected void stop(){
        previousFloor[0] = currentFloor.floorNum;
        timer.stop();

        setShouldStop(false);
        setDirection(Direction.IDLE);
        summoners.forEach(Summoner::updateDirectionIndicator);

        this.enterPassengers();
        taskSet.remove(currentFloor);


        waitUntil = System.currentTimeMillis() + 5000;
        if(!timer.isRunning()) timer.start(); // wznawiamy glowny timer;
      }

    public void move(){
//        System.out.println(taskSet);

        if(!timer.isRunning()){
            timer.start();
        }
    }
}