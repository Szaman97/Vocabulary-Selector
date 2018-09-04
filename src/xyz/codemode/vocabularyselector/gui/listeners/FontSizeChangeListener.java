package xyz.codemode.vocabularyselector.gui.listeners;

import xyz.codemode.vocabularyselector.core.VocabularySelector;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class FontSizeChangeListener implements ItemListener {
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            int fontSize = (int) e.getItem();
            VocabularySelector.getWindow().getTabbedTextPanels().setTextPanelsFontSize(fontSize);
            VocabularySelector.getWindow().getStatusBar().setText("Changed font size to " + fontSize);
        }
    }
}
