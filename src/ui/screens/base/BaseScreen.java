package ui.screens.base;

import javax.swing.*;

public abstract class BaseScreen extends JPanel {

    public BaseScreen() {
        add(new JLabel(getTitle()));
    }

    public void showMessage(final String message) {
        JOptionPane.showMessageDialog(this, message);

    }

    public abstract String getTitle();
}
