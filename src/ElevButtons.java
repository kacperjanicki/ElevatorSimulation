import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.TreeSet;

public class ElevButtons extends JPanel {
    protected boolean active;
    private JPanel container;
    protected Wagonik wagonik;

    private String logMessage;
    private JLabel logLabel;
    private JPanel logPanel;
    private JPanel queuePanel;

    public ElevButtons(){
        this.setBorder(new LineBorder(Color.BLACK, 1));
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(300, 900));
        this.active = false;

        container = new JPanel();
        container.setLayout(new GridLayout(4,3,10,40));

        JPanel centeringPanel = new JPanel(new GridBagLayout());
        centeringPanel.add(container);

        logPanel = new JPanel(new BorderLayout());
        logPanel.setPreferredSize(new Dimension(300,200));

        JPanel messagePanel = new JPanel(new BorderLayout());
        logLabel = new JLabel("Welcome to Elevator Simulation");
        logLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messagePanel.add(logLabel,BorderLayout.CENTER);

        queuePanel = new JPanel(new FlowLayout());
        queuePanel.setPreferredSize(new Dimension(300,100));

        logPanel.add(messagePanel,BorderLayout.NORTH);
        logPanel.add(queuePanel,BorderLayout.SOUTH);

        this.add(centeringPanel, BorderLayout.CENTER);
        this.add(logPanel, BorderLayout.NORTH);
    }

    public void setLogMessage(String msg){
        this.logMessage = msg;
        logLabel.setText("<html>"+logMessage+"</html>");
        logPanel.revalidate();
        logPanel.repaint();
    }
    public void updateQueuePanel(TreeSet<Floor> taskSet){
        System.out.println(taskSet);
        queuePanel.removeAll();
        for(Floor floor: taskSet){

            JLabel label = new JLabel("Floor: "+floor.floorNum);
            queuePanel.add(label);
            queuePanel.revalidate();
            queuePanel.repaint();
            }
        }

    public void initializeButtons(){
        for(int i=10; i>=0; i--){
            int floorNumber = i;
            JButton button = new JButton(Integer.toString(i));
            Floor floor = wagonik.floors.stream()
                    .filter(f-> f.floorNum == floorNumber)
                    .findFirst()
                    .orElseThrow();

            button.addActionListener(e-> {
//                wagonik.waitUntil = System.currentTimeMillis() + 3000;
                wagonik.goTo(floor);
            });
            container.add(button);
        }
    }

    public void setActive(boolean value){
        this.active = value;
        for(Component comp : container.getComponents()){
            if (!(comp instanceof JButton)) continue;
            JButton but = (JButton) comp;
            but.setEnabled(active && SimulationManager.simulationRunning);
        }
        container.revalidate();
        container.repaint();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }
}
