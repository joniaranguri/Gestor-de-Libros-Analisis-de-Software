package BL;

import com.sun.deploy.util.SystemUtils;
import dtos.Usuario;
import helpers.Encryptor;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UsuarioLogger {

    public enum RegisterStatus {
        SUCCESS,ERROR,DUPLICATED,INVALID
    }

    public static String FILE_PATH = getFilePath();

    private static String getFilePath() {
        final String OS = System.getProperty("os.name");
        return (OS != null && OS.startsWith("Windows") ? ".." : ".") + "/files/users.txt";
    }

    public static RegisterStatus registrar(final Usuario usuario) {

        String linea;
        String[] aux;
        Scanner reader = null;
        FileWriter file = null;
        PrintWriter pw;

        boolean isValidUser = validarUsuario(usuario);
        if (!isValidUser)
           return RegisterStatus.INVALID;
        try {
            reader = new Scanner(new File(FILE_PATH));
            while (reader.hasNextLine()) {
                linea = reader.nextLine();
                // Cada linea se guardara con el formato USERNAME|PASSWORD
                aux = linea.split("\\|");
                if (aux[0].compareTo(usuario.getUsername()) == 0)
                    return RegisterStatus.DUPLICATED;
            }

            file = new FileWriter(FILE_PATH, true);
            pw = new PrintWriter(file);
//			String aux2 = encryptor.Encrypt(usuario.getPassword());
            pw.println(
                    usuario.getUsername() + "|" + Encryptor.encrypt(usuario.getPassword(), Encryptor.getSecretKey()));

            return RegisterStatus.SUCCESS;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                // Cerramos todos los archivos
                if (reader != null)
                    reader.close();
                if (null != file)
                    file.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return RegisterStatus.ERROR;
    }

    public static boolean login(final Usuario usuario) {
        String linea;
        String[] aux;
        try {
            Scanner input = new Scanner(new File(FILE_PATH));
            while (input.hasNextLine()) {
                linea = input.nextLine();
                // Cada linea se guardara con el formato USERNAME|PASSWORD
                aux = linea.split("\\|");
                if (aux[0].compareTo(usuario.getUsername()) == 0
                        && Encryptor.decrypt(aux[1], Encryptor.getSecretKey()).compareTo(usuario.getPassword()) == 0) {
                    input.close();
                    return true;
                }
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private static boolean validarUsuario(Usuario usuario) {
        boolean matchFoundUsername = Pattern.matches("^[a-zA-Z0-9]*", usuario.getUsername());
        boolean matchFoundPassword = Pattern.matches("^[a-zA-Z0-9]*", usuario.getPassword());
        if (!matchFoundUsername || !matchFoundPassword)
            return false;
        boolean passwordHasCapitalLetters = false;
        boolean passwordHasNumbers = false;
        for (char c : usuario.getPassword().toCharArray()) {
            if (Character.isUpperCase(c))
                passwordHasCapitalLetters = true;
            if (Character.isDigit(c))
                passwordHasNumbers = true;
        }
        return passwordHasCapitalLetters && passwordHasNumbers;
    }

}
