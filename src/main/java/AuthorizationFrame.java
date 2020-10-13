import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class AuthorizationFrame {

    private UserService userService;

    private final JFrame frame;
    private final JPanel loginPanel,
            passwordPanel,
            errorPanel,
            buttonsPanel;
    private final JLabel loginLabel,
            passwordLabel,
            errorLabel;
    private final JTextField loginInput;
    private final JPasswordField passwordInput;
    private final JButton enterButton,
            createUserButton;

    public AuthorizationFrame() {
        frame = new JFrame("Authorization");
        userService = UserService.getInstance();

        loginPanel = new JPanel();
        passwordPanel = new JPanel();
        buttonsPanel = new JPanel();
        errorPanel = new JPanel();

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(loginPanel);
        frame.add(passwordPanel);
        frame.add(buttonsPanel);
        frame.add(errorPanel);

        loginLabel = new JLabel("Login:");
        passwordLabel = new JLabel("Password:");

        loginInput = new JTextField(10);
        passwordInput = new JPasswordField(10);


        enterButton = new JButton("Enter");
        enterButton.addActionListener(new EnterListener());
        createUserButton = new JButton("Create user");
        createUserButton.addActionListener(new CreateUserListener());

        errorLabel = new JLabel();

        loginPanel.add(loginLabel);
        loginPanel.add(loginInput);

        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordInput);

        buttonsPanel.add(enterButton);
        buttonsPanel.add(createUserButton);

        errorPanel.add(errorLabel);

        frame.setSize(400,200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private class EnterListener implements ActionListener{
        public void actionPerformed(ActionEvent event) {
            String login = loginInput.getText();
            String passsword = passwordInput.getText();
            User user;

            if (!login.equals("") && !passsword.equals("")) {
                try {
                    user = userService.login(login, passsword);
                    if (user != null) {
                        closeAuthAndOpenPF(user);
                    } else {
                        errorLabel.setText("Invalid login or password");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    errorLabel.setText("DB error");
                }
            } else {
                errorLabel.setText("Set login and password");
            }
        }
    }

    private class CreateUserListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String login = loginInput.getText();
            String passsword = passwordInput.getText();
            User user;

            if (!login.equals("") && !passsword.equals("")) {
                try {
                    user = userService.createUser(login, passsword);
                    if (user != null) {
                        closeAuthAndOpenPF(user);
                    } else {
                        errorLabel.setText("DB returned 0");
                    }
                } catch (SQLIntegrityConstraintViolationException ex) {
                    ex.printStackTrace();
                    errorLabel.setText("User already exist");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    errorLabel.setText("DB error");
                }
            } else {
                errorLabel.setText("Set login and password");
            }
        }
    }

    private void closeAuthAndOpenPF(User user) {
        frame.dispose();
        new PrimaryFrame(user);
    }
}

