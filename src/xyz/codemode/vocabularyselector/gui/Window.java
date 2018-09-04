package xyz.codemode.vocabularyselector.gui;

import xyz.codemode.vocabularyselector.core.Config;
import xyz.codemode.vocabularyselector.gui.listeners.MyWindowListener;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public Window() {
        super("Vocabulary Selector");
        init();
        setVisible(true);
    }

    private void init() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new MyWindowListener());
        setBounds(Config.getWindowX(), Config.getWindowY(), Config.getWindowWidth(), Config.getWindowHeight());
        setExtendedState(Config.getWindowState());
        setMinimumSize(new Dimension(640,480));
        setLayout(new BorderLayout());

        statusBar = new StatusBar();
        add(statusBar, BorderLayout.PAGE_END);

        add(new MenuBar(), BorderLayout.PAGE_START);

        tabbedTextPanels = new TabbedTextPanels();

        int fontSize = Config.getFontSize();
        tabbedTextPanels.setTextPanelsFontSize(fontSize);

        try {
            String colorName = Config.getFontColor();
            Color color = (Color)Color.class.getField(colorName).get(null);
            tabbedTextPanels.setTextPanelsFontColor(color);
        } catch (Exception e) {
            statusBar.setText("Color from config.json is incorrect! Setting default black color for text.");
            tabbedTextPanels.setTextPanelsFontColor(Color.BLACK);
        }

        add(tabbedTextPanels);

        controlPanel = new ControlPanel();
        add(controlPanel, BorderLayout.LINE_END);
        statusBar.setText("Application is ready");
    }

    public StatusBar getStatusBar() { return statusBar; }
    public TabbedTextPanels getTabbedTextPanels() { return tabbedTextPanels; }
    public ControlPanel getControlPanel() { return controlPanel; }

    private StatusBar statusBar;
    private TabbedTextPanels tabbedTextPanels;
    private ControlPanel controlPanel;
}
