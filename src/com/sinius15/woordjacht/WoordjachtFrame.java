package com.sinius15.woordjacht;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

/**
 * Created by Sinius on 9-5-2015.
 * The frame to display stuff
 */
public class WoordjachtFrame extends JFrame implements DocumentListener, FocusListener, ActionListener {

    private JTextField[][] fields;
    private WoordJachtFixer fixer;
    private final int rows;
    private final int cols;
    private final JList<String> found;

    public WoordjachtFrame(int rows, int cols, WoordJachtFixer fixer){
        this.fixer = fixer;
        this.rows = rows;
        this.cols = cols;

        fields = new JTextField[rows][cols];
        setTitle("Woordjacht Fixer");
        setLayout(null);

        for(int row = 0; row < rows; row++){
            for(int col = 0; col< cols; col++){
                JTextField field = new JTextField();
                add(field, null);
                field.setBounds(row * 50+10, col * 50 + 10, 40, 40);
                field.getDocument().addDocumentListener(this);
                field.addFocusListener(this);
                fields[row][col] = field;
            }
        }
        JButton button = new JButton("Zoek!");
        button.addActionListener(this);
        button.setBounds(10, 220, 190, 40);
        add(button);

        found = new JList<>();
        found.setBounds(10, 270, 190, 580);
        JScrollPane scrollPane = new JScrollPane(found);
        scrollPane.setBounds(10, 270, 190, 580);
        add(scrollPane);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(230, 900);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.focusNextComponent();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    @Override
    public void focusGained(FocusEvent e) {
        if(e.getComponent() instanceof JTextField)
            ((JTextField) e.getComponent()).setText("");
    }

    @Override
    public void focusLost(FocusEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        char[][] matrix = new char[rows][cols];

        for(int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JTextField field = fields[row][col];
                matrix[row][col] = field.getText().charAt(0);
            }
        }

        ArrayList<WordMatch> result = fixer.doSearch(matrix);
        found.setModel(new ListModel<String>() {
            @Override
            public int getSize() {
                return result.size();
            }

            @Override
            public String getElementAt(int index) {
                return result.get(index).getWord();
            }

            @Override
            public void addListDataListener(ListDataListener l) {
            }

            @Override
            public void removeListDataListener(ListDataListener l) {
            }
        });
    }
}
