package ui;

import constants.ScreenName;
import controllers.ScreenManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        setupWindow();
        setupContentPane();
        setupMenuPane();
        setupMainPane();
        addViewsToContentPane();
        this.screenManager = new ScreenManager(mainPanel);
    }

    private void addViewsToContentPane() {
        contentPane.add(menuPanel, BorderLayout.LINE_START);
        contentPane.add(mainPanel, BorderLayout.CENTER);
    }

    private void setupMainPane() {
        // mainPanel.setBackground(new Color(227, 232, 229, 100));
        final LineBorder line = new LineBorder(Color.black, 2, true);
        mainPanel.setBorder(line);
    }

    private void setupMenuPane() {
        menuPanel.setBackground(new Color(227, 232, 229));
        menuPanel.setBounds(0, 0, 500, 500);
        menuPanel.setLayout(new GridLayout(8, 1));
        setupButtonActions();
        addSubviewsToMenuPane();
        setupExitButton();
    }

    private void setupExitButton() {
        menuSalirButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                dispose();
            }
        });
    }

    private void addSubviewsToMenuPane() {
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
        setupButtonAction(menuAltasButton, ScreenName.ALTAS);
        setupButtonAction(menuConsultasButton, ScreenName.CONSULTAS);
        setupButtonAction(menuActualizacionesButton, ScreenName.ACTUALIZACIONES);
        setupButtonAction(menuBajasButton, ScreenName.BAJAS);
        setupButtonAction(menuOrdenarRegistrosButton, ScreenName.ORDENAR_REGISTROS);
        setupButtonAction(menuListarRegistrosButton, ScreenName.LISTAR_REGISTROS);
    }

    private void setupButtonAction(final JButton button, final ScreenName screenName) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                screenManager.goToScreen(screenName);
            }
        });
    }

    private void setupContentPane() {
        contentPane.setVerifyInputWhenFocusTarget(false);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        BorderLayout layout = new BorderLayout();
        layout.setHgap(10);
        contentPane.setLayout(layout);
        contentPane.setBackground(new Color(23, 43, 56));
    }

    private void setupWindow() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        setTitle(GESTOR_DE_LIBROS_TITLE);
        setLocationRelativeTo(null);
    }

}
