package ui.screens;

import controllers.IOController;
import dtos.Libro;
import ui.screens.base.BaseScreen;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static constants.Dimens.CENTER_WIDTH;
import static constants.Dimens.TITLE_FULL_WIDTH;

public class Bajas extends BaseScreen implements ActionListener {
    public static final String INICIAR = "Iniciar";
    private static final String ELIMINAR = "Eliminar";
    private static BaseScreen INSTANCE;
    JLabel tituloLabel = new JLabel("Titulo :");
    JLabel autorLabel = new JLabel("Autor :");
    JLabel editorialLabel = new JLabel("Editorial :");
    JLabel ediciónLabel = new JLabel("Edición :");
    JLabel añoDePublicaciónLabel = new JLabel("Año de publicación :");
    JLabel isbnLabel = new JLabel("ISBN :");

    JLabel tituloValueLabel = new JLabel();
    JLabel autorValueLabel = new JLabel();
    JLabel editorialValueLabel = new JLabel();
    JLabel edicionValueLabel = new JLabel();
    JLabel añoDePublicacionValueLabel = new JLabel();
    JLabel isbnValueLabel = new JLabel();

    JButton deleteButton = new JButton(INICIAR);
    private Libro bookToDelete;

    public static BaseScreen getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Bajas();
        }
        return INSTANCE;
    }

    private Bajas() {
        configureView();
        setLabelsStyle();
    }

    private void setLabelsStyle() {
        final Font f = tituloLabel.getFont();
        tituloLabel.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        autorLabel.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        ediciónLabel.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        editorialLabel.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        añoDePublicaciónLabel.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        isbnLabel.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
    }

    private void getIsbnToDelete() {
        String isbnToDelete = JOptionPane.showInputDialog("ISBN del libro a eliminar:");

        while (isbnToDelete == null || isbnToDelete.isEmpty()) {
            isbnToDelete = JOptionPane.showInputDialog("El ISBN del libro a eliminar no valido.\nIntente nuevamente.");
        }
        final Libro bookToSeek = new Libro();
        bookToSeek.setISBN(isbnToDelete);
        bookToDelete = IOController.getInstance().getLibro(bookToSeek);
        if (bookToDelete != null) {
            showLibro();
        } else {
            showMessage("No se encontró registro con el ISBN ingresado");
            reiniciar();
        }
    }

    private void reiniciar() {
        tituloValueLabel.setText("");
        autorValueLabel.setText("");
        edicionValueLabel.setText("");
        editorialValueLabel.setText("");
        añoDePublicacionValueLabel.setText("");
        isbnValueLabel.setText("");
        deleteButton.setText("Iniciar");
    }

    private void showLibro() {
        tituloValueLabel.setText(bookToDelete.getTitulo());
        autorValueLabel.setText(bookToDelete.getAutor());
        edicionValueLabel.setText(String.valueOf(bookToDelete.getEdicion()));
        editorialValueLabel.setText(bookToDelete.getEditorial());
        añoDePublicacionValueLabel.setText(String.valueOf(bookToDelete.getAnno_de_publicacion()));
        isbnValueLabel.setText(bookToDelete.getISBN());
        deleteButton.setText(ELIMINAR);
    }

    private void confirmDelete() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Estas seguro que queres borrar a " + bookToDelete.getTitulo(), "Confirmar borrado", dialogButton);
        if (dialogResult == 0) {
            final boolean isbnDeleted = IOController.getInstance().deleteBook(bookToDelete);
            if (isbnDeleted) {
                showMessage("Se ha eliminado correctamente el libro " + bookToDelete.getTitulo());
            } else {
                showMessage("No se encontró registro con el ISBN ingresado");
            }
            reiniciar();
        }
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    protected void setLocationAndSize() {
        //Setting location and Size of each components using setBounds() method.
        titleView.setBounds(0, 0, TITLE_FULL_WIDTH, 50);
        tituloLabel.setBounds(CENTER_WIDTH, 30, 200, 30);
        autorLabel.setBounds(CENTER_WIDTH, 90, 200, 30);
        editorialLabel.setBounds(CENTER_WIDTH, 150, 200, 30);
        ediciónLabel.setBounds(CENTER_WIDTH, 210, 200, 30);
        añoDePublicaciónLabel.setBounds(CENTER_WIDTH, 270, 200, 30);
        isbnLabel.setBounds(CENTER_WIDTH, 330, 200, 30);
        tituloValueLabel.setBounds(CENTER_WIDTH, 60, 200, 30);
        autorValueLabel.setBounds(CENTER_WIDTH, 120, 200, 30);
        editorialValueLabel.setBounds(CENTER_WIDTH, 180, 200, 30);
        edicionValueLabel.setBounds(CENTER_WIDTH, 240, 200, 30);
        añoDePublicacionValueLabel.setBounds(CENTER_WIDTH, 300, 200, 30);
        isbnValueLabel.setBounds(CENTER_WIDTH, 360, 200, 30);
        deleteButton.setBounds(CENTER_WIDTH, 420, 100, 30);
    }

    @Override
    protected void addComponentsToContainer() {
        add(titleView);
        add(tituloLabel);
        add(tituloValueLabel);
        add(autorLabel);
        add(autorValueLabel);
        add(editorialLabel);
        add(editorialValueLabel);
        add(ediciónLabel);
        add(edicionValueLabel);
        add(añoDePublicaciónLabel);
        add(añoDePublicacionValueLabel);
        add(isbnLabel);
        add(isbnValueLabel);
        add(deleteButton);
    }

    @Override
    protected void addActionsEvents() {
        deleteButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteButton) {
            if (deleteButton.getText().equals(INICIAR)) {
                final boolean hayRegistros = IOController.getInstance().hasRegisters();
                if (hayRegistros) {
                    getIsbnToDelete();
                } else {
                    showMessage("No hay registros para modificar");
                }
            } else {
                confirmDelete();
            }
        }
    }
}
