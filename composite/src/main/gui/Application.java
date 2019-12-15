package gui;

import gui.state.EllipseShapeState;
import gui.state.LineShapeState;
import gui.state.RectangleShapeState;
import gui.state.ShapeState;
import kotlin.Unit;
import model.CanvasShape;
import model.StrokeWidth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class Application extends JFrame {
    // root panel
    private JPanel root;
    // canvas
    private JCanvas canvas;

    // stroke & fill
    private StrokeWidth strokeWidth = StrokeWidth.ONE;
    private Color strokeColor = Color.BLACK;
    private Color fillColor = Color.BLACK;

    // color pickers
    private ColorChooserButton fillColorPicker;
    private ColorChooserButton strokeColorChooser;

    // mode switchers
    private JRadioButton lineRadio;
    private JRadioButton ellipseRadio;
    private JRadioButton rectangleRadio;
    private JRadioButton cursorRadio;

    // shapes store
    private transient ArrayList<CanvasShape> shapes;

    // states handling shape creation
    private transient LineShapeState lineShapeState;
    private transient EllipseShapeState ellipseShapeState;
    private transient RectangleShapeState rectangleShapeState;
    private transient ShapeState shapeState = null;

    private static Application instance = null;

    private Application(String title, int width, int height) {
        initFrame(title, width, height);
        initCanvasMouseListener();
        initCanvasMouseMotionListener();
        initRadioActionListeners();
    }

    public static Application getInstance(String title, int width, int height) {
        if (instance == null) {
            instance = new Application(title, width, height);
        }
        return instance;
    }

    private void initRadioActionListeners() {
        lineRadio.addActionListener(e -> shapeState = lineShapeState);
        ellipseRadio.addActionListener(e -> shapeState = ellipseShapeState);
        rectangleRadio.addActionListener(e -> shapeState = rectangleShapeState);
        cursorRadio.addActionListener(e -> {
            canvas.unselectAll();
            shapeState = null;
        });
    }

    private void initCanvasMouseMotionListener() {
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            private Unit doOnShapesMutation() {
                canvas.doOnShapesMutation();
                return Unit.INSTANCE;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (shapeState != null) {
                    shapeState.handleDrag(e, this::doOnShapesMutation);
                    canvas.repaint();
                }
            }
        });
    }

    private void initCanvasMouseListener() {
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                canvas.requestFocusInWindow();
                canvas.unselectAll();
                if (shapeState != null) {
                    shapeState.handlePress(e);
                }
                canvas.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (shapeState != null) {
                    shapeState.handleRelease(e);
                }
                canvas.repaint();
            }
        });
    }

    private void initFrame(String title, int width, int height) {
        setTitle(title);
        setContentPane(root);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        setVisible(true);
    }

    @Override
    public void requestFocus() {
        super.requestFocus();
        canvas.requestFocusInWindow(); // key listeners won't work without this
    }

    // form constructor
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
