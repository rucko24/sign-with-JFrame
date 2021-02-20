package com.signwithmouse.util;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 */
public class GetFileChooser {

    private static final FileNameExtensionFilter FILTERS = new FileNameExtensionFilter(
            "Archivo de imagen", "png");

    /**
     *
     * @param type tipo de dialogo, archivos, directorios etc
     * @return JFileChooser con ruta destino
     */
    public static JFileChooser getFileChooser(final int type) {
        final JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("Guardar firma!");
        jFileChooser.setDialogType(type);
        jFileChooser.setFileFilter(FILTERS);
        return jFileChooser;
    }
}