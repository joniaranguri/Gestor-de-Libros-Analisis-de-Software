package ui.screens;

import controllers.AuthenticationManager;
import ui.screens.base.BaseScreen;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static constants.Dimens.*;

public class Login extends BaseScreen implements ActionListener {
    private static BaseScreen INSTANCE;
    private static final String LOGIN_TITLE = "Bienvenido al Gestor de Libros \n" +
            "Inicie sesión para continuar";
    JLabel userLabel=new JLabel("Usuario");
    JLabel passwordLabel=new JLabel("Contraseña");
    JTextField userTextField=new JTextField();
    JPasswordField passwordField=new JPasswordField();
    JButton loginButton=new JButton("Acceder");
    JCheckBox showPassword=new JCheckBox("Mostrar Contraseña");



    private Login(){
        setLayout(null);
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    public void addActionEvent() {
        loginButton.addActionListener(this);
        showPassword.addActionListener(this);
    }
    public void addComponentsToContainer() {
        add(titleView);
        add(userLabel);
        add(passwordLabel);
        add(userTextField);
        add(passwordField);
        add(showPassword);
        add(loginButton);
    }

    public void setLocationAndSize(){
        //Setting location and Size of each components using setBounds() method.
        titleView.setBounds(0,0,TITLE_FULL_WIDTH,50);
        userLabel.setBounds(START_WIDTH,150,100,30);
        passwordLabel.setBounds(START_WIDTH,200,100,30);
        userTextField.setBounds(CENTER_WIDTH,150,200,30);
        passwordField.setBounds(CENTER_WIDTH,200,200,30);
        showPassword.setBounds(CENTER_WIDTH,250,200,30);
        loginButton.setBounds(CENTER_WIDTH,300,100,30);


    }
    public static BaseScreen getInstance() {
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
            final String userText = userTextField.getText();
            final char[] passwordText = passwordField.getPassword();

            if (AuthenticationManager.getInstance().performUserAuthentication(userText, passwordText)) {
                showMessage("Usuario logueado exitosamente");
            } else {
                showMessage("Usuario o contraseña incorrectos");
            }

        }
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }


        }
    }

}
