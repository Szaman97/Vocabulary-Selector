package xyz.codemode.vocabularyselector.core;

import xyz.codemode.vocabularyselector.gui.Window;

import javax.swing.*;

public class VocabularySelector {
    public static void main(String... args) {
        Config.init();

        if(Config.getSystemUI()) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }

        manager = new Manager();

        SwingUtilities.invokeLater(() -> window = new Window());

        manager.displayVocabulary();
    }

    public static Window getWindow() { return window; }
    public static Manager getManager() { return manager; }

    private static Manager manager;
    private static Window window;
}
