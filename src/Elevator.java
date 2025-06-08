import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Elevator extends JPanel {

    public Elevator(){
        this.setPreferredSize(new Dimension(300,1000));
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.red);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
    }
}
