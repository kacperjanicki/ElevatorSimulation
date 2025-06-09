import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

enum Direction {
    UP(-1),
    DOWN(1),
    IDLE(0);

    private final int value;
    Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}



public class Elevator extends JPanel {
    public static final int floorCount=10;
    public ArrayList<Floor> floors = new ArrayList<>();
    public ArrayList<Summoner> floorSummoners = new ArrayList<>();

    public Wagonik wagonik;

    public Elevator(JPanel rightPanel){
        this.setLayout(null);
        this.setPreferredSize(new Dimension(400, 800));
        int y = 0;
        int floorHeight = 70;
        for (int i = floorCount; i >= 0; i--) {
            Floor floor = new Floor(i);
            floor.setBounds(100, y, 400, floorHeight);
            this.add(floor);
            floors.add(floor);

            Summoner floorSummoner = new Summoner(floor);
            floorSummoner.setBounds(25, y, 150, 75);
//          Summoner dodajemy dla każdego piętra, jest on defaultowo niewidoczny, dopiero w SimulationManager
//          zdecydujemy żeby pokazać tylko te na których są pasażerowie
//          Summoner na piętrze bez pasażerów na początku i tak się przyda, bo na to piętro mogą w przyszłości wysiaść inni
            floorSummoner.setVisible(false);
            rightPanel.add(floorSummoner);
            floorSummoners.add(floorSummoner);
            floor.summoner = floorSummoner;

            y += floorHeight;
//          setvisible true jesli chcemy odkryc jak wygladaja ukryte floor
            floor.floorPanel.setVisible(false);
        }
        wagonik = new Wagonik(floors,floorSummoners);
        this.add(wagonik);
        this.setComponentZOrder(wagonik,0);
    }

    public void start() {
        System.out.println("Elevator moving");
        wagonik.move();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
    }
}
class Wagonik extends JPanel{
    protected boolean shouldStop;
    protected ArrayList<Floor> floors;
    protected ArrayList<Summoner> summoners;
    protected Floor currentFloor;
    static protected Direction direction;


    public Wagonik(ArrayList<Floor> floors, ArrayList<Summoner> summoners){
        this.shouldStop = false;
        this.floors = floors;
        this.summoners = summoners;
        this.currentFloor = floors.get(floors.size()-1);
        setDirection(Direction.UP);
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
        direction = dir;
    }

    protected void enterPassengers(){
        Iterator<Passenger> it = currentFloor.passengers.iterator();
        Timer timer = new Timer(500,null);
        timer.addActionListener(evt -> {
            if (it.hasNext()) {
                Passenger pas = it.next();
                this.add(pas.icon);
                it.remove();
                currentFloor.updatePassengers();
            } else {
                ((Timer) evt.getSource()).stop();
            }
        });
        timer.start();
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
                ((Timer) e.getSource()).stop();
                return;
            }
//          z kazdym kolejnym pietrem zatrzymujemy winde na 1s, debugging
            if(newFloor.floorNum != previousFloor[0]){
                previousFloor[0] = newFloor.floorNum;
                timer.stop();
//              pasazerowie wsiadaja
//                System.out.println("pietro: "+currentFloor.floorNum + "y: "+this.getLocation().y);
                System.out.println(currentFloor.passengers);
                if(currentFloor.hasAwaitingPassengers()){
                    this.enterPassengers();
                }
                setShouldStop(true);
                setDirection(Direction.IDLE);
                this.currentFloor = getCurrentFloor(p.y);


                new Timer(3000, evt -> {
                    timer.start();
                }) {{
                    setRepeats(false);
                    start();
                    setShouldStop(false);
                    setDirection(Direction.UP);
                }};
                return;
            }
//            System.out.println("pietro: "+currentFloor.floorNum + "y: "+this.getLocation().y);
            if (p.y >= 0) {
                if(currentFloor.floorNum == 10) setDirection(Direction.DOWN);
                this.currentFloor = getCurrentFloor(p.y);
                this.setLocation(p.x, p.y + direction.getValue());
                this.repaint();
            }
        });
        timer.start();
    }
}
