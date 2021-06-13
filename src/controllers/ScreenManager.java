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
        BaseScreen screenToNavigate = null;
        switch (screenName) {
            case ALTAS:
                screenToNavigate = new Altas();
                break;
            case BAJAS:
                screenToNavigate = new Bajas();
                break;
            case CONSULTAS:
                screenToNavigate = new Consultas();

                break;
            case LISTAR_REGISTROS:
                screenToNavigate = new ListarRegistros();

                break;
            case ORDENAR_REGISTROS:
                screenToNavigate = new OrdenarRegistros();

                break;
            case ACTUALIZACIONES:
                screenToNavigate = new Actualizaciones();

                break;
            default:
                screenToNavigate = new Login();
        }
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.add(screenToNavigate, BorderLayout.CENTER);
        contentPane.revalidate();

    }

    private boolean requireLogin(final ScreenName screenName) {
        return ScreenName.ALTAS.equals(screenName) || ScreenName.BAJAS.equals(screenName);
    }
}
