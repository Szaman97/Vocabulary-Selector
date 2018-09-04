package xyz.codemode.vocabularyselector.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class TextPanel extends JPanel {
    public TextPanel() {
        super();
        init();
    }

    public void setText(String text) {
        try {
            StyledDocument doc = textPane.getStyledDocument();
            doc.remove(0, doc.getLength());
            doc.insertString(doc.getLength(), text, set);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void setFontSize(int fontSize) {
        try {
            StyledDocument doc = textPane.getStyledDocument();
            StyleConstants.setFontSize(set, fontSize);
            String content = textPane.getText();
            doc.remove(0, doc.getLength());
            doc.insertString(doc.getLength(), content, set);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void setFontColor(Color color) {
        try {
            StyledDocument doc = textPane.getStyledDocument();
            StyleConstants.setForeground(set, color);
            String content = textPane.getText();
            doc.remove(0, doc.getLength());
            doc.insertString(doc.getLength(), content, set);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5,5,5,5));
        textPane.setEditable(false);
        scrollPane = new JScrollPane(textPane);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

    }

    private JScrollPane scrollPane;
    private JTextPane textPane = new JTextPane();
    private SimpleAttributeSet set = new SimpleAttributeSet();
}
