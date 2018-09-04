package xyz.codemode.vocabularyselector.gui;

import xyz.codemode.vocabularyselector.core.CalendarModule;
import xyz.codemode.vocabularyselector.core.Config;
import xyz.codemode.vocabularyselector.gui.listeners.DrawNowListener;
import xyz.codemode.vocabularyselector.gui.listeners.FontColorChangeListener;
import xyz.codemode.vocabularyselector.gui.listeners.FontSizeChangeListener;
import xyz.codemode.vocabularyselector.gui.models.FontColorComboBoxModel;
import xyz.codemode.vocabularyselector.gui.models.FontSizeComboBoxModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ControlPanel extends JPanel {
    public ControlPanel() {
        super();
        init();
    }

    public int getFontSize() { return fontSizeComboBox.getItemAt(fontSizeComboBox.getSelectedIndex()); }
    public String getFontColor() { return fontColorComboBox.getItemAt(fontColorComboBox.getSelectedIndex()); }

    private void init() {
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(200,500));

        JPanel fontSettings = fontSettings();
        fontSettings.setPreferredSize(new Dimension(190,90));
        add(fontSettings);

        JPanel nextDrawDate = nextDrawDate();
        nextDrawDate.setPreferredSize(new Dimension(190, 70));
        add(nextDrawDate);

        JButton drawButton = new JButton("DRAW NOW");
        drawButton.setFont(new Font("LetItBeDefault", Font.PLAIN, 18));
        drawButton.addActionListener(new DrawNowListener());
        add(drawButton);
    }

    private JPanel fontSettings() {
        JPanel panel = new JPanel();
        TitledBorder border = new TitledBorder("FONT SETTINGS");
        border.setTitleFont(new Font("LetItBeDefault", Font.BOLD, 12));
        panel.setBorder(border);
        panel.setLayout(null);

        JLabel fontSizeLabel = new JLabel("Font size:");
        fontSizeLabel.setBounds(10,20,100,25);
        panel.add(fontSizeLabel);

        JLabel fontColorLabel = new JLabel("Font color:");
        fontColorLabel.setBounds(10,50,100,25);
        panel.add(fontColorLabel);

        fontSizeComboBox = new JComboBox<>(new FontSizeComboBoxModel());
        fontSizeComboBox.setBounds(135,20,45,25);
        int sizeFromConfig = Config.getFontSize();
        fontSizeComboBox.setSelectedItem(sizeFromConfig);
        fontSizeComboBox.addItemListener(new FontSizeChangeListener());
        panel.add(fontSizeComboBox);

        fontColorComboBox = new JComboBox<>(new FontColorComboBoxModel());
        fontColorComboBox.setBounds(80,50,100,25);
        String colorFromConfig = Config.getFontColor();
        fontColorComboBox.setSelectedItem(colorFromConfig);
        fontColorComboBox.addItemListener(new FontColorChangeListener());
        panel.add(fontColorComboBox);

        return panel;
    }


    private JPanel nextDrawDate() {
        JPanel panel = new JPanel();
        TitledBorder border = new TitledBorder("NEXT DRAW DATE");
        border.setTitleFont(new Font("LetItBeDefault", Font.BOLD, 12));
        panel.setBorder(border);
        panel.setLayout(new FlowLayout());

        JLabel nextDrawDateLabel = new JLabel(CalendarModule.nextDrawDate());
        nextDrawDateLabel.setFont(new Font("LetItBeDefault", Font.PLAIN, 24));
        panel.add(nextDrawDateLabel);

        return panel;
    }

    private JComboBox<Integer> fontSizeComboBox;
    private JComboBox<String> fontColorComboBox;
}
