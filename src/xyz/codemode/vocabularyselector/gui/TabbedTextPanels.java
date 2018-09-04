package xyz.codemode.vocabularyselector.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TabbedTextPanels extends JPanel {
    public TabbedTextPanels() {
        super();
        init();
    }

    public void setTextPanelsFontSize(int fontSize) {
        lastDrawnPanel.setFontSize(fontSize);
        allDrawnPanel.setFontSize(fontSize);
        allVocabularyPanel.setFontSize(fontSize);
    }

    public void setTextPanelsFontColor(Color color) {
        lastDrawnPanel.setFontColor(color);
        allDrawnPanel.setFontColor(color);
        allVocabularyPanel.setFontColor(color);
    }

    private void init() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5,5,5,5));

        tabbedPane.addTab("Last Drawn Vocabulary", lastDrawnPanel);
        tabbedPane.addTab("All Drawn Vocabulary", allDrawnPanel);
        tabbedPane.addTab("All Vocabulary", allVocabularyPanel);
        add(tabbedPane);
    }

    public TextPanel getLastDrawnPanel() { return lastDrawnPanel; }
    public TextPanel getAllDrawnPanel() { return allDrawnPanel; }
    public TextPanel getAllVocabularyPanel() { return allVocabularyPanel; }

    private JTabbedPane tabbedPane = new JTabbedPane();
    private TextPanel lastDrawnPanel = new TextPanel();
    private TextPanel allDrawnPanel = new TextPanel();
    private TextPanel allVocabularyPanel = new TextPanel();
}
