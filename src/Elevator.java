import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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

    public Wagonik wagonik;
    protected Controller controller;

    public Elevator(Controller controller){
        this.setLayout(null);
        this.setPreferredSize(new Dimension(400, 800));
        this.controller = controller;

        int y = 0;
        int floorHeight = 70;
        for (int i = floorCount; i >= 0; i--) {
            Floor floor = new Floor(i);
            floor.setBounds(100, y, 400, floorHeight);
            this.add(floor);
            floors.add(floor);
            y += floorHeight;
//          setvisible true jesli chcemy odkryc jak wygladaja ukryte floor
            floor.floorPanel.setVisible(false);

        }
        wagonik = new Wagonik(floors,controller);
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
    private int currentFloor;
    static protected Direction direction;
    protected Controller elevController;

    public Wagonik(ArrayList<Floor> floors,Controller elevController){
        this.shouldStop = false;
        this.floors = floors;
        this.elevController = elevController;
        this.currentFloor = 0;
        direction = Direction.UP;

        int labelWidth = 20;
        int panelWidth = 200;
        this.setBounds(100+labelWidth, floors.get(floors.size()-1).getY(), panelWidth, 70);
        this.setBorder(new LineBorder(Color.BLACK, 1));
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.green);
        g.fillRect(0,0,getWidth(),getHeight());
    }

    public int getCurrentFloor(int currentY){
        for(Floor f: floors){
            if(f.getLocation().y == currentY){
                return f.floorNum;
            }
        }
        return currentFloor;
    }

    public void setShouldStop(boolean value) {
        if (this.shouldStop != value) {
            this.shouldStop = value;
            elevController.updateDirectionIndicator();
        }
    }


    public void move(){
        // w ActionListenerze(lambdzie) nie mozna zmieniac wartosci inta,
        // wiec dajemy int[]
        int[] previousFloor = {-1};

        Timer timer = new Timer(10, null);
        timer.setInitialDelay(0);
        timer.addActionListener(e -> {
            elevController.updateDirectionIndicator();
            Point p = this.getLocation();
            int newFloor = getCurrentFloor(p.y);

            if(shouldStop){
                ((Timer) e.getSource()).stop();
                return;
            }
//          z kazdym kolejnym pietrem zatrzymujemy winde na 1s, debugging
            if(newFloor != previousFloor[0]){
                previousFloor[0] = newFloor;
                timer.stop();
                setShouldStop(true);
                direction = Direction.IDLE;

                new Timer(1000, evt -> {
                    timer.start();
                }) {{
                    setRepeats(false);
                    start();
                    setShouldStop(false);
                    direction=Direction.UP;
                }};
                return;
            }

            if (p.y > 0) {
                this.currentFloor = getCurrentFloor(p.y);
//                System.out.println("current floor: "+currentFloor);
                this.setLocation(p.x, p.y + direction.getValue());
                this.repaint();
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }
}
