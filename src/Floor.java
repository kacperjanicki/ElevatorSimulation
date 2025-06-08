import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

class Floor extends JPanel {
    protected int floorNum;
    protected JPanel floorPanel;
    protected JPanel passengerPanel;
    protected JLabel floorLabel;

    public Floor(int floorNumber) {
        this.floorNum = floorNumber;
        this.setLayout(null);
        this.setPreferredSize(new Dimension(300, 70));

        int labelWidth = 20;  // Zwiększyłem szerokość etykiety dla lepszej widoczności
        int passengerWidth = 100;  // Zwiększyłem szerokość panelu pasażera
        int floorPanelWidth = 200;

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

        passengerPanel = new JPanel();
        passengerPanel.setBounds(labelWidth + floorPanelWidth, 0, passengerWidth, 70);
        passengerPanel.setBackground(Color.RED);
        passengerPanel.setBorder(new LineBorder(Color.BLACK));
        passengerPanel.setOpaque(true);

        this.add(floorLabel);
        this.add(floorPanel);
        this.add(passengerPanel);
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public String toString() {
        return "Pietro: " + floorNum;
    }
}
