package ui.screens;

import ui.screens.base.BaseScreen;

public class OrdenarRegistros extends BaseScreen {
    private static BaseScreen INSTANCE;

    public static BaseScreen getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrdenarRegistros();
        }
        return INSTANCE;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
