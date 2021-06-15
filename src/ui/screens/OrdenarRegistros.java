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
    private OrdenarRegistros() {
        configureView();
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    protected void setLocationAndSize() {

    }

    @Override
    protected void addComponentsToContainer() {

    }

    @Override
    protected void addActionsEvents() {

    }
}
