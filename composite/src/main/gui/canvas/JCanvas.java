package gui.canvas;

import model.CanvasShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

public class JCanvas extends JPanel {
    private transient List<CanvasShape> shapes;

    private transient MouseAdapter shapeMouseAdapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setCursor(Cursor.getDefaultCursor());
        }

        @Override
        public void mousePressed(MouseEvent e) {
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            repaint();
        }
    };

    private transient MouseMotionAdapter shapeMouseMotionAdapter = new MouseMotionAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {
            repaint();
        }
    };

    public JCanvas(List<CanvasShape> shapes) {
        this.shapes = shapes;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        for (var shape : shapes) {
            shape.paint(g2d, this);

            shape.removeMouseListener(shapeMouseAdapter);
            shape.addMouseListener(shapeMouseAdapter);

            shape.removeMouseMotionListener(shapeMouseMotionAdapter);
            shape.addMouseMotionListener(shapeMouseMotionAdapter);
        }
    }
}
