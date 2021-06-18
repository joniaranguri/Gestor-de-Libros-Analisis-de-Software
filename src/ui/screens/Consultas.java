package ui.screens;

import controllers.IOController;
import dtos.Libro;
import ui.screens.base.BaseScreen;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static constants.Dimens.CENTER_WIDTH;
import static constants.Dimens.TITLE_FULL_WIDTH;

public class Consultas extends BaseScreen implements ActionListener {
    private static final String NUEVA_CONSULTA = "Nueva consulta";
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

    JButton consultaButton = new JButton(NUEVA_CONSULTA);
    private Libro bookToShow;

    public static BaseScreen getInstance() {
        //No queremos que cada vez que se clickee sobre la opcion
        //se cree una nueva instancia de la pantalla
        //Para evitar eso usamos el patron Singleton
        if (INSTANCE == null) {
            INSTANCE = new Consultas();
        }
        return INSTANCE;
    }

    private Consultas() {
        configureView();
    }

    @Override
    public String getTitle() {
        return "Consultas";
    }

    @Override
    protected void setLocationAndSize() {
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
        isbnTextField.setEnabled(false);
        consultaButton.setBounds(CENTER_WIDTH, 420, 200, 30);
        setTextFieldsDisabled();
    }

    private void setTextFieldsDisabled() {
        isbnTextField.setEnabled(false);
        autorTextField.setEnabled(false);
        editorialTextField.setEnabled(false);
        edicionTextField.setEnabled(false);
        anioDePublicacionTextField.setEnabled(false);
        tituloTextField.setEnabled(false);
    }

    @Override
    protected void addComponentsToContainer() {
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
        add(consultaButton);
    }

    @Override
    protected void addActionsEvents() {
        consultaButton.addActionListener(this);
    }

    @Override
    public void reiniciar() {
        tituloTextField.setText("");
        autorTextField.setText("");
        editorialTextField.setText("");
        edicionTextField.setText("");
        anioDePublicacionTextField.setText("");
        isbnTextField.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == consultaButton) {
            final boolean hayRegistros = IOController.getInstance().hasRegisters();
            if (hayRegistros) {
                getIsbnToShow();
            } else {
                showMessage("No hay registros para consultar");
            }
        }
    }

    private void getIsbnToShow() {
        String isbnToShow = JOptionPane.showInputDialog("ISBN del libro a consultar:");

        while (isbnToShow != null && isbnToShow.isEmpty()) {
            isbnToShow = JOptionPane.showInputDialog("El ISBN del libro a consultar no valido.\nIntente nuevamente.");
        }
        if (isbnToShow == null) {
            return;
        }
        final Libro bookToSeek = new Libro();
        bookToSeek.setISBN(isbnToShow);
        bookToShow = IOController.getInstance().getLibro(bookToSeek);
        if (bookToShow != null) {
            showLibro();
        } else {
            showMessage("No se encontró registro con el ISBN ingresado");
            reiniciar();
        }
    }

    private void showLibro() {
        tituloTextField.setText(bookToShow.getTitulo());
        autorTextField.setText(bookToShow.getAutor());
        editorialTextField.setText(bookToShow.getEditorial());
        edicionTextField.setText(String.valueOf(bookToShow.getEdicion()));
        anioDePublicacionTextField.setText(String.valueOf(bookToShow.getAnno_de_publicacion()));
        isbnTextField.setText(bookToShow.getISBN());
    }
}


