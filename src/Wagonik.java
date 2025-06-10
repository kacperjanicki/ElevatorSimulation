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
    int[] previousFloor = {-1};
    private long waitUntil = -1;

    TreeSet<Floor> taskSet = new TreeSet<>(Comparator.comparingInt(f -> f.floorNum));
    public ElevButtons buttons;
    private Integer targetY;

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
                    if (next != null) {
                        Direction newDir = next.floorNum > currentFloor.floorNum ? Direction.UP : Direction.DOWN;
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

//            System.out.println("current floor: "+currentFloor.floorNum+" passCount "+currentFloor.passengers.size());
            if(newFloor.floorNum != previousFloor[0] && currentFloor == getNextTask()) stop();

            if(shouldStop){
                setDirection(Direction.IDLE);
                return;
            }
//            System.out.println("pietro: "+currentFloor.floorNum + "y: "+this.getLocation().y);
            if(!SimulationManager.simulationRunning) return;

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
        System.out.println("curr "+aktualneFloor.floorNum);


        Iterator<Passenger> it = aktualneFloor.passengers.iterator();
        Timer timer = new Timer(500,null);
        timer.addActionListener(evt -> {
            if (it.hasNext() && currentPassengers.size() < elevCapacity) {
                Passenger pas = it.next();
                pas.passengerPanel.remove(pas.icon);
                pas.passengerPanel.revalidate();
                pas.passengerPanel.repaint();

                this.add(pas.icon);
                currentPassengers.add(pas);
                it.remove();

                pas.addRemoveListener(this);

                this.revalidate();
                this.repaint();
            }else if(currentPassengers.size() == elevCapacity){
//                System.out.println("capacity reached");
                return;
            }

            else {
                ((Timer) evt.getSource()).stop();
            }
        });
        timer.start();
    }

    protected void updateButtonsState() {
        boolean shouldBeActive = SimulationManager.simulationRunning
                && direction == Direction.IDLE;
        buttons.setActive(shouldBeActive);
    }


    protected void goTo(Floor floor){
        if (!taskSet.contains(floor)) {
            taskSet.add(floor);
        }
        targetY = floor.getLocation().y;
        // jesli winda stoi to ruszmay ja
        if (direction == Direction.IDLE) {
            Floor next = getNextTask();
            if (next != null) {
                Direction newDir = next.floorNum > currentFloor.floorNum ? Direction.UP : Direction.DOWN;
                setDirection(newDir);
                move();
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
        System.out.println("current passengers: "+currentPassengers.size());
        if(currentFloor.hasAwaitingPassengers()){
            this.enterPassengers();
        }
//      log.addMessage("Elevator capacity reached. Maximum passengers at the time is 5")

        taskSet.remove(currentFloor);

        setShouldStop(false);
        setDirection(Direction.IDLE);
        updateButtonsState();

        waitUntil = System.currentTimeMillis() + 3000;
        if(!timer.isRunning()) timer.start(); // wznawiamy glowny timer;

      }

    public void move(){
        System.out.println(taskSet);
        // w ActionListenerze(lambdzie) nie mozna zmieniac wartosci inta,
        // wiec dajemy int[]
        if(!timer.isRunning()){
            timer.start();
        }
    }
}