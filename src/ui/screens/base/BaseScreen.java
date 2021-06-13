package ui.screens.base;

import javax.swing.*;

public abstract class BaseScreen extends JPanel {
    protected JLabel titleView = new JLabel(getTitle(), SwingConstants.CENTER);

    public void showMessage(final String message) {
        JOptionPane.showMessageDialog(this, message);

    }

    protected abstract String getTitle();

}
