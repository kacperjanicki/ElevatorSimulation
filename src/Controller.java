import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Controller extends JPanel {
    private JButton up;
    private JButton down;
    private JPanel container;

    public Controller(){
        this.setPreferredSize(new Dimension(200,900));
        this.setLayout(new BorderLayout());
        this.setBorder(new LineBorder(Color.black,1));

        container = new JPanel();
        container.setPreferredSize(new Dimension(150,100));
        container.setLayout(new GridLayout(2,1,10,10));
        container.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1), new EmptyBorder(10, 10, 20, 10)));

        JPanel row1 = new JPanel();
        row1.setLayout(new GridLayout(1,2,20,0));
        up = new JButton("^");
        up.setEnabled(false);
        down = new JButton("⌄");
        down.setEnabled(false);

        row1.add(up);
        row1.add(down);

        JPanel row2 = new JPanel();
        row2.setLayout(new BorderLayout());
        JButton summon = new JButton();
        row2.add(summon,BorderLayout.CENTER);

        container.add(row1);
        container.add(row2);

        JPanel centeringPanel = new JPanel(new GridBagLayout());
        centeringPanel.add(container);

        this.add(centeringPanel,BorderLayout.SOUTH);
    }

    public void updateDirectionIndicator(){
        switch (Wagonik.direction) {
            case Direction.UP -> up.setBackground(Color.GREEN);
            case Direction.DOWN -> down.setBackground(Color.RED);
            case Direction.IDLE -> {
                // defaultowy background JButton
                up.setBackground(UIManager.getColor("Button.background"));
                down.setBackground(UIManager.getColor("Button.background"));
            }
        }
        container.revalidate();
        container.repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
//        g.drawRect(0,0,100,100);
    }
}
