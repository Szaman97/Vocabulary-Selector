package xyz.codemode.vocabularyselector.core;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Manager {
    public Manager() {
        Config.init();
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
            //TODO: status bar info; has to be synchronized - status bar does not exist in that moment
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

    public ExecutorService getExecutorService() { return exec; }

    private Vocabulary vocabulary;
    private ExecutorService exec = Executors.newSingleThreadExecutor();

}
