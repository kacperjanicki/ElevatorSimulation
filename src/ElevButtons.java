import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ElevButtons extends JPanel {

    public ElevButtons(){
        this.setBorder(new LineBorder(Color.BLACK, 1));
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(300, 900));

        JPanel container = new JPanel();
        container.setLayout(new GridLayout(4,3,10,40));

        for(int i=10; i>=0; i--){
            JButton button = new JButton(Integer.toString(i));
            container.add(button);
        }

        JPanel centeringPanel = new JPanel(new GridBagLayout());
        centeringPanel.add(container);

        this.add(centeringPanel, BorderLayout.CENTER);

    }


    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }
}
