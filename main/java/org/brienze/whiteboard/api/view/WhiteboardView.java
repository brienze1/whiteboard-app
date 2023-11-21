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
    private final JButton squareButton = new JButton("[ ]");
    private final JButton circleButton = new JButton("( )");
    private final JButton textButton = new JButton("A");
    private final JButton lineButton = new JButton("|");
    private final JButton clearButton = new JButton("X");
    private final JButton clearAllButton = new JButton("Clear All");

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

        // Switch functions according to view needs, currently using new testing ones
        // To change back to initial view,
        // swap with initializeWindow(), initializeWhiteboard(), initializeLeftMenu()
        // respectively
        initializeWindowNew();
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
        window.add(leftMenu);
    }

    private void initializeWhiteboard() {
        whiteboardViewModel.setBackground(Color.white);
        whiteboardViewModel.setBounds(260, 0, 650, 500);
    }

    private void initializeLeftMenu() {
        squareButton.setBounds(10, 10, 50, 30);
        circleButton.setBounds(70, 10, 50, 30);
        textButton.setBounds(130, 10, 50, 30);
        lineButton.setBounds(190, 10, 50, 30);
        clearButton.setBounds(190, 90, 50, 30);
        textInput.setBounds(10, 50, 230, 30);
        loadButton.setBounds(10, 370, 70, 30);
        whiteboardInput.setBounds(10, 400, 150, 30);

        leftMenu.setBounds(10, 10, 250, 450);
        leftMenu.setBorder(new BasicBorders.ButtonBorder(Color.gray, Color.darkGray, Color.gray, Color.gray));
        leftMenu.setBackground(Color.lightGray);
        leftMenu.setLayout(null);
        leftMenu.add(squareButton);
        leftMenu.add(circleButton);
        leftMenu.add(textButton);
        leftMenu.add(lineButton);
        leftMenu.add(clearButton);
        leftMenu.add(textInput);
        leftMenu.add(loadButton);
        leftMenu.add(whiteboardInput);
    }

    // -----------------------------------------------------------------------
    private void initializeWindowNew() {
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
        clearAllButton.setBounds(190, 10, 110, 30);

        textButton.setBounds(250, 50, 50, 30);
        textInput.setBounds(10, 50, 230, 30);

        whiteboardInput.setBounds(380, 10, 300, 30);
        loadButton.setBounds(690, 10, 70, 30);

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
        bottomMenu.add(clearAllButton);
        bottomMenu.add(textInput);
        bottomMenu.add(loadButton);
        bottomMenu.add(whiteboardInput);
    }

    // -----------------------------------------------------------------------

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
            }
        });

        circleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedShape = Type.CIRCLE;
            }
        });

        textButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedShape = Type.TEXT;
            }
        });

        lineButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedShape = Type.LINE;
            }
        });

        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                whiteboardViewModel.clear();
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
