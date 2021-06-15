package ui.screens;

import ui.screens.base.BaseScreen;

public class ListarRegistros extends BaseScreen {
    private static BaseScreen INSTANCE;

    public static BaseScreen getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ListarRegistros();
        }
        return INSTANCE;
    }

    private ListarRegistros() {
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
