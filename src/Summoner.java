import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Summoner extends JPanel {
    private JButton up;
    private JButton down;
    protected Floor floor;
    protected Elevator elev;
    protected JPanel summonerPanel;

    public Summoner(Floor floor,Elevator elev){
        this.floor = floor;
        this.elev = elev;
        this.setLayout(null);

        summonerPanel = new JPanel();
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
        summon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elev.wagonik.goTo(floor);
            }
        });
        row2.add(summon,BorderLayout.CENTER);

        summonerPanel.add(row1);
        summonerPanel.add(row2);

        this.add(summonerPanel);
    }

    public void updateDirectionIndicator(){
        switch (elev.wagonik.direction) {
            case Direction.UP -> {
                down.setBackground(UIManager.getColor("Button.background"));
                up.setBackground(Color.GREEN);
            }
            case Direction.DOWN -> {
                up.setBackground(UIManager.getColor("Button.background"));
                down.setBackground(Color.RED);
            }
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
    }
}
