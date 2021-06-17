package ui.screens;

import controllers.IOController;
import ui.screens.base.BaseScreen;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static constants.Dimens.CENTER_WIDTH;
import static constants.Dimens.TITLE_FULL_WIDTH;

public class OrdenarRegistros extends BaseScreen implements ActionListener {
    private static final String ORDENAR = "Ordenar";
    private static BaseScreen INSTANCE;
    JButton orderButton = new JButton(ORDENAR);

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
        return "Ordenar los registros por ISBN";
    }

    @Override
    protected void setLocationAndSize() {
        titleView.setBounds(0, 0, TITLE_FULL_WIDTH, 50);
        orderButton.setBounds(CENTER_WIDTH, 200, 200, 30);
    }

    @Override
    protected void addComponentsToContainer() {
        add(titleView);
        add(orderButton);
    }

    @Override
    protected void addActionsEvents() {
        orderButton.addActionListener(this);
    }

    @Override
    public void reiniciar() {
        // Nada para hacer
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == orderButton) {
            final boolean hayRegistros = IOController.getInstance().hasRegisters();
            if (hayRegistros) {
                confirmOrder();
            } else {
                showMessage("No hay registros para ordenar");
            }
        }
    }

    private void confirmOrder() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Estas seguro que queres ordenar los registros? ", "Confirmar ordenamiento", dialogButton);
        if (dialogResult == 0) {
            IOController.getInstance().ordenarLibros();
            showMessage("Se ordenaron los registros");
        }
    }
}
