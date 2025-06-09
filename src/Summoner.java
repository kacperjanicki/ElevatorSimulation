import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Summoner extends JPanel {
    private JButton up;
    private JButton down;
    protected Floor floor;
    protected JPanel summonerPanel;

    public Summoner(Floor floor){
        this.floor = floor;
        this.setLayout(null);
//        this.setBorder(new LineBorder(Color.black,1));

        summonerPanel = new JPanel();
//        summonerPanel.setBackground(Color.BLUE);
        summonerPanel.setBounds(0,0,150,70);
        summonerPanel.setLayout(new GridLayout(2,1,10,10));
        summonerPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1), new EmptyBorder(10, 10, 20, 10)));

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
//        JLabel num = new JLabel("Pietro: "+floor.floorNum);
//        row2.add(num,BorderLayout.EAST);

        summonerPanel.add(row1);
        summonerPanel.add(row2);

        this.add(summonerPanel);
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
        summonerPanel.revalidate();
        summonerPanel.repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
//        g.drawRect(0,0,100,100);
    }
}
