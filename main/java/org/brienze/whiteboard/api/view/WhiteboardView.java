package org.brienze.whiteboard.api.view;

import org.brienze.whiteboard.api.enums.Type;
import org.brienze.whiteboard.api.viewmodel.WhiteboardViewModel;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;
import java.util.UUID;

@Component
public class WhiteboardView {

    private final WhiteboardViewModel whiteboardViewModel;
    private final JFrame window = new JFrame();
    private final JPanel leftMenu = new JPanel();
    private final JPanel bottomMenu = new JPanel();
    private final JTextField textInput = new JTextField();
    private final JTextField whiteboardInput = new JTextField();
    private final JButton loadButton = new JButton("Load");
    private final JButton squareButton = new JButton(new IconMaker("Rectangle", 30, 15, 2));
    private final JButton circleButton = new JButton(new IconMaker("Circle", 20.0, 2));
    private final JButton textButton = new JButton("A");
    private final JButton lineButton = new JButton(new IconMaker("Line", 10, 2));
    private final JButton clearButton = new JButton("X");
    private final JButton clearAllButton = new JButton("Clear All");
    private final JButton undoButton = new JButton("Undo");

    private Type selectedShape = null;
    private int drawingX = 0;
    private int drawingY = 0;
    private UUID currentShapeId;

    public WhiteboardView(WhiteboardViewModel whiteboardViewModel) {
        this.whiteboardViewModel = whiteboardViewModel;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
            e1.printStackTrace();
        }

        initializeWindow();
        initializeWhiteboardTop();
        initializeBottomMenu();
        initializeButtonActions();

        window.setVisible(true);
    }

    private void initializeWindow() {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Whiteboard App");
        window.setBounds(0, 0, 800, 500);
        window.setLayout(null);
        window.add(whiteboardViewModel);
        window.add(bottomMenu);
    }

    private void initializeWhiteboardTop() {
        whiteboardViewModel.setBackground(Color.white);
        whiteboardViewModel.setBounds(0, 0, 800, 360);
    }

    private void initializeBottomMenu() {
        squareButton.setBounds(10, 10, 50, 30);
        circleButton.setBounds(70, 10, 50, 30);
        lineButton.setBounds(130, 10, 50, 30);
        undoButton.setBounds(190, 10, 110, 30);
        clearAllButton.setBounds(310, 10, 85, 70);
        undoButton.setBackground(Color.pink);
        clearAllButton.setBackground(Color.pink);

        squareButton.setFocusPainted(false);
        circleButton.setFocusPainted(false);
        lineButton.setFocusPainted(false);
        undoButton.setFocusPainted(false);
        clearAllButton.setFocusPainted(false);

        textInput.setBounds(10, 50, 230, 30);
        textButton.setBounds(250, 50, 50, 30);
        textButton.setFocusPainted(false);

        whiteboardInput.setBounds(410, 10, 270, 30);
        loadButton.setBounds(690, 10, 70, 30);
        loadButton.setFocusPainted(false);

        bottomMenu.setBounds(10, 370, 770, 90);
        bottomMenu.setBorder(
                new BasicBorders.ButtonBorder(
                        Color.gray, Color.darkGray, Color.gray, Color.gray
                )
        );
        bottomMenu.setBackground(Color.lightGray);
        bottomMenu.setLayout(null);
        bottomMenu.add(squareButton);
        bottomMenu.add(circleButton);
        bottomMenu.add(textButton);
        bottomMenu.add(lineButton);
        bottomMenu.add(undoButton);
        bottomMenu.add(clearAllButton);
        bottomMenu.add(textInput);
        bottomMenu.add(loadButton);
        bottomMenu.add(whiteboardInput);
    }

    private void unselectAll() {
        squareButton.setBackground(Color.white);
        circleButton.setBackground(Color.white);
        lineButton.setBackground(Color.white);
        undoButton.setBackground(Color.pink);
        clearAllButton.setBackground(Color.pink);
        textButton.setBackground(Color.white);
        loadButton.setBackground(Color.white);
    }

    private void initializeButtonActions() {
        whiteboardViewModel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);

                if (whiteboardViewModel.getName() == null) {
                    String name = Optional.ofNullable(whiteboardInput.getText()).filter(string -> !string.isBlank()).orElse(UUID.randomUUID().toString());
                    whiteboardInput.setText(name);
                    whiteboardViewModel.load(name);
                }

                drawingX = e.getX();
                drawingY = e.getY();

                currentShapeId = whiteboardViewModel.draw(selectedShape, textInput.getText(), drawingX, drawingY);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                whiteboardViewModel.save(currentShapeId, e.getX(), e.getY());
            }
        });

        whiteboardViewModel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                whiteboardViewModel.update(currentShapeId, e.getX(), e.getY());
            }
        });

        squareButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedShape = Type.RECTANGLE;
                unselectAll();
                e.getComponent().setBackground(Color.cyan);
            }
        });

        circleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedShape = Type.CIRCLE;
                unselectAll();
                e.getComponent().setBackground(Color.cyan);
            }
        });

        textButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedShape = Type.TEXT;
                unselectAll();
                e.getComponent().setBackground(Color.cyan);
            }
        });

        lineButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedShape = Type.LINE;
                unselectAll();
                e.getComponent().setBackground(Color.cyan);
            }
        });

        undoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                whiteboardViewModel.undoShape();
                unselectAll();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setBackground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                e.getComponent().setBackground(Color.pink);
            }
        });

        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                whiteboardViewModel.clear();
            }
        });

        clearAllButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                whiteboardViewModel.clear();
                unselectAll();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setBackground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                e.getComponent().setBackground(Color.pink);
            }
        });

        loadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                whiteboardViewModel.load(whiteboardInput.getText());
            }
        });
    }
}
