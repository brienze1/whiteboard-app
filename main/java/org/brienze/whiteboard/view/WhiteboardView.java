package org.brienze.whiteboard.view;

import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

@Component
public class WhiteboardView {

    private final JFrame whiteboard = new JFrame();
    private final JPanel leftMenu = new JPanel();;

    public WhiteboardView() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
//            e1.printStackTrace();
        }

        initializeWhiteboard();
        initializeLeftMenu();

        whiteboard.setVisible(true);
    }

    private void initializeWhiteboard() {
        whiteboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        whiteboard.setResizable(false);
        whiteboard.setTitle("Whiteboard App");
        whiteboard.setBounds(0, 0, 700, 500);
        whiteboard.setLayout(null);
        whiteboard.add(leftMenu);
    }

    private void initializeLeftMenu() {
        leftMenu.setBounds(10,10,170,450);
        leftMenu.setBorder(new BasicBorders.ButtonBorder(Color.gray, Color.darkGray, Color.gray, Color.gray));
        leftMenu.setBackground(Color.WHITE);
    }
}
