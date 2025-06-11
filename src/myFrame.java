import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class myFrame extends JFrame {
    private SimulationManager manager;
    private Elevator elevator;
    private ElevButtons elevButtons;
    protected JPanel rightPanel;

    public myFrame(){
        super("Elevator simulation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(200,900));
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));

        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(200,900));
        rightPanel.setLayout(null);

        elevButtons = new ElevButtons();
        elevator = new Elevator(rightPanel,elevButtons);

        JPanel mainPanel = new JPanel(new BorderLayout(10,0));
        mainPanel.setPreferredSize(new Dimension(900,800));

        JPanel elevatorPanel = new JPanel(new BorderLayout());
        elevatorPanel.setPreferredSize(new Dimension(450,800));
        elevatorPanel.add(elevator,BorderLayout.CENTER);

        JPanel startPanel = new JPanel();
        startPanel.setPreferredSize(new Dimension(800,100));
        JButton start = new JButton("START");
        start.addActionListener(e -> {
            manager.startSimulation();
            start.setEnabled(false);
            elevator.floorSummoners.forEach(Summoner::updateDirectionIndicator);
        });
        manager = new SimulationManager(elevator,start);

        mainPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1), new EmptyBorder(10, 10, 20, 10)));
        startPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1),new EmptyBorder(20, 10, 10, 10)));

        startPanel.add(start);
        leftPanel.add(elevButtons);

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
//      debug - wyswietlaj na drugim monitorze
//        this.setLocation(-1000,200);
    }

}
