package xyz.codemode.vocabularyselector.core;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Manager {
    public Manager() {
        vocabulary = new Vocabulary();
        drawVocabularyWhenTimeIsRight();
    }

    /**
     * Use when app is starting
     */
    private void drawVocabularyWhenTimeIsRight() {
        if(CalendarModule.canDrawVocabulary()) {
            CalendarModule.increaseNextDrawDate(Config.getDaysBetweenDraws());
            vocabulary.drawVocabulary(Config.getWordsToDraw(),Config.getGeneratorInitValue());
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "New vocabulary has been drawn", "New vocabulary", JOptionPane.INFORMATION_MESSAGE));
        }
    }

    /**
     * Use in action listener - requested by user
     */
    public void drawVocabularyUserRequested() { vocabulary.drawVocabulary(Config.getWordsToDraw(),Config.getGeneratorInitValue()); }

    public void displayVocabulary() {
        SwingUtilities.invokeLater(() -> {
            VocabularySelector.getWindow().getTabbedTextPanels().getAllVocabularyPanel().setText(vocabulary.getAllVocabulary());
            VocabularySelector.getWindow().getTabbedTextPanels().getLastDrawnPanel().setText(vocabulary.getLastDrawnVocabulary());
            VocabularySelector.getWindow().getTabbedTextPanels().getAllDrawnPanel().setText(vocabulary.getAllDrawnVocabulary());
            VocabularySelector.getWindow().getStatusBar().setText("Vocabulary loaded");
        });
    }

    /**
     * Reloads vocabulary from files in external thread, draws new set and displays it in window
     */
    public void reloadVocabularyFromFile() {
        exec.submit(() -> {
            vocabulary.loadFromFiles();
            drawVocabularyUserRequested();
            displayVocabulary();
        });
    }

    public ExecutorService getExecutorService() { return exec; }

    private Vocabulary vocabulary;
    private ExecutorService exec = Executors.newSingleThreadExecutor();

}
