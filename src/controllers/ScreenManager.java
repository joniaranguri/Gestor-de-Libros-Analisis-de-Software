package controllers;

import constants.ScreenName;
import ui.screens.*;
import ui.screens.base.BaseScreen;

import javax.swing.*;
import java.awt.*;

public class ScreenManager {
    private final JPanel contentPane;

    public ScreenManager(final JPanel contentPane) {
        this.contentPane = contentPane;
        contentPane.setLayout(new BorderLayout());
    }

    public void goToScreen(final ScreenName screenName) {
        goToScreenInstance(requireLogin(screenName) ? ScreenName.LOGIN : screenName);
    }

    private void goToScreenInstance(final ScreenName screenName) {
        BaseScreen screenToNavigate;
        switch (screenName) {
            case ALTAS:
                screenToNavigate = Altas.getInstance();
                break;
            case BAJAS:
                screenToNavigate = Bajas.getInstance();
                break;
            case CONSULTAS:
                screenToNavigate = Consultas.getInstance();

                break;
            case LISTAR_REGISTROS:
                screenToNavigate = ListarRegistros.getInstance();

                break;
            case ORDENAR_REGISTROS:
                screenToNavigate = OrdenarRegistros.getInstance();

                break;
            case ACTUALIZACIONES:
                screenToNavigate = Actualizaciones.getInstance();

                break;
            default:
                screenToNavigate = Login.getInstance();
        }
        contentPane.removeAll();
        contentPane.repaint();
        contentPane.add(screenToNavigate, BorderLayout.CENTER);
        contentPane.revalidate();

    }

    private boolean requireLogin(final ScreenName screenName) {
        return ScreenName.ALTAS.equals(screenName) || ScreenName.BAJAS.equals(screenName);
    }
}
