import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;



public class Elevator extends JPanel {
    public static final int floorCount=10;

    public ArrayList<Floor> floors = new ArrayList<>();

    public JPanel wagonik;

    public Elevator(){
        this.setPreferredSize(new Dimension(300, 1000));
        this.setLayout(null);

        int y = 0;
        int floorHeight = 70;
        for (int i = floorCount; i >= 0; i--) {
            Floor floor = new Floor(i);
            floor.setBounds(0, y, 400, floorHeight);
            this.add(floor, JLayeredPane.DEFAULT_LAYER);
            floors.add(floor);
            y += floorHeight;
//          setvisible true jesli chcemy odkryc jak wygladaja ukryte floor
            floor.floorPanel.setVisible(false);

        }
        wagonik = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.setColor(Color.green);
                g.fillRect(0,0,400,70);
            }
        };
        Floor last = floors.getLast();

        wagonik.setBounds(30,last.getY(), 400, floorHeight);
        wagonik.setBorder(new LineBorder(Color.black, 1));

        this.add(wagonik);
        this.setComponentZOrder(wagonik,0);
    }

    public void start() {
        System.out.println("Elevator moving");

        Timer timer = new Timer(10, null);
        timer.setInitialDelay(0);
        timer.addActionListener(e -> {
            Point p = wagonik.getLocation();
            if (p.y > 0) {
                wagonik.setLocation(p.x, p.y - 1);
                this.repaint();
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

    }
}
