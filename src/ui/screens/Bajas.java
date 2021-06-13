package ui.screens;

import ui.screens.base.BaseScreen;

public class Bajas extends BaseScreen {
    private static  BaseScreen INSTANCE;

    public static BaseScreen getInstance() {
        if(INSTANCE == null){
            INSTANCE = new Bajas();
        }
        return INSTANCE;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
