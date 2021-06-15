package ui.screens;

import controllers.IOController;
import dtos.Libro;
import ui.screens.base.BaseScreen;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static constants.Dimens.CENTER_WIDTH;
import static constants.Dimens.TITLE_FULL_WIDTH;

public class Actualizaciones extends BaseScreen implements ActionListener {
    private static final String ACTUALIZACIONES_TITLE = "Actualizaciones";
    private static final String INICIAR = "Iniciar";
    private static final String MODIFICAR = "Modificar";
    private static BaseScreen INSTANCE;
    JLabel tituloLabel = new JLabel("Titulo");
    JLabel autorLabel = new JLabel("Autor");
    JLabel editorialLabel = new JLabel("Editorial");
    JLabel ediciónLabel = new JLabel("Edición");
    JLabel añoDePublicaciónLabel = new JLabel("Año de publicación");
    JLabel isbnLabel = new JLabel("ISBN");

    JTextField tituloTextField = new JTextField();
    JTextField autorTextField = new JTextField();
    JTextField editorialTextField = new JTextField();
    JTextField edicionTextField = new JTextField();
    JTextField añoDePublicacionTextField = new JTextField();
    JTextField isbnTextField = new JTextField();

    JButton modifyButton = new JButton(INICIAR);
    private Libro bookToModify;

    public static BaseScreen getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Actualizaciones();
        }
        return INSTANCE;
    }

    private Actualizaciones() {
        configureView();
    }

    @Override
    public String getTitle() {
        return ACTUALIZACIONES_TITLE;
    }

    @Override
    public void setLocationAndSize() {
        //Setting location and Size of each components using setBounds() method.
        titleView.setBounds(0, 0, TITLE_FULL_WIDTH, 50);
        tituloLabel.setBounds(CENTER_WIDTH, 30, 200, 30);
        autorLabel.setBounds(CENTER_WIDTH, 90, 200, 30);
        editorialLabel.setBounds(CENTER_WIDTH, 150, 200, 30);
        ediciónLabel.setBounds(CENTER_WIDTH, 210, 200, 30);
        añoDePublicaciónLabel.setBounds(CENTER_WIDTH, 270, 200, 30);
        isbnLabel.setBounds(CENTER_WIDTH, 330, 200, 30);
        tituloTextField.setBounds(CENTER_WIDTH, 60, 200, 30);
        autorTextField.setBounds(CENTER_WIDTH, 120, 200, 30);
        editorialTextField.setBounds(CENTER_WIDTH, 180, 200, 30);
        edicionTextField.setBounds(CENTER_WIDTH, 240, 200, 30);
        añoDePublicacionTextField.setBounds(CENTER_WIDTH, 300, 200, 30);
        isbnTextField.setBounds(CENTER_WIDTH, 360, 200, 30);
        isbnTextField.setEnabled(false);
        modifyButton.setBounds(CENTER_WIDTH, 420, 100, 30);
    }

    @Override
    public void addComponentsToContainer() {
        add(titleView);
        add(tituloLabel);
        add(tituloTextField);
        add(autorLabel);
        add(autorTextField);
        add(editorialLabel);
        add(editorialTextField);
        add(ediciónLabel);
        add(edicionTextField);
        add(añoDePublicaciónLabel);
        add(añoDePublicacionTextField);
        add(isbnLabel);
        add(isbnTextField);
        add(modifyButton);
    }

    @Override
    protected void addActionsEvents() {
        modifyButton.addActionListener(this);
    }

    private void reiniciar() {
        tituloTextField.setText("");
        autorTextField.setText("");
        editorialTextField.setText("");
        edicionTextField.setText("");
        añoDePublicacionTextField.setText("");
        isbnTextField.setText("");
        modifyButton.setText(INICIAR);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == modifyButton) {
            if (modifyButton.getText().equals(INICIAR)) {
                final boolean hayRegistros = IOController.getInstance().hasRegisters();
                if (hayRegistros) {
                    getIsbnToModify();
                } else {
                    showMessage("No hay registros para modificar");
                }
            } else {
                confirmModify();
            }
        }
    }

    private void confirmModify() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Estas seguro que queres modificar a " + bookToModify.getTitulo(), "Confirmar borrado", dialogButton);
        if (dialogResult == 0) {
            if (!getBookToModify()) {
                return;
            }
            final boolean isbnModified = IOController.getInstance().modifyBook(bookToModify);
            if (isbnModified) {
                showMessage("Se ha modificado correctamente el libro " + bookToModify.getTitulo());
            }
        }
        reiniciar();
    }

    private boolean getBookToModify() {
        if (validateValues()) {
            bookToModify.setTitulo(tituloTextField.getText());
            bookToModify.setEditorial(editorialTextField.getText());
            bookToModify.setEdicion(Integer.parseInt(edicionTextField.getText()));
            bookToModify.setAnno_de_publicacion(Integer.parseInt(añoDePublicacionTextField.getText()));
            bookToModify.setAutor(autorTextField.getText());
            return true;

        }
        return false;
    }

    private boolean validateValues() {
        final Integer edicion = IOController.leerEntero(edicionTextField);
        final Integer añoPublicacion = IOController.leerEntero(añoDePublicacionTextField);

        final boolean hasErrors = edicion == null || añoPublicacion == null;

        if (hasErrors) {
            showMessage("Se detectaron los siguientes errores:\n" +
                    (edicion == null ? "* Edición debe ser un número\n" : "")
                    + (añoPublicacion == null ? "* Año de publicación debe ser un número\n" : ""));
            return false;
        }
        return true;
    }

    private void getIsbnToModify() {
        String isbnToModify = JOptionPane.showInputDialog("ISBN del libro a modificar:");

        while (isbnToModify != null && isbnToModify.isEmpty()) {
            isbnToModify = JOptionPane.showInputDialog("El ISBN del libro a modficar no valido.\nIntente nuevamente.");
        }
        if(isbnToModify == null){
            return;
        }
        final Libro bookToSeek = new Libro();
        bookToSeek.setISBN(isbnToModify);
        bookToModify = IOController.getInstance().getLibro(bookToSeek);
        if (bookToModify != null) {
            showLibro();
        } else {
            showMessage("No se encontró registro con el ISBN ingresado");
            reiniciar();
        }
    }

    private void showLibro() {
        tituloTextField.setText(bookToModify.getTitulo());
        autorTextField.setText(bookToModify.getAutor());
        editorialTextField.setText(bookToModify.getEditorial());
        edicionTextField.setText(String.valueOf(bookToModify.getEdicion()));
        añoDePublicacionTextField.setText(String.valueOf(bookToModify.getAnno_de_publicacion()));
        isbnTextField.setText(bookToModify.getISBN());
        modifyButton.setText(MODIFICAR);
    }
}
