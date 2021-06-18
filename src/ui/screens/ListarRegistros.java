package ui.screens;

import controllers.IOController;
import dtos.Libro;
import ui.screens.base.BaseScreen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ListarRegistros extends BaseScreen {
    private static BaseScreen INSTANCE;

    public static BaseScreen getInstance() {
        //No queremos que cada vez que se clickee sobre la opcion
        //se cree una nueva instancia de la pantalla
        //Para evitar eso usamos el patron Singleton
        if (INSTANCE == null) {
            INSTANCE = new ListarRegistros();
        }
        return INSTANCE;
    }

    private ListarRegistros() {
        setLocationAndSize();

    }

    @Override
    public String getTitle() {
        return "La cantidad de Registros es : " + IOController.getInstance().getVectorLibros().size();
    }

    @Override
    protected void setLocationAndSize() {
        //Se configuran los anchos,altos, y posiciones de las vistas
        setLayout(new BorderLayout());
        addComponentsToContainer();
    }

    @Override
    protected void addComponentsToContainer() {
        //Se agregan las vistas al contenedor de la pantalla
        final ArrayList<String> labels = getLabels();
        final int sizeList = labels.size();
        final JList<String> listArea = new JList<>(labels.toArray(new String[sizeList]));
        listArea.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listArea.setFont(new Font("Serif", Font.PLAIN, 14));
        JScrollPane listScroller = new JScrollPane();
        listScroller.setViewportView(listArea);
        listArea.setLayoutOrientation(JList.VERTICAL);
        add(listScroller, BorderLayout.CENTER);
        add(titleView, BorderLayout.NORTH);
    }

    private ArrayList<String> getLabels() {
        final ArrayList<String> labels = new ArrayList<>();
        for (Libro libro : IOController.getInstance().getVectorLibros()) {
            labels.add("<html>" +
                    "<br/><b>ISBN               : </b>" + libro.getISBN() +
                    "<br/><b>Titulo             : </b>" + "\"" + libro.getTitulo() + "\"" +
                    "<br/><b>Autor              : </b>" + libro.getAutor() +
                    "<br/><b>Editorial          : </b>" + libro.getEditorial() +
                    "<br/><b>Edicion            : </b>" + libro.getEdicion() +
                    "<br/><b>Anio de publicacion: </b>" + libro.getAnno_de_publicacion() + "<br/><br/");
        }
        return labels;
    }

    @Override
    protected void addActionsEvents() {
        //Nada para hacer
    }

    @Override
    public void reiniciar() {
        removeAll();
        repaint();
        addComponentsToContainer();
        titleView.setText(getTitle());
        revalidate();
    }
}
