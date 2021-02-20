package com.signwithmouse;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * https://foro.elhacker.net/java/problemas_con_app_simple_para_dibujar_firma_y_guardarla_como_imagen-t427969.0.html
 *
 * Nombre de clase = recogeFirma
 * Descripcion = Clase encargada de capturar la firma que se dibuja en pantalla
 *
 * @version 1.0
 * @author rubn
 * @author google
 * @author mas personas
 */
@Slf4j
public class MainJFrame extends JFrame {

    private static final String TITLE = "App Firma";
    private JButton jButtonBorrar = new JButton("Borrar");
    private JButton jButtonGuardar = new JButton("Guardar");
    private JLabel jLabelIL = new JLabel("Introduzca su firma");

    private final JPanel mainJPanel = new JPanel();
    //Algo de Magia
    private JPanelForSign jPanelForSign = new JPanelForSign();

    public MainJFrame() {

        this.configureJFrame();
        this.configureJPanels();
        this.initBehaviour();

    }

    private void configureJFrame() {
        super.setResizable(false);
        super.setPreferredSize(new Dimension(400,350));
        super.add(mainJPanel);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.pack();
        super.setLocationRelativeTo(null);
        super.setVisible(true);
    }

    private void configureJPanels() {

        final TitledBorder border = new TitledBorder(TITLE);
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);

        this.mainJPanel.setBorder(border);
        this.mainJPanel.setLayout(new BorderLayout());

        final JPanel panelFooter = new JPanel();
        panelFooter.setLayout(new FlowLayout());
        panelFooter.add(new JLabel());
        panelFooter.add(jLabelIL);
        panelFooter.add(jButtonBorrar);
        panelFooter.add(jButtonGuardar);
        mainJPanel.add(jPanelForSign);
        mainJPanel.add(panelFooter,BorderLayout.SOUTH);
        mainJPanel.setPreferredSize(new Dimension(450, 250));
    }

    private void initBehaviour() {
        jButtonBorrar.addActionListener(e -> {
            //Blanquear la imagen de la firma
            jPanelForSign.clearSignZone();
        });
        jButtonGuardar.addActionListener(e -> {
            jPanelForSign.guardarFirma();
        });
    }

    public static void main(String... blabla) {
        final String osType = System.getProperty("os.name");
        try {
            if(osType.contains("Win")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }else if(osType.contains("Linux")) {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            }
        }catch(Exception ex) {}

        new Thread(MainJFrame::new).start();
    }

}
