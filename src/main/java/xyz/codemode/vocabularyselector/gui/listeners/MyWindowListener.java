package xyz.codemode.vocabularyselector.gui.listeners;

import xyz.codemode.vocabularyselector.core.Config;
import xyz.codemode.vocabularyselector.core.VocabularySelector;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;

public class MyWindowListener extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent we) {
        int fontSize = VocabularySelector.getWindow().getControlPanel().getFontSize();
        String fontColor = VocabularySelector.getWindow().getControlPanel().getFontColor();
        int width = VocabularySelector.getWindow().getWidth();
        int height = VocabularySelector.getWindow().getHeight();
        int x = VocabularySelector.getWindow().getX();
        int y = VocabularySelector.getWindow().getY();
        int state = VocabularySelector.getWindow().getExtendedState();

        ExecutorService exec = VocabularySelector.getManager().getExecutorService();
        exec.execute(() -> {
                int configFontSize = Config.getFontSize();
                String configFontColor = Config.getFontColor();
                int configWidth = Config.getWindowWidth();
                int configHeight = Config.getWindowHeight();
                int configX = Config.getWindowX();
                int configY = Config.getWindowY();
                int configState = Config.getWindowState();

                boolean condition = (configFontSize!=fontSize) || (!configFontColor.equals(fontColor))
                                    || (configWidth!=width) || (configHeight!=height) || (configX!=x)
                                    || (configY!=y) || (configState!=state);

                if(condition) {
                    Config.setFontSize(fontSize);
                    Config.setFontColor(fontColor);
                    if(state != Frame.MAXIMIZED_BOTH) {
                        Config.setWindowWidth(width);
                        Config.setWindowHeight(height);
                        Config.setWindowX(x);
                        Config.setWindowY(y);
                    }
                    Config.setWindowState(state);
                    Config.saveConfig();
                }
            });

        VocabularySelector.getWindow().dispose();
        exec.shutdown();
    }
}
