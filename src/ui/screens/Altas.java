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
    JLabel edicionLabel = new JLabel("Edici\u00f3n");
    JLabel anioDePublicacionLabel = new JLabel("A\u00f1o de publicaci\u00f3n");
    JLabel isbnLabel = new JLabel("ISBN");

    JTextField tituloTextField = new JTextField();
    JTextField autorTextField = new JTextField();
    JTextField editorialTextField = new JTextField();
    JTextField edicionTextField = new JTextField();
    JTextField anioDePublicacionTextField = new JTextField();
    JTextField isbnTextField = new JTextField();

    JButton altaButton = new JButton("Dar de alta");

    public static BaseScreen getInstance() {
        //No queremos que cada vez que se clickee sobre la opcion
        //se cree una nueva instancia de la pantalla
        //Para evitar eso usamos el patron Singleton
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
        //Se configuran los anchos,altos, y posiciones de las vistas
        titleView.setBounds(0, 0, TITLE_FULL_WIDTH, 50);
        tituloLabel.setBounds(CENTER_WIDTH, 30, 200, 30);
        autorLabel.setBounds(CENTER_WIDTH, 90, 200, 30);
        editorialLabel.setBounds(CENTER_WIDTH, 150, 200, 30);
        edicionLabel.setBounds(CENTER_WIDTH, 210, 200, 30);
        anioDePublicacionLabel.setBounds(CENTER_WIDTH, 270, 200, 30);
        isbnLabel.setBounds(CENTER_WIDTH, 330, 200, 30);
        tituloTextField.setBounds(CENTER_WIDTH, 60, 200, 30);
        autorTextField.setBounds(CENTER_WIDTH, 120, 200, 30);
        editorialTextField.setBounds(CENTER_WIDTH, 180, 200, 30);
        edicionTextField.setBounds(CENTER_WIDTH, 240, 200, 30);
        anioDePublicacionTextField.setBounds(CENTER_WIDTH, 300, 200, 30);
        isbnTextField.setBounds(CENTER_WIDTH, 360, 200, 30);
        altaButton.setBounds(CENTER_WIDTH, 420, 200, 30);
    }

    @Override
    public void addComponentsToContainer() {
        //Se agregan las vistas al contenedor de la pantalla
        add(titleView);
        add(tituloLabel);
        add(tituloTextField);
        add(autorLabel);
        add(autorTextField);
        add(editorialLabel);
        add(editorialTextField);
        add(edicionLabel);
        add(edicionTextField);
        add(anioDePublicacionLabel);
        add(anioDePublicacionTextField);
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
        //Tenemos que validar la edicion
        //el anio de publicacion
        //y el ISBN
        final Integer edicion = IOController.leerEntero(edicionTextField);
        final Integer anioPublicacion = IOController.leerEntero(anioDePublicacionTextField);
        final String isbn = isbnTextField.getText();

        final boolean hasErrors = edicion == null || anioPublicacion == null || (isbn == null || isbn.length() != 13);
        //Si hay errores hay que mostrarselos al usuario
        if (hasErrors) {
            showMessage("Se detectaron los siguientes errores:\n" +
                    (edicion == null ? "* Edici\u00f3n debe ser un n\u00famero\n" : "")
                    + (anioPublicacion == null ? "* A\u00f1o de publicaci\u00f3n debe ser un n\u00famero\n" : "")
                    + (isbn == null || isbn.isEmpty() ? "* El ISBN no puede quedar vacio\n" : "")
                    + (isbn != null && isbn.length() != 13 ? "* El ISBN debe estar compuesto por 13 car\u00e1cteres\n" : ""));
            return;
        }
        //Si no hubo errores, tengo que guardar el libro ingresado
        final boolean libroGuardado = guardarLibro();
        if (libroGuardado) {
            showMessage("El libro se ha guardado exitosamente");
        } else {
            showMessage("El libro que intenta guardar ya est\u00e1 dado de alta");
        }
        //Al final del proeceso, reinicio para permitir mas Altas
        reiniciar();

    }

    @Override
    public void reiniciar() {
        //Seteo en blanco todos los textFields
        tituloTextField.setText("");
        autorTextField.setText("");
        editorialTextField.setText("");
        edicionTextField.setText("");
        anioDePublicacionTextField.setText("");
        isbnTextField.setText("");
    }

    private boolean guardarLibro() {
        //Obtengo los valores de los textFields
        final String isbn = isbnTextField.getText();
        final String title = tituloTextField.getText();
        final String author = autorTextField.getText();
        final String editorial = editorialTextField.getText();
        final String edition = edicionTextField.getText();
        final String yearOfPublish = anioDePublicacionTextField.getText();
        //Genero un libro con los valores ingresados
        final Libro libro = new Libro();
        libro.setISBN(isbn);
        libro.setTitulo(title);
        libro.setAutor(author);
        libro.setEditorial(editorial);
        libro.setEdicion(Integer.parseInt(edition));
        libro.setAnno_de_publicacion(Integer.parseInt(yearOfPublish));
        //Le pido al controlador de Entrada salida que guarde el libro
        return IOController.getInstance().saveNewBook(libro);
    }

}
