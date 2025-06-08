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
    public myFrame(){

        super("Elevator simulator");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

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

        JPanel mainPanel = new JPanel(new BorderLayout(10,0));
        mainPanel.setPreferredSize(new Dimension(900,800));

        JPanel elevatorPanel = new JPanel(new BorderLayout());
        elevatorPanel.setPreferredSize(new Dimension(450,800));
        elevatorPanel.add(elevator,BorderLayout.CENTER);

        JPanel startPanel = new JPanel();
        startPanel.setPreferredSize(new Dimension(800,100));
        JButton start = new JButton("START");
        start.addActionListener(e -> manager.startSimulation());

        // for debugging
        JButton stop = new JButton("stop");
        stop.addActionListener(e -> manager.stopSimulation());

        mainPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1), new EmptyBorder(10, 10, 20, 10)));
        startPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1),new EmptyBorder(20, 10, 10, 10)));

        startPanel.add(start);
        startPanel.add(stop);
        leftPanel.add(elevButtons);
        rightPanel.add(controller);

        mainPanel.add(leftPanel,BorderLayout.WEST);
        mainPanel.add(rightPanel,BorderLayout.EAST);
        mainPanel.add(elevatorPanel,BorderLayout.CENTER);

        this.add(mainPanel,BorderLayout.CENTER);
        this.add(startPanel,BorderLayout.SOUTH);
        this.getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));

        this.pack();
        this.setSize(950,1000);
        this.setVisible(true);
        this.setResizable(false);
    }

}
