package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ColorChooserButton extends JButton {
    private Color current;
    private List<ColorChangedListener> listeners = new ArrayList<>();

    public ColorChooserButton(Color c) {
        setSelectedColor(c);
        addActionListener(e -> {
            Color color = JColorChooser.showDialog(null, "Выберите цвет", current);
            setSelectedColor(color);
        });
    }

    public static ImageIcon createIcon(Color main, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(main);
        graphics.fillRect(0, 0, width, height);
        graphics.setXORMode(Color.DARK_GRAY);
        graphics.drawRect(0, 0, width - 1, height - 1);
        image.flush();
        return new ImageIcon(image);
    }

    public void setSelectedColor(Color newColor) {
        setSelectedColor(newColor, true);
    }

    public void setSelectedColor(Color color, boolean notify) {
        if (color == null) {
            return;
        }

        current = color;
        setIcon(createIcon(current, 16, 16));
        repaint();

        if (notify) {
            for (var listener : listeners) {
                listener.doOnColorChanged(color);
            }
        }
    }

    public void addColorChangedListener(ColorChangedListener listener) {
        listeners.add(listener);
    }
}
