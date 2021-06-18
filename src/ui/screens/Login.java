package ui.screens;

import BL.UsuarioLogger;
import constants.ScreenName;
import controllers.AuthenticationManager;
import controllers.ScreenManager;
import ui.screens.base.BaseScreen;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static constants.Dimens.*;

public class Login extends BaseScreen implements ActionListener {
    private static BaseScreen INSTANCE;
    private static final String LOGIN_TITLE = "Bienvenido al Gestor de Libros \n" +
            "Inicie sesi\u00f3n para continuar";
    JLabel userLabel = new JLabel("Usuario");
    JLabel passwordLabel = new JLabel("Contrase\u00f1a");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("Acceder");
    JButton registerButton = new JButton("Registrarse");
    JCheckBox showPassword = new JCheckBox("Mostrar Contrase\u00f1a");

    private Login() {
        configureView();
    }

    public void addComponentsToContainer() {
        //Se agregan las vistas al contenedor de la pantalla
        add(titleView);
        add(userLabel);
        add(passwordLabel);
        add(userTextField);
        add(passwordField);
        add(showPassword);
        add(loginButton);
        add(registerButton);
    }

    @Override
    protected void addActionsEvents() {
        loginButton.addActionListener(this);
        showPassword.addActionListener(this);
        registerButton.addActionListener(this);
    }

    @Override
    public void reiniciar() {
        //No hacer nada
    }

    public void setLocationAndSize() {
        //Se configuran los anchos,altos, y posiciones de las vistas
        titleView.setBounds(0, 0, TITLE_FULL_WIDTH, 50);
        userLabel.setBounds(START_WIDTH, 150, 100, 30);
        passwordLabel.setBounds(START_WIDTH, 200, 100, 30);
        userTextField.setBounds(CENTER_WIDTH, 150, 200, 30);
        passwordField.setBounds(CENTER_WIDTH, 200, 200, 30);
        showPassword.setBounds(CENTER_WIDTH, 250, 200, 30);
        loginButton.setBounds(CENTER_WIDTH, 300, 100, 30);
        registerButton.setBounds(CENTER_WIDTH, 350, 100, 30);
    }

    public static BaseScreen getInstance() {
        //No queremos que cada vez que se clickee sobre la opcion
        //se cree una nueva instancia de la pantalla
        //Para evitar eso usamos el patron Singleton
        if (INSTANCE == null) {
            INSTANCE = new Login();
        }
        return INSTANCE;
    }

    @Override
    public String getTitle() {
        return LOGIN_TITLE;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            performLoginAction();
        }
        if (e.getSource() == registerButton) {
            performRegisterAction();
        }
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }
    }

    private void performLoginAction() {
        final String userText = userTextField.getText();
        final String passwordText = passwordField.getText();

        if (AuthenticationManager.getInstance().performUserAuthentication(userText, passwordText)) {
            showMessage("Usuario logueado exitosamente");
            new ScreenManager(this).goToScreen(ScreenName.ALTAS);
        } else {
            showMessage("Usuario o contrase\u00f1a incorrectos");
        }
    }

    private void performRegisterAction() {
        final String userText = userTextField.getText();
        final String passwordText = passwordField.getText();

        final UsuarioLogger.RegisterStatus registerStatus = AuthenticationManager.getInstance().performUserRegistration(userText, passwordText);
        String message;
        switch (registerStatus) {
            case DUPLICATED:
                message = "El usuario ya se encuentra registrado";
                break;
            case INVALID:
                message = "El usuario o contraseña tienen un formato no permitido";
                break;
            case SUCCESS:
                message = "El usuario ha sido registrado correctamente";
                break;
            default:
                message = "Ha ocurrido un error al intentar registrar al usuario.";
        }
        showMessage(message);
    }

}
