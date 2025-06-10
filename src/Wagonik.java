import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

class Wagonik extends JPanel {
    protected boolean shouldStop;
    protected ArrayList<Floor> floors;
    protected ArrayList<Summoner> summoners;
    protected ArrayList<Passenger> currentPassengers;
    protected Floor currentFloor;
    protected Direction direction;

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
        this.direction = dir;
        updateButtonsState();
    }

    protected void enterPassengers(){
        Iterator<Passenger> it = currentFloor.passengers.iterator();
        Timer timer = new Timer(500,null);
        timer.addActionListener(evt -> {
            if (it.hasNext()) {
                Passenger pas = it.next();
                this.add(pas.icon);
                currentPassengers.add(pas);
                System.out.println("current passengers: "+currentPassengers.size());
                it.remove();
                currentFloor.updatePassengers();
            } else {
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

    public void move(){
        // w ActionListenerze(lambdzie) nie mozna zmieniac wartosci inta,
        // wiec dajemy int[]
        int[] previousFloor = {-1};

        Timer timer = new Timer(10, null);
        timer.setInitialDelay(0);
        timer.addActionListener(e -> {
            summoners.forEach(Summoner::updateDirectionIndicator);
            Point p = this.getLocation();
            Floor newFloor = getCurrentFloor(p.y);
            this.currentFloor = newFloor;

            if(shouldStop){
                setDirection(Direction.IDLE);
                ((Timer) e.getSource()).stop();
                return;
            }
//          z kazdym kolejnym pietrem zatrzymujemy winde na 1s, debugging
            if(newFloor.floorNum != previousFloor[0]){
                setDirection(Direction.IDLE);
                previousFloor[0] = newFloor.floorNum;
                timer.stop();
//              pasazerowie wsiadaja
//                System.out.println("pietro: "+currentFloor.floorNum + "y: "+this.getLocation().y);
//                System.out.println(currentFloor.passengers);
                if(currentFloor.hasAwaitingPassengers()){
                    this.enterPassengers();
                }
                this.currentFloor = getCurrentFloor(p.y);

                new Timer(3000, evt -> {
                    // po 3s winda rusza do gory
                    setShouldStop(false);
                    setDirection(Direction.UP);
                    timer.start();
                }) {{
                    // przez 3s stoi w miejscu, buttons po lewo active
                    setRepeats(false);
                    start();
                    setDirection(Direction.IDLE);
                    summoners.forEach(s -> s.updateDirectionIndicator());
                    updateButtonsState();
                }};
                return;
            }
//            System.out.println("pietro: "+currentFloor.floorNum + "y: "+this.getLocation().y);
            if (p.y >= 0 && SimulationManager.simulationRunning) {
                if(currentFloor.floorNum == 10) setDirection(Direction.DOWN);
                this.currentFloor = getCurrentFloor(p.y);
                this.setLocation(p.x, p.y + direction.getValue());
                this.repaint();
            }
        });
        timer.start();
    }
}