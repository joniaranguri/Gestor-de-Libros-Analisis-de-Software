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
    JLabel edicionLabel = new JLabel("Edici\u00f3n :");
    JLabel anioDePublicacionLabel = new JLabel("A\u00f1o de publicaci\u00f3n :");
    JLabel isbnLabel = new JLabel("ISBN :");

    JLabel tituloValueLabel = new JLabel();
    JLabel autorValueLabel = new JLabel();
    JLabel editorialValueLabel = new JLabel();
    JLabel edicionValueLabel = new JLabel();
    JLabel anioDePublicacionValueLabel = new JLabel();
    JLabel isbnValueLabel = new JLabel();

    JButton deleteButton = new JButton(INICIAR);
    private Libro bookToDelete;

    public static BaseScreen getInstance() {
        //No queremos que cada vez que se clickee sobre la opcion
        //se cree una nueva instancia de la pantalla
        //Para evitar eso usamos el patron Singleton
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
        //Seteo el font de los labels en bold para poder resaltarlos de los valores
        final Font f = tituloLabel.getFont();
        tituloLabel.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        autorLabel.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        edicionLabel.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        editorialLabel.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        anioDePublicacionLabel.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        isbnLabel.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
    }

    private void getIsbnToDelete() {
        //Necesitamos obtener el ISBN al cual vamos a eliminar
        String isbnToDelete = JOptionPane.showInputDialog("ISBN del libro a eliminar:");
        //El isbn no puede tener un largo distinto de 13
        //Sino se cumple la restriccion, volvemos a pedir el ISBN
        while (isbnToDelete != null && isbnToDelete.length()!= 13) {
            isbnToDelete = JOptionPane.showInputDialog("El ISBN del libro a eliminar no valido.\nIntente nuevamente.");
        }
        //Si se selecciono cancelar, no hay nada mas que hacer
        if (isbnToDelete == null) {
            return;
        }
        // Si llegue aca, tengo que eliminar el libro
        final Libro bookToSeek = new Libro();
        bookToSeek.setISBN(isbnToDelete);
        //Le pido al controlador de entrada salida que elimine el libro
        bookToDelete = IOController.getInstance().getLibro(bookToSeek);
        //Informamos el resultado
        if (bookToDelete != null) {
            showLibro();
        } else {
            showMessage("No se encontr\u00f3 registro con el ISBN ingresado");
            //Reiniciamos para permitir nuevas bajas
            reiniciar();
        }
    }

    @Override
    public void reiniciar() {
        //Seteamos en blanco todos los labels
        tituloValueLabel.setText("");
        autorValueLabel.setText("");
        edicionValueLabel.setText("");
        editorialValueLabel.setText("");
        anioDePublicacionValueLabel.setText("");
        isbnValueLabel.setText("");
        deleteButton.setText("Iniciar");
    }

    private void showLibro() {
        //Mostramos los valores del libro a eliminar
        tituloValueLabel.setText(bookToDelete.getTitulo());
        autorValueLabel.setText(bookToDelete.getAutor());
        edicionValueLabel.setText(String.valueOf(bookToDelete.getEdicion()));
        editorialValueLabel.setText(bookToDelete.getEditorial());
        anioDePublicacionValueLabel.setText(String.valueOf(bookToDelete.getAnno_de_publicacion()));
        isbnValueLabel.setText(bookToDelete.getISBN());
        deleteButton.setText(ELIMINAR);
    }

    private void confirmDelete() {
        //Vamos a pedir confirmacion para borrar un registro
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Estas seguro que queres borrar a " + bookToDelete.getTitulo() + "?", "Confirmar borrado", dialogButton);
        if (dialogResult == 0) {
            final boolean isbnDeleted = IOController.getInstance().deleteBook(bookToDelete);
            if (isbnDeleted) {
                showMessage("Se ha eliminado correctamente el libro " + bookToDelete.getTitulo());
            } else {
                showMessage("No se encontr\u00f3 registro con el ISBN ingresado");
            }
        }
        //Al terminar el proceso necesitamos reiniciar la pantalla
        reiniciar();
    }

    @Override
    public String getTitle() {
        return null;
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
        tituloValueLabel.setBounds(CENTER_WIDTH, 60, 200, 30);
        autorValueLabel.setBounds(CENTER_WIDTH, 120, 200, 30);
        editorialValueLabel.setBounds(CENTER_WIDTH, 180, 200, 30);
        edicionValueLabel.setBounds(CENTER_WIDTH, 240, 200, 30);
        anioDePublicacionValueLabel.setBounds(CENTER_WIDTH, 300, 200, 30);
        isbnValueLabel.setBounds(CENTER_WIDTH, 360, 200, 30);
        deleteButton.setBounds(CENTER_WIDTH, 420, 200, 30);
    }

    @Override
    protected void addComponentsToContainer() {
        //Se agregan las vistas al contenedor de la pantalla
        add(titleView);
        add(tituloLabel);
        add(tituloValueLabel);
        add(autorLabel);
        add(autorValueLabel);
        add(editorialLabel);
        add(editorialValueLabel);
        add(edicionLabel);
        add(edicionValueLabel);
        add(anioDePublicacionLabel);
        add(anioDePublicacionValueLabel);
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
                //Si no hay registros no se puede eliminar nada
                final boolean hayRegistros = IOController.getInstance().hasRegisters();
                if (hayRegistros) {
                    getIsbnToDelete();
                } else {
                    showMessage("No hay registros para eliminar");
                }
            } else {
                confirmDelete();
            }
        }
    }
}
