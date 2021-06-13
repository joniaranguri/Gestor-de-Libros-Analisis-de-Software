package ui.screens;

import ui.screens.base.BaseScreen;

public class Altas extends BaseScreen {
    private static  BaseScreen INSTANCE;

    public static BaseScreen getInstance() {
        if(INSTANCE == null){
           INSTANCE = new Altas();
        }
        return INSTANCE;
    }

    @Override
    public String getTitle() {
        return "Altas";
    }
}
