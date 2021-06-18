package controllers;

import dtos.Libro;

import javax.swing.*;
import java.io.*;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class IOController {
    private static IOController INSTANCE;
    public static String ruta = "libros.tsv";
    private final Vector<Libro> vectorLibros = new Vector<>();

    private IOController() {
        leerRegistros();
    }

    private void leerRegistros() {
        String[] campos;
        try {
            Scanner entrada = new Scanner(new FileReader(ruta));
            while (entrada.hasNextLine()) {
                campos = entrada.nextLine().split("\t");
                final Libro libro = new Libro();
                libro.setISBN(campos[0]);
                libro.setTitulo(campos[1]);
                libro.setAutor(campos[2]);
                libro.setEditorial(campos[3]);
                libro.setEdicion(Integer.parseInt(campos[4]));
                libro.setAnno_de_publicacion(Integer.parseInt(campos[5]));
                vectorLibros.add(libro);
            }
            entrada.close();
        } catch (FileNotFoundException e) {
        }
    }

    public static IOController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IOController();
        }
        return INSTANCE;
    }

    private void imprimirEnArchivo(final Libro libro, final PrintStream archivo) {
        archivo.print(libro.getISBN() + "\t");
        archivo.print(libro.getTitulo() + "\t");
        archivo.print(libro.getAutor() + "\t");
        archivo.print(libro.getEditorial() + "\t");
        archivo.print(libro.getEdicion() + "\t");
        archivo.print(libro.getAnno_de_publicacion() + "\n");
    }

    public static Integer leerEntero(final JTextField vista) {
        final String texto = vista.getText();
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void saveAll() {
        try {
            PrintStream salida = new PrintStream(ruta);
            for (Libro vectorLibro : vectorLibros) {
                imprimirEnArchivo(vectorLibro, salida);
            }
            salida.close();
        } catch (FileNotFoundException e) {
        }
    }

    public boolean saveNewBook(final Libro libro) {
        final boolean condicionLibro = noEsLibroDuplicado(libro);
        if (condicionLibro) {
            vectorLibros.add(libro);
        }
        return condicionLibro;
    }

    private boolean noEsLibroDuplicado(final Libro libro) {
        final Libro dato = getLibro(libro);
        return dato == null;
    }

    public Libro getLibro(Libro libro) {
        final int i = vectorLibros.indexOf(libro);
        return i < 0 ? null : vectorLibros.get(i);
    }

    public boolean deleteBook(Libro bookToDelete) {
        return vectorLibros.remove(bookToDelete);
    }

    public boolean hasRegisters() {
        return !vectorLibros.isEmpty();
    }

    public boolean modifyBook(Libro bookToModify) {
        final Libro currentBook = getLibro(bookToModify);
        if (currentBook == null) {
            return false;
        }
        currentBook.setAutor(bookToModify.getAutor());
        currentBook.setEdicion(bookToModify.getEdicion());
        currentBook.setEditorial(bookToModify.getEditorial());
        currentBook.setAnno_de_publicacion(bookToModify.getAnno_de_publicacion());
        currentBook.setTitulo(bookToModify.getTitulo());
        return true;

    }

    public Vector<Libro> getVectorLibros() {
        return this.vectorLibros;
    }

    public void ordenarLibros() {
        Collections.sort(vectorLibros);
    }
}
