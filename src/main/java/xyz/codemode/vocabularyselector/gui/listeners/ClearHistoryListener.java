package xyz.codemode.vocabularyselector.gui.listeners;

import xyz.codemode.vocabularyselector.core.VocabularySelector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ClearHistoryListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        File files[] = {new File("./cfg/lastDrawnVocabulary-custom.txt"), new File("./cfg/drawn-custom.json"),
                new File("./cfg/lastDrawnVocabulary.txt"), new File("./cfg/drawn.json")};

        int choice = JOptionPane.showConfirmDialog(null, "Delete all info about drawn vocabulary?",
                "Confirm", JOptionPane.YES_NO_OPTION);

        if(choice == JOptionPane.YES_OPTION) {
            for(File f : files) {
                f.delete();
            }

            VocabularySelector.getManager().reloadVocabularyFromFile();
        }
    }
}
