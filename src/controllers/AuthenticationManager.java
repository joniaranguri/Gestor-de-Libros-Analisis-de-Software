package controllers;

import BL.UsuarioLogger;
import dtos.Usuario;
import ui.screens.Login;

import javax.swing.*;
import java.util.Arrays;

public final class AuthenticationManager {

    private static AuthenticationManager INSTANCE;
    private static Usuario usuarioLogueado;
    private  AuthenticationManager(){
    }
    public static AuthenticationManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuthenticationManager();
        }
        return INSTANCE;
    }

    public boolean performUserAuthentication(final String userText, final String passwordText) {
        final Usuario usuarioALoguear = new Usuario(userText, passwordText);
        final boolean usuarioLogueadoExitosamente = UsuarioLogger.login(usuarioALoguear);
        if (usuarioLogueadoExitosamente) {
            usuarioLogueado = usuarioALoguear;
        }
        return usuarioLogueadoExitosamente;
    }

    public UsuarioLogger.RegisterStatus performUserRegistration(final String userText, final String passwordText) {
        final Usuario usuarioARegistrar = new Usuario(userText,passwordText);
        return UsuarioLogger.registrar(usuarioARegistrar);
    }
}
