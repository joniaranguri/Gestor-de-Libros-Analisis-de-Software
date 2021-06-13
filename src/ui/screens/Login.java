package ui.screens;

import ui.screens.base.BaseScreen;

public class Login extends BaseScreen {
    private static  BaseScreen INSTANCE;
    private static final String LOGIN_TITLE = "Bienvenido al Gestor de Libros \n Inicie sesión para continuar";

    public static BaseScreen getInstance() {
        if(INSTANCE == null){
            INSTANCE = new Login();
        }
        return INSTANCE;
    }

    @Override
    public String getTitle() {
        return LOGIN_TITLE;
    }
}
