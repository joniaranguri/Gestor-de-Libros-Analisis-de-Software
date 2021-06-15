package ui.screens.base;

import javax.swing.*;

public abstract class BaseScreen extends JPanel {
    protected JLabel titleView = new JLabel(getTitle(), SwingConstants.CENTER);

    public void configureView() {
        setLayout(null);
        setLocationAndSize();
        addComponentsToContainer();
        addActionsEvents();
    }

    public void showMessage(final String message) {
        JOptionPane.showMessageDialog(this, message);

    }

    protected abstract String getTitle();
    protected abstract void setLocationAndSize();
    protected abstract void addComponentsToContainer();
    protected abstract void addActionsEvents();

}
