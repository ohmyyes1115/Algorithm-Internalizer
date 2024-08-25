package io.github.ohmyyes1115;

import java.awt.*;
import java.awt.image.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.border.*;
import javax.swing.text.*;
import javax.swing.undo.UndoManager;
import javax.imageio.*;

import io.github.ohmyyes1115.component.DragMover;

class MainUI extends JFrame {
    
    private static int        L_PANEL_MAX_WIDTH = 420;
    private static int ELSE_PANEL_HEIGHT = 70;
    
    private final static Color BOTTOM_LAYER_BG_COLOR = new Color( 45,  45,  48);
    private final static Color         VIEW_BG_COLOR = new Color( 30,  30,  30);
    private final static Color     VIEW_BORDER_COLOR = new Color(169, 169, 169);
    private final static Color            TEXT_COLOR = new Color(220, 220, 220);
    private final static Color           CARET_COLOR = new Color(225, 225, 225);

    private GroupLayout m_main_layout = null;
    private JComponent m_desc_comp = null;
    
    private JTextPane m_code_comp;  // coding text-pane

    private CodingView m_codingView;

    public MainUI() {
        super("Time To Repeat");

        // hardcode layout for 1920x1080 for now
        setupWindowSize();
        
        // Exit the program when the window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main_panel = new JPanel();
        main_panel.setBackground(BOTTOM_LAYER_BG_COLOR);

        m_main_layout = new GroupLayout(main_panel);
        main_panel.setLayout(m_main_layout);

        add(main_panel);

        initLayout();

        // SwingUtilities.invokeLater(() -> {});

        setVisible(true);
    }

    public CodingView getCodingView() {
        if (m_codingView == null) {
            m_codingView = createCodingView();
        }

        return m_codingView;
    }
}