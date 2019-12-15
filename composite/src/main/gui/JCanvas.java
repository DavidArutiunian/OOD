package gui;

import kotlin.Unit;
import model.CanvasShape;
import model.CompositeShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JCanvas extends JPanel {
    private boolean selecting = false;

    private transient Set<WeakReference<CanvasShape>> subscribed = new HashSet<>();
    private transient List<CanvasShape> shapes;

    public JCanvas(List<CanvasShape> shapes) {
        this.shapes = shapes;
        doOnShapesMutation();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    selecting = true;
                }
                if (selecting && e.getKeyCode() == KeyEvent.VK_G) {
                    var group = shapes
                        .stream()
                        .filter(CanvasShape::isSelected)
                        .peek(CanvasShape::unselect)
                        .peek(CanvasShape::disableInteraction)
                        .collect(Collectors.toList());
                    // skip self grouping
                    if (group.size() == 1) {
                        return;
                    }
                    var composite = new CompositeShape(group);
                    composite.select();
                    shapes.removeIf(CanvasShape::isSelected);
                    shapes.add(composite);
                    doOnShapesMutation();
                    repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    selecting = false;
                }
            }
        });
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
                shape.doOnMouseReleased(this::doOnMouseReleasedAndDragged);
                shape.doOnMouseDragged(this::doOnMouseReleasedAndDragged);
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
        requestFocusInWindow();
        if (!selecting) {
            unselectAll();
        }
        shape.select();
        repaint();
        return Unit.INSTANCE;
    }

    private Unit doOnMouseReleasedAndDragged(CanvasShape shape, MouseEvent event) {
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
