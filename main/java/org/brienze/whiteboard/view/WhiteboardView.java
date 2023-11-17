package org.brienze.whiteboard.view;

import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.*;

@Component
public class WhiteboardView {

    private final JFrame window = new JFrame();
    private final Canvas whiteboard = new Canvas(); //TODO this needs to be autowired
    private final JPanel leftMenu = new JPanel();
    private final JTextField textInput = new JTextField();
    private final JButton squareButton = new JButton("[ ]");
    private final JButton circleButton = new JButton("( )");
    private final JButton textButton = new JButton("A");
    private final JButton lineButton = new JButton("|");
    private final JButton clearButton = new JButton("X");

    private String selectedShape = "";
    private int drawingX = 0;
    private int drawingY = 0;
    private int drawingWidth = 0;
    private int drawingHeight = 0;

    public WhiteboardView() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
            e1.printStackTrace();
        }

        initializeWindow();
        initializeWhiteboard();
        initializeLeftMenu();
        initializeButtonActions();

        window.setVisible(true);
    }

    private void initializeWindow() {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Whiteboard App");
        window.setBounds(0, 0, 700, 500);
        window.setLayout(null);
        window.add(whiteboard);
        window.add(leftMenu);
    }

    private void initializeWhiteboard() {
        whiteboard.setBackground(Color.white);
        whiteboard.setBounds(200, 0, 500, 500);
    }

    private void initializeLeftMenu() {
        squareButton.setBounds(10, 10, 30, 30);
        circleButton.setBounds(50, 10, 30, 30);
        textButton.setBounds(90, 10, 30, 30);
        lineButton.setBounds(130, 10, 30, 30);
        clearButton.setBounds(130, 90, 30, 30);

        textInput.setBounds(10, 50, 150, 30);

        leftMenu.setBounds(10, 10, 170, 450);
        leftMenu.setBorder(new BasicBorders.ButtonBorder(Color.gray, Color.darkGray, Color.gray, Color.gray));
        leftMenu.setBackground(Color.lightGray);
        leftMenu.setLayout(null);
        leftMenu.add(squareButton);
        leftMenu.add(circleButton);
        leftMenu.add(textButton);
        leftMenu.add(lineButton);
        leftMenu.add(textInput);
        leftMenu.add(clearButton);
    }

    private void initializeButtonActions() {
        whiteboard.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);

                drawingX = e.getX();
                drawingY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                Graphics g = whiteboard.getGraphics();

                drawingWidth = e.getX() - drawingX;
                drawingHeight = e.getY() - drawingY;

                int tempDrawingX = drawingX;
                int tempDrawingY = drawingY;

                if (drawingWidth < 0) {
                    tempDrawingX += drawingWidth;
                    drawingWidth *= -1;
                }

                if (drawingHeight < 0) {
                    tempDrawingY += drawingHeight;
                    drawingHeight *= -1;
                }

                switch (selectedShape) {
                    case "square" -> g.drawRect(tempDrawingX, tempDrawingY, drawingWidth, drawingHeight);
                    case "circle" -> g.drawOval(tempDrawingX, tempDrawingY, drawingWidth, drawingHeight);
                    case "text" -> g.drawString(textInput.getText(), drawingX, drawingY);
                    case "line" -> g.drawLine(drawingX, drawingY, e.getX(), e.getY());
                }
            }
        });

        whiteboard.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                Graphics g = whiteboard.getGraphics();

                drawingWidth = e.getX() - drawingX;
                drawingHeight = e.getY() - drawingY;

                int tempDrawingX = drawingX;
                int tempDrawingY = drawingY;

                if (drawingWidth < 0) {
                    tempDrawingX += drawingWidth;
                    drawingWidth *= -1;
                }

                if (drawingHeight < 0) {
                    tempDrawingY += drawingHeight;
                    drawingHeight *= -1;
                }

//                switch (selectedShape) {
//                    case "square" ->  g.drawRect(drawingX, drawingY, drawingWidth, drawingHeight);
//                    case "circle" ->  g.drawOval(drawingX, drawingY, drawingWidth, drawingHeight);
//                }
            }
        });

        squareButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedShape = "square";
            }
        });

        circleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedShape = "circle";
            }
        });

        textButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedShape = "text";
            }
        });

        lineButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedShape = "line";
            }
        });

        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                whiteboard.repaint();
            }
        });
    }
}
