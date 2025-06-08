import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class myFrame extends JFrame {
    private SimulationManager manager;
    private Elevator elevator;
    private Controller controller;
    private ElevButtons elevButtons;
    protected final int floorCount=10;

    public myFrame(){
        super("Elevator simulator");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(0,20));

        elevator = new Elevator();
        controller = new Controller();
        elevButtons = new ElevButtons();
        manager = new SimulationManager(elevator);

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(200,900));
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));

        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(200,900));
        rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));

        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(800,900));
        mainPanel.setLayout(new BorderLayout(100,0));

        JPanel elevatorPanel = new JPanel();
        elevatorPanel.setPreferredSize(new Dimension(400,900));
        elevatorPanel.setLayout(new GridLayout(1,2));
        elevatorPanel.add(elevator);

        JPanel startPanel = new JPanel();
        startPanel.setPreferredSize(new Dimension(800,100));
        JButton start = new JButton("START");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.startSimulation();
                start.setEnabled(false);
            }
        });


        mainPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1), new EmptyBorder(10, 10, 20, 10)));
        startPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1),new EmptyBorder(20, 10, 10, 10)));

        startPanel.add(start);
        leftPanel.add(elevButtons);
        rightPanel.add(controller);

        mainPanel.add(leftPanel,BorderLayout.WEST);
        mainPanel.add(rightPanel,BorderLayout.EAST);
        mainPanel.add(elevatorPanel,BorderLayout.CENTER);

        this.add(mainPanel,BorderLayout.CENTER);
        this.add(startPanel,BorderLayout.SOUTH);
        this.getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));

        this.setSize(800,1000);
        this.setVisible(true);
        this.setResizable(false);
    }

}
