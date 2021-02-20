package com.signwithmouse;

import com.signwithmouse.util.GetFileChooser;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * JPanel donde se firmara
 */
@Slf4j
public class JPanelForSign extends JPanel {

    private static final int WIDTH = 400;

    private BufferedImage bufferedImage = new BufferedImage(WIDTH, WIDTH, BufferedImage.TYPE_INT_ARGB);
    private final AtomicInteger uX = new AtomicInteger();
    private final AtomicInteger uY = new AtomicInteger();

    public JPanelForSign() {
        super.setBackground(Color.WHITE);
        super.requestFocus();
        super.setBorder(BorderFactory.createEtchedBorder());
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                uX.set(e.getX());
                uY.set(e.getY());
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                final int x = e.getX();
                final int y = e.getY();
                final Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
                g2.setBackground(Color.WHITE);
                g2.setColor(Color.BLACK);
                g2.drawLine(uX.get(), uY.get(), x, y);
                uX.set(x);
                uY.set(y);
                g2.dispose();
            }
        });

    }

    public void clearSignZone() {
        this.createEmptyImage();
        super.repaint();
    }

    private void createEmptyImage() {
        bufferedImage = new BufferedImage(super.getWidth(), super.getHeight(), BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
        g2d.setColor(Color.BLACK);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        final Graphics2D graphics2D = (Graphics2D) g.create();
        graphics2D.drawImage(bufferedImage, 0, 0, null);
        super.repaint();
    }

    /**
     * Método que guarda la imagen generada
     */
    public void guardarFirma() {
        final JFileChooser jFileChooser = GetFileChooser.getFileChooser(JFileChooser.DIRECTORIES_ONLY);
        if (jFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            Path path1 = jFileChooser.getSelectedFile().toPath();
            final String fileN = path1.getFileName().toString();

            if(!fileN.endsWith(".png")) {//En caso de que no termine en .png se lo concatenamos
                try {
                    String fileName = path1.getFileName().toString().concat(".png");
                    path1 = path1.resolveSibling(fileName);
                    ImageIO.write(bufferedImage, "png", path1.toFile());
                    JOptionPane.showMessageDialog(null, "Imagen creada correctamente!");
                } catch (IOException e) {
                    log.warn(e.getMessage());
                    JOptionPane.showMessageDialog(null, "Error al guardar!");
                }
            } else {
                try {
                    final Path path2 = jFileChooser.getSelectedFile().toPath();
                    ImageIO.write(bufferedImage, "png", path2.toFile());
                    JOptionPane.showMessageDialog(null, "Imagen creada correctamente!");
                } catch (IOException e) {
                    log.warn(e.getMessage());
                    JOptionPane.showMessageDialog(null, "Error al guardar!");
                }
            }
        }
    }
}