package gui;

import gui.canvas.JCanvas;
import gui.color_chooser.ColorChooserButton;
import gui.state.EllipseShapeState;
import gui.state.LineShapeState;
import gui.state.RectangleShapeState;
import gui.state.ShapeState;
import model.CanvasShape;
import model.StrokeWidth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class GUI extends JFrame {
    private JPanel root;

    private JCanvas canvas;

    private StrokeWidth strokeWidth = StrokeWidth.ONE;
    private Color strokeColor = Color.BLACK;
    private Color fillColor = Color.BLACK;

    private ColorChooserButton fillColorPicker;
    private ColorChooserButton strokeColorChooser;
    private ButtonGroup shapeRadioGroup;
    private JRadioButton lineRadio;
    private JRadioButton ellipseRadio;
    private JRadioButton rectangleRadio;

    private transient ArrayList<CanvasShape> shapes;

    private transient LineShapeState lineShapeState;
    private transient EllipseShapeState ellipseShapeState;
    private transient RectangleShapeState rectangleShapeState;
    private transient ShapeState shapeState = null;

    public GUI(String title, int width, int height) {
        setTitle(title);
        setContentPane(root);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        setVisible(true);

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (shapeState != null) {
                    shapeState.handlePress(e);
                    canvas.repaint();
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                shapeRadioGroup.clearSelection();
                shapeState = null;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (shapeState != null) {
                    shapeState.handleRelease(e);
                    canvas.repaint();
                }
            }
        });

        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (shapeState != null) {
                    shapeState.handleDrag(e);
                    canvas.repaint();
                }
            }
        });

        lineRadio.addActionListener(e -> shapeState = lineShapeState);
        ellipseRadio.addActionListener(e -> shapeState = ellipseShapeState);
        rectangleRadio.addActionListener(e -> shapeState = rectangleShapeState);
    }

    private void createUIComponents() {
        shapes = new ArrayList<>();
        canvas = new JCanvas(shapes);

        strokeColorChooser = new ColorChooserButton(strokeColor);
        strokeColorChooser.addColorChangedListener(color -> strokeColor = color);
        fillColorPicker = new ColorChooserButton(fillColor);
        fillColorPicker.addColorChangedListener(color -> fillColor = color);

        lineShapeState = new LineShapeState(shapes, fillColor, strokeColor, strokeWidth);
        ellipseShapeState = new EllipseShapeState(shapes, fillColor, strokeColor, strokeWidth);
        rectangleShapeState = new RectangleShapeState(shapes, fillColor, strokeColor, strokeWidth);

        fillColorPicker.addColorChangedListener(lineShapeState::fillColorChanged);
        strokeColorChooser.addColorChangedListener(lineShapeState::strokeColorChanged);
        fillColorPicker.addColorChangedListener(ellipseShapeState::fillColorChanged);
        strokeColorChooser.addColorChangedListener(ellipseShapeState::strokeColorChanged);
        fillColorPicker.addColorChangedListener(rectangleShapeState::fillColorChanged);
        strokeColorChooser.addColorChangedListener(rectangleShapeState::strokeColorChanged);
    }
}