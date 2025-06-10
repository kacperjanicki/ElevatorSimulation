import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

class Floor extends JPanel{
    protected int floorNum;
    protected JPanel floorPanel;
    protected JLabel floorLabel;
    protected ArrayList<Passenger> passengers = new ArrayList<>();
    protected Summoner summoner;
    protected PassengerPanel passengerPanel;

    static final int labelWidth = 20;
    static final int passengerWidth = 100;
    static final int floorPanelWidth = 200;

    public Floor(int floorNumber) {
        this.floorNum = floorNumber;
        this.setLayout(null);
        this.setPreferredSize(new Dimension(300, 70));

        floorLabel = new JLabel(Integer.toString(floorNum));
        floorLabel.setBounds(0, 0, labelWidth, 70);
        floorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        floorLabel.setBorder(new LineBorder(Color.BLACK));
        floorLabel.setOpaque(true);
        floorLabel.setBackground(Color.LIGHT_GRAY);

        floorPanel = new JPanel();
        floorPanel.setBounds(labelWidth, 0, floorPanelWidth, 70);
        floorPanel.setBackground(Color.YELLOW);
        floorPanel.setBorder(new LineBorder(Color.BLACK));
        floorPanel.setOpaque(true);

        passengerPanel = new PassengerPanel();
        passengerPanel.setBounds(labelWidth + floorPanelWidth, 0, passengerWidth, 70);

        this.add(floorLabel);
        this.add(floorPanel);
        this.add(passengerPanel);
    }

    public void updatePassengers() {
        passengerPanel.removeAll();
        passengers.forEach(p->passengerPanel.add(p.icon));
//        System.out.println(Arrays.toString(passengerPanel.getComponents()));
        passengerPanel.revalidate();
        passengerPanel.repaint();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public String toString() {
        return "Pietro: " + floorNum;
    }


    boolean hasAwaitingPassengers(){
        return !passengers.isEmpty();
    }


}

class PassengerPanel extends JPanel{
    public PassengerPanel(){
        this.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
        this.setBackground(Color.RED);
        this.setBorder(new LineBorder(Color.BLACK));
    }
}

class Passenger{
    protected JLabel icon;
    private Floor currentFloor;
    protected PassengerPanel passengerPanel;

    public Passenger(Floor floor){
        this.currentFloor = floor;
        this.passengerPanel = floor.passengerPanel;
        icon = new JLabel("|");
        icon.setFont(new Font("Arial", Font.BOLD, 24));
        icon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    }

    void addRemoveListener(Wagonik container){
        icon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(SimulationManager.simulationRunning && Wagonik.direction == Direction.IDLE){
                    System.out.println(container.getParent());
                    removeOnClick(container);
                }
            }
        });
    }

    void removeOnClick(Wagonik wagon){
        wagon.remove(icon);
        wagon.revalidate();
        wagon.repaint();
        currentFloor.passengers.remove(this);

//        if (!currentFloor.hasAwaitingPassengers()) {
//            currentFloor.summoner.setVisible(false);
//        }
    }
}
