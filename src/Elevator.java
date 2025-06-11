import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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

    protected Wagonik wagonik;
    protected ElevButtons buttons;

    public Elevator(JPanel rightPanel,ElevButtons buttons){
        this.setLayout(null);
        this.buttons = buttons;
        this.setPreferredSize(new Dimension(400, 800));

        int y = 0;
        int floorHeight = 70;
        for (int i = floorCount; i >= 0; i--) {
            Floor floor = new Floor(i);
            floor.setBounds(100, y, 400, floorHeight);
            this.add(floor);
            floors.add(floor);

            Summoner floorSummoner = new Summoner(floor,this);
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
        this.wagonik = new Wagonik(floors,floorSummoners,buttons);
        SimulationManager.elevatorButtons = buttons;
        buttons.wagonik = this.wagonik;
        buttons.initializeButtons();
        this.add(wagonik);
        this.setComponentZOrder(wagonik,0);
    }

    public void start() {
        wagonik.move();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
    }
}

