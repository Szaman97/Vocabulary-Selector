package xyz.codemode.vocabularyselector.gui;

import xyz.codemode.vocabularyselector.core.Config;
import xyz.codemode.vocabularyselector.core.VocabularySelector;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.concurrent.ExecutorService;

public class SettingsWindow extends JDialog {
    public SettingsWindow() {
        super(VocabularySelector.getWindow());
        init();
        setVisible(true);
    }

    private void saveSettings() {
        VocabularySelector.getWindow().getStatusBar().setText("Settings has been saved");
        ExecutorService exec = VocabularySelector.getManager().getExecutorService();
        exec.execute(() -> {
            Object[] systemButton = systemUI.getSelectedObjects();
            boolean systemUISet;

            try { systemUISet = systemButton[0] != null; }
            catch (NullPointerException e) { systemUISet = false; }

            Config.setGeneratorInitValue((int)field.getValue());
            Config.setWordsToDraw((int)wordsToDraw.getValue());
            Config.setDaysBetweenDraws((int)days.getValue());
            Config.setSystemUI(systemUISet);

            Config.saveConfig();
        });
    }

    private void init() {
        setModal(true);
        setLayout(new FlowLayout());
        setSize(new Dimension(230,290));
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Settings");

        JPanel generatorSettings = generatorSettings();
        generatorSettings.setPreferredSize(new Dimension(210,130));
        add(generatorSettings);

        JPanel viewSettings = viewSettings();
        viewSettings.setPreferredSize(new Dimension(210,80));
        add(viewSettings);

        JButton cancel = new JButton("Cancel");
        cancel.setPreferredSize(new Dimension(75,25));
        cancel.addActionListener(event -> dispose());
        add(cancel);

        JButton ok = new JButton("OK");
        ok.setPreferredSize(new Dimension(75,25));
        ok.addActionListener(event -> {
            saveSettings();
            dispose();
        });
        add(ok);
    }

    private JPanel generatorSettings() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        TitledBorder border = new TitledBorder("GENERATOR SETTINGS");
        border.setTitleFont(borderFont);
        panel.setBorder(border);

        JLabel initValueLabel = new JLabel("Generator init value");
        initValueLabel.setBounds(10,30,130,25);
        panel.add(initValueLabel);

        JLabel wordsLabel = new JLabel("Words to draw");
        wordsLabel.setBounds(10,60,130,25);
        panel.add(wordsLabel);

        JLabel daysLabel = new JLabel("Days between draws");
        daysLabel.setBounds(10,90,130,25);
        panel.add(daysLabel);

        NumberFormatter formatter = new NumberFormatter(NumberFormat.getInstance());
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(Integer.MIN_VALUE);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        field = new JFormattedTextField(formatter);
        field.setBounds(115,30,85,25);
        field.setValue(Config.getGeneratorInitValue());
        panel.add(field);

        wordsToDraw = new JSpinner();
        wordsToDraw.setModel(new SpinnerNumberModel(Config.getWordsToDraw(),1,50,1));
        wordsToDraw.setBounds(160,60,40,25);
        panel.add(wordsToDraw);

        days = new JSpinner();
        days.setModel(new SpinnerNumberModel(Config.getDaysBetweenDraws(),1,50,1));
        days.setBounds(160,90,40,25);
        panel.add(days);

        return panel;
    }

    private JPanel viewSettings() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        TitledBorder border = new TitledBorder("VIEW SETTINGS");
        border.setTitleFont(borderFont);
        panel.setBorder(border);

        ButtonGroup buttonGroup = new ButtonGroup();

        systemUI = new JRadioButton("System UI", Config.getSystemUI());
        systemUI.addActionListener(null);
        buttonGroup.add(systemUI);
        panel.add(systemUI);

        javaUI = new JRadioButton("Java UI", !Config.getSystemUI());
        javaUI.addActionListener(null);
        buttonGroup.add(javaUI);
        panel.add(javaUI);

        return panel;
    }

    private Font borderFont = new Font("LetItBeDefault", Font.BOLD, 12);
    private JFormattedTextField field;
    private JSpinner wordsToDraw;
    private JSpinner days;
    private JRadioButton systemUI;
    private JRadioButton javaUI;
}
