package ui.screens;

import controllers.IOController;
import dtos.Libro;
import ui.screens.base.BaseScreen;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static constants.Dimens.*;
import static constants.Dimens.CENTER_WIDTH;

public class Altas extends BaseScreen implements ActionListener {
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

    JButton altaButton = new JButton("Dar de alta");

    public static BaseScreen getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Altas();
        }
        return INSTANCE;
    }

    private Altas() {
        configureView();
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
        altaButton.setBounds(CENTER_WIDTH, 420, 100, 30);
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
        add(altaButton);
    }

    @Override
    protected void addActionsEvents() {
        altaButton.addActionListener(this);
    }

    @Override
    public String getTitle() {
        return "Dar de alta";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == altaButton) {
            performAltaLibro();
        }
    }

    private void performAltaLibro() {
        final Integer edicion = IOController.leerEntero(edicionTextField);
        final Integer añoPublicacion = IOController.leerEntero(añoDePublicacionTextField);
        final String isbn = isbnTextField.getText();

        final boolean hasErrors = edicion == null || añoPublicacion == null || (isbn == null || isbn.isEmpty());

        if (hasErrors) {
            showMessage("Se detectaron los siguientes errores:\n" +
                    (edicion == null ? "* Edición debe ser un número\n" : "")
                    + (añoPublicacion == null ? "* Año de publicación debe ser un número\n" : "")
                    + (isbn == null || isbn.isEmpty() ? "* El ISBN no puede quedar vacío\n" : ""));
            return;
        }
        final boolean libroGuardado = guardarLibro();
        if (libroGuardado) {
            showMessage("El libro se ha guardado exitosamente");
        } else {
            showMessage("El libro que intenta guardar ya está dado de alta");
        }
        reiniciar();

    }

    private void reiniciar() {
        tituloTextField.setText("");
        autorTextField.setText("");
        editorialTextField.setText("");
        edicionTextField.setText("");
        añoDePublicacionTextField.setText("");
        isbnTextField.setText("");
    }

    private boolean guardarLibro() {
        final String isbn = isbnTextField.getText();
        final String title = tituloTextField.getText();
        final String author = autorTextField.getText();
        final String editorial = editorialTextField.getText();
        final String edition = edicionTextField.getText();
        final String yearOfPublish = añoDePublicacionTextField.getText();

        final Libro libro = new Libro();
        libro.setISBN(isbn);
        libro.setTitulo(title);
        libro.setAutor(author);
        libro.setEditorial(editorial);
        libro.setEdicion(Integer.parseInt(edition));
        libro.setAnno_de_publicacion(Integer.parseInt(yearOfPublish));
        return IOController.getInstance().saveNewBook(libro);
    }

}
