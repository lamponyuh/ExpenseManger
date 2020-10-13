import sun.security.pkcs11.Secmod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrimaryFrame {

//    public static void main(String[] args) {
//        new PrimaryFrame(new User(1,"asd"));
//    }

    private final User user;
    private final JFrame frame;
    private final JPanel mainPanel;

    public PrimaryFrame(User user) {

        this.user = user;

        frame = new JFrame("Expense Manager");
        mainPanel = new JPanel();
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);

        frame.setJMenuBar(getMenuBar());

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    private JMenuBar getMenuBar() {

        JMenuBar mainMenuBar;
        JMenu fileMenu, pagesSubMenu;
        JMenuItem expensePage, wagePage, exit;

        mainMenuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        pagesSubMenu = new JMenu("Pages");
        exit = new JMenuItem("Exit");



        expensePage = new JMenuItem("Expense page");
        wagePage = new JMenuItem("Wage page");

        mainMenuBar.add(fileMenu);
        fileMenu.add(pagesSubMenu);
        fileMenu.add(exit);
        pagesSubMenu.add(expensePage);
        pagesSubMenu.add(wagePage);

        expensePage.addActionListener(new ExpensePageListener());

        wagePage.addActionListener(new WagePageListener());

        exit.addActionListener(new ExitListener());


        return mainMenuBar;
    }

    private void setNewPanel(Page page) {
        mainPanel.removeAll();
        mainPanel.add(page.getGuiPanel());
        mainPanel.validate();
        mainPanel.repaint();
    }

    class ExpensePageListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Я открыл траты");
        }
    }

    class WagePageListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setNewPanel(new WagePage());
        }
    }

    class ExitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
        }
    }
}
