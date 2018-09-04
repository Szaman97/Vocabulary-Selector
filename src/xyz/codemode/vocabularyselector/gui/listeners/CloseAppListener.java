package xyz.codemode.vocabularyselector.gui.listeners;

import xyz.codemode.vocabularyselector.core.VocabularySelector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseAppListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        VocabularySelector.getWindow().getWindowListeners()[0].windowClosing(null);
    }
}
