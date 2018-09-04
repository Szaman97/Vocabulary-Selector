package xyz.codemode.vocabularyselector.gui.listeners;

import xyz.codemode.vocabularyselector.core.VocabularySelector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;

public class DrawNowListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        int choice = JOptionPane.showConfirmDialog(VocabularySelector.getWindow(),
                "Do you really want to draw new vocabulary?\nTime for next draw won't change.",
                "Are you sure?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(choice==JOptionPane.YES_OPTION) {
            VocabularySelector.getWindow().getStatusBar().setText("New vocabulary has been drawn");
            ExecutorService exec = VocabularySelector.getManager().getExecutorService();
            exec.execute(() -> {
                VocabularySelector.getManager().drawVocabularyUserRequested();
                VocabularySelector.getManager().displayVocabulary();
            });
        }
    }
}
