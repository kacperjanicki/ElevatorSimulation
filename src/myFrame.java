import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class myFrame extends JFrame {
    private Elevator elevator;
    private Controller controller;
    private ElevButtons elevButtons;
    protected final int floorCount=10;


    public myFrame(){
        super("Elevator simulator");
        System.out.println("tworze klase");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(0,20));

        elevator = new Elevator();
        controller = new Controller();
        elevButtons = new ElevButtons();
        ArrayList<Floor> floors = new ArrayList<>();

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(200,900));
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));

        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(200,900));
        rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));

        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(800,900));
        mainPanel.setLayout(new BorderLayout(100,0));

        JPanel startPanel = new JPanel();
        startPanel.setPreferredSize(new Dimension(800,100));
        JButton start = new JButton("START");

        mainPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1), new EmptyBorder(10, 10, 20, 10)));
        startPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1),new EmptyBorder(20, 10, 10, 10)));

        for(int i=floorCount; i>=0;i--){
            Floor floor = new Floor(i);
            floors.add(floor);
            elevator.add(floor);
        }
        startPanel.add(start);
        leftPanel.add(elevButtons);
        rightPanel.add(controller);


        mainPanel.add(leftPanel,BorderLayout.WEST);
        mainPanel.add(rightPanel,BorderLayout.EAST);
        mainPanel.add(elevator,BorderLayout.CENTER);


        this.add(mainPanel,BorderLayout.CENTER);
        this.add(startPanel,BorderLayout.SOUTH);
        this.getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));

        this.setSize(800,1000);
        this.setVisible(true);
        this.setResizable(false);
    }

}
