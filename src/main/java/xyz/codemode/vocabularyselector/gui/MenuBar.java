package xyz.codemode.vocabularyselector.gui;

import xyz.codemode.vocabularyselector.core.VocabularySelector;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MenuBar extends JMenuBar {
    public MenuBar() {
        super();
        build();
    }

    private String[] menuTitles = { "File", "Help" };
    private String[] fileMenuTitles = { "Settings", "Exit" };
    private String[] helpMenuTitles = { "About" };

    private JMenu createJMenu(String title, int keyEvent) {
        JMenu jMenu = new JMenu(title);
        jMenu.setMnemonic(keyEvent);
        return jMenu;
    }

    private JMenuItem createJMenuItem(String title, int keyEvent, ActionListener al) {
        JMenuItem jMenuItem;
        jMenuItem = new JMenuItem(title);
        jMenuItem.setMnemonic(keyEvent);
        jMenuItem.addActionListener(al);
        return jMenuItem;
    }

    private void build() {
        JMenu file = createJMenu(menuTitles[0], KeyEvent.VK_F);
        JMenu help = createJMenu(menuTitles[1], KeyEvent.VK_H);

        file.add(createJMenuItem(fileMenuTitles[0],KeyEvent.VK_S,event -> new SettingsWindow()));
        file.add(createJMenuItem(fileMenuTitles[1],KeyEvent.VK_X,event -> VocabularySelector.getWindow().getWindowListeners()[0].windowClosing(null)));
        help.add(createJMenuItem(helpMenuTitles[0],KeyEvent.VK_A,event -> new AboutWindow()));

        add(file);
        add(help);
    }
}
