package ui;

import BL.LoggerFile;
import constants.ScreenName;
import controllers.IOController;
import controllers.ScreenManager;
import controllers.AuthenticationManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static constants.Dimens.WINDOW_HEIGHT;
import static constants.Dimens.WINDOW_WIDTH;

public class InterfazPrincipal extends JFrame {

    private static final String GESTOR_DE_LIBROS_TITLE = "Gestor de Libros";
    private final JPanel contentPane = new JPanel();
    private final JPanel menuPanel = new JPanel();
    private final JPanel mainPanel = new JPanel();
    private final JButton menuAltasButton = new JButton("Altas");
    private final JButton menuConsultasButton = new JButton("Consultas");
    private final JButton menuActualizacionesButton = new JButton("Actualizaciones");
    private final JButton menuBajasButton = new JButton("Bajas");
    private final JButton menuOrdenarRegistrosButton = new JButton("Ordenar registros");
    private final JButton menuListarRegistrosButton = new JButton("Listar Registros");
    private final JButton menuSalirButton = new JButton("Salir");
    private final ScreenManager screenManager;

    public InterfazPrincipal() {
        //Se configura la ventana principal
        setupWindow();
        //Se configura el contenedor general
        setupContentPane();
        //Se configura el contenedor del menu
        setupMenuPane();
        //Se configura el contenedor principal
        setupMainPane();
        //Se agregan las vistas al contenedor
        addViewsToContentPane();
        //Se instancia el manejador de pantallas
        this.screenManager = new ScreenManager(mainPanel);
        //Por defecto vamos a Login
        screenManager.goToScreen(ScreenName.LOGIN);
        //Se agrega un listener para poder guardar los cambios
        addExitListener();
    }

    private void addExitListener() {
        //Escuchamos el evento de window closing para poder hacer un guardado de los registros
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitAndSaveConfirmation();
            }
        });
    }

    private void exitAndSaveConfirmation() {
        //Si el usuario no esta logueado no necesitamos guardar nada
        if (!AuthenticationManager.getInstance().isUserLogged()) {
            return;
        }
        //Le pedimos al usuario que nos confirme si quiere guardar los registros
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Vas a salir.\nSe deben guardar los registros?", "Confirmar", dialogButton);
        if (dialogResult == 0) {
            //Solamente guardamos si nos dice que si
            IOController.getInstance().saveAll();
            JOptionPane.showMessageDialog(this, "Se han guardado correctamente los registros");
        }else{
            LoggerFile.getInstance().Log("El usuario no guardo los registros existentes");
        }

    }

    private void addViewsToContentPane() {
        //Se agregan las vistas al contenedor
        contentPane.add(menuPanel, BorderLayout.LINE_START);
        contentPane.add(mainPanel, BorderLayout.CENTER);
    }

    private void setupMainPane() {
        //Se configura el contenedor principal
        final LineBorder line = new LineBorder(Color.black, 2, true);
        mainPanel.setBorder(line);
    }

    private void setupMenuPane() {
        //Se configura el contenedor del menu
        //Seteamos un color de fondo
        menuPanel.setBackground(new Color(227, 232, 229));
        //Asignamos una posicion y un tamanio fijo
        menuPanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        menuPanel.setLayout(new GridLayout(8, 1));
        setupButtonActions();
        addSubviewsToMenuPane();
        setupExitButton();
    }

    private void setupExitButton() {
        //Configuramos el evento a ejecutar al seleccionar la opcion de Salir
        menuSalirButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                //Cuando ocurra el evento, vamos a pedir confirmacion para guardar los registros
                exitAndSaveConfirmation();
                //Luego terminamos el programa
                dispose();
            }
        });
    }

    private void addSubviewsToMenuPane() {
        //Se agregan las vistas al contenedor de menu
        menuPanel.add(new JLabel("Menu", SwingConstants.CENTER));
        menuPanel.add(menuAltasButton);
        menuPanel.add(menuConsultasButton);
        menuPanel.add(menuActualizacionesButton);
        menuPanel.add(menuBajasButton);
        menuPanel.add(menuOrdenarRegistrosButton);
        menuPanel.add(menuListarRegistrosButton);
        menuPanel.add(menuSalirButton);
    }

    private void setupButtonActions() {
        //Se agregan las acciones para los botones del menu
        setupButtonAction(menuAltasButton, ScreenName.ALTAS);
        setupButtonAction(menuConsultasButton, ScreenName.CONSULTAS);
        setupButtonAction(menuActualizacionesButton, ScreenName.ACTUALIZACIONES);
        setupButtonAction(menuBajasButton, ScreenName.BAJAS);
        setupButtonAction(menuOrdenarRegistrosButton, ScreenName.ORDENAR_REGISTROS);
        setupButtonAction(menuListarRegistrosButton, ScreenName.LISTAR_REGISTROS);
    }

    private void setupButtonAction(final JButton button, final ScreenName screenName) {
        //Se agrega el comportamiento para los botones del menu
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                //Cuando se haga click, hay que ir a la pantalla correspondiente
                screenManager.goToScreen(screenName);
            }
        });
    }

    private void setupContentPane() {
        //Se configura el contendor general
        contentPane.setVerifyInputWhenFocusTarget(false);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        BorderLayout layout = new BorderLayout();
        layout.setHgap(10);
        contentPane.setLayout(layout);
        contentPane.setBackground(new Color(23, 43, 56));
    }

    private void setupWindow() {
        //Se configura la ventana principal
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle(GESTOR_DE_LIBROS_TITLE);
        setLocationRelativeTo(null);
    }

}
