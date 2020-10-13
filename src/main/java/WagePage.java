import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WagePage implements Page{

    JPanel mainPanel;

    @Override
    public JPanel getGuiPanel() {
        mainPanel = new JPanel();

        return mainPanel;
    }
}
