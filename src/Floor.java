import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

class Floor extends JPanel {
    private int floorNum;

    public Floor(int floorNumber){
        floorNum = floorNumber;
        this.setPreferredSize(new Dimension(300, 70));
        this.setBorder(new LineBorder(Color.black,1));

        JLabel num = new JLabel(Integer.toString(floorNum));
        this.add(num);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.green);
        g.fillRect(0,0,300,70);
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }
}