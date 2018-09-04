package xyz.codemode.vocabularyselector.gui.listeners;

import xyz.codemode.vocabularyselector.core.VocabularySelector;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class FontColorChangeListener implements ItemListener {
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            try {
                String fontColor = (String) e.getItem();
                Color color = (Color)Color.class.getField(fontColor).get(null);
                VocabularySelector.getWindow().getTabbedTextPanels().setTextPanelsFontColor(color);
                VocabularySelector.getWindow().getStatusBar().setText("Changed color to " + fontColor);
            } catch (Exception exc) {
                VocabularySelector.getWindow().getStatusBar().setText("Incorrect font color! Setting BLACK");
                VocabularySelector.getWindow().getTabbedTextPanels().setTextPanelsFontColor(Color.BLACK);
            }
        }
    }
}
