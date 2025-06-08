import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

class Floor extends JPanel {
    protected int floorNum;
    protected JPanel floorPanel;
    protected JPanel wagonikContainer;

    public Floor(int floorNumber){
        floorNum = floorNumber;
        this.setPreferredSize(new Dimension(400, 70));
        this.setLayout(null);

        JLabel floorLabel = new JLabel(Integer.toString(floorNum));
        floorLabel.setBounds(0, 0, 400, 70);
        floorLabel.setHorizontalAlignment(SwingConstants.LEFT);

        wagonikContainer = new JPanel(null);
        wagonikContainer.setOpaque(false);
        wagonikContainer.setBounds(30, 0,400,70);

        floorPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.setColor(Color.yellow);
                g.fillRect(0,0,400,70);
            }
        };
        floorPanel.setBounds(0,0,400,70);
        floorPanel.setBorder(new LineBorder(Color.black,1));
        wagonikContainer.add(floorPanel);

        this.add(floorLabel);
        this.add(wagonikContainer);

    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public String toString(){
        return "Pietro: "+floorNum;
    }
}