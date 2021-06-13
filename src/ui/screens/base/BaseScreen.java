package ui.screens.base;

import javax.swing.*;

public abstract class BaseScreen extends JPanel {

    public BaseScreen() {
        add(new JLabel(getTitle()));
    }

    public abstract String getTitle();
}
