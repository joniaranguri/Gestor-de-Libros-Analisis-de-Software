package BL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import dtos.Usuario;
import helpers.RSAAsymetricCrypto;

public class UsuarioLogger {

	public static String FILE_PATH = "../files/users.txt";

	public UsuarioLogger() {

	}

	public static int Registrar(Usuario usuario) {

		String linea;
		String[] aux;
		Scanner reader = null;
		FileWriter file = null;
		PrintWriter pw = null;
		try {
			reader = new Scanner(new File(FILE_PATH));
			while (reader.hasNextLine()) {
				linea = reader.nextLine();
				// Cada linea se guardara con el formato USERNAME|PASSWORD
				aux = linea.split("\\|");
				if (aux[0].compareTo(usuario.getUsername()) == 0)
					return 1;
			}

			file = new FileWriter(FILE_PATH, true);
			pw = new PrintWriter(file);
			pw.println(usuario.getUsername() + "|" + usuario.getPassword());

			return 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 1;
		} finally {
			try {
				//Cerramos todos los archivos
				if (reader != null)
					reader.close();
				if (null != file)
					file.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public static int Login(Usuario usuario, RSAAsymetricCrypto encryptor) throws NoSuchAlgorithmException {
		String linea;
		String[] aux;
		try {
			Scanner input = new Scanner(new File(FILE_PATH));
			while (input.hasNextLine()) {
				linea = input.nextLine();
				// Cada linea se guardara con el formato USERNAME|PASSWORD
				aux = linea.split("\\|");
				if (aux[0].compareTo(usuario.getUsername()) == 0 && aux[1].compareTo(usuario.getPassword()) == 0) {
					// && encryptor.Decrypt(aux[1]).compareTo(usuario.getPassword()) == 0) {
					input.close();
					return 0;
				}

			}
			input.close();
			return 1;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 1;
		}
	}

}
