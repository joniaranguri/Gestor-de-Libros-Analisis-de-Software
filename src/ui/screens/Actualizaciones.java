package ui.screens;

import ui.screens.base.BaseScreen;

public class Actualizaciones extends BaseScreen {
    private static final String ACTUALIZACIONES_TITLE = "Actualizaciones";
    private static  BaseScreen INSTANCE;

    public static BaseScreen getInstance() {
        if(INSTANCE == null){
            INSTANCE = new Actualizaciones();
        }
        return INSTANCE;
    }

    @Override
    public String getTitle() {
        return ACTUALIZACIONES_TITLE;
    }
}
