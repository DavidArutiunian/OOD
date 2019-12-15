package gui.canvas;

import kotlin.Unit;
import model.CanvasShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JCanvas extends JPanel {
    private transient Set<WeakReference<CanvasShape>> subscribed = new HashSet<>();
    private transient List<CanvasShape> shapes;

    public JCanvas(List<CanvasShape> shapes) {
        this.shapes = shapes;
        doOnShapesMutation();
    }

    public void doOnShapesMutation() {
        for (var shape : shapes) {
            // check if `subscribed` contains shape ref
            // also remove all null refs
            if (subscribed.stream().noneMatch(ref -> {
                var unref = ref.get();
                if (unref == null) {
                    subscribed.remove(ref);
                    return false;
                }
                return unref == shape;
            })) {
                var ref = new WeakReference<>(shape);
                subscribed.add(ref);

                shape.doOnMouseEntered(this::doOnMouseEntered);
                shape.doOnMouseExited(this::doOnMouseExited);
                shape.doOnMousePressed(this::doOnMousePressed);
                shape.doOnMouseRelease(this::doOnMouseReleased);
                shape.doOnMouseDragged(this::doOnMouseDragged);
            }
        }
    }

    private Unit doOnMouseEntered(CanvasShape shape, MouseEvent event) {
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return Unit.INSTANCE;
    }

    private Unit doOnMouseExited(CanvasShape shape, MouseEvent event) {
        setCursor(Cursor.getDefaultCursor());
        return Unit.INSTANCE;
    }

    private Unit doOnMousePressed(CanvasShape shape, MouseEvent event) {
        unselectAll();
        shape.select();
        repaint();
        return Unit.INSTANCE;
    }

    private Unit doOnMouseReleased(CanvasShape shape, MouseEvent event) {
        repaint();
        return Unit.INSTANCE;
    }

    private Unit doOnMouseDragged(CanvasShape shape, MouseEvent event) {
        repaint();
        return Unit.INSTANCE;
    }

    public void unselectAll() {
        for (var shape : shapes) {
            shape.unselect();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        for (var shape : shapes) {
            shape.paint(g2d, this);
        }
    }
}
