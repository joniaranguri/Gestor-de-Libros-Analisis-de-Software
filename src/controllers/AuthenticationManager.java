package controllers;

import BL.UsuarioLogger;
import dtos.Usuario;

public final class AuthenticationManager {

    private static AuthenticationManager INSTANCE;
    private static Usuario usuarioLogueado;
    private  AuthenticationManager(){
    }
    public static AuthenticationManager getInstance() {
        //No queremos que cada vez que se clickee sobre la opcion
        //se cree una nueva instancia de la clase
        //Para evitar eso usamos el patron Singleton
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

    public boolean isUserLogged() {
        return usuarioLogueado != null;
    }
}
