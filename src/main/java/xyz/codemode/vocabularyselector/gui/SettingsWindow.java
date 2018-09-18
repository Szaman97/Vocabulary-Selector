package xyz.codemode.vocabularyselector.gui;

import xyz.codemode.vocabularyselector.core.Config;
import xyz.codemode.vocabularyselector.core.VocabularySelector;
import xyz.codemode.vocabularyselector.gui.listeners.ClearHistoryListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.text.NumberFormat;
import java.util.concurrent.ExecutorService;

public class SettingsWindow extends JDialog {
    public SettingsWindow() {
        super(VocabularySelector.getWindow());
        init();
        setVisible(true);
    }

    private void saveSettings() {
        VocabularySelector.getWindow().getStatusBar().setText("Settings has been saved. You may need to restart the app to apply changes.");
        ExecutorService exec = VocabularySelector.getManager().getExecutorService();
        exec.execute(() -> {
            Object[] systemButton = systemUI.getSelectedObjects();
            boolean systemUISet;

            //Check if systemUI JRadioButton is pressed
            try { systemUISet = systemButton[0] != null; }
            catch (NullPointerException e) { systemUISet = false; }

            Config.setGeneratorInitValue((int)field.getValue());
            Config.setWordsToDraw((int)wordsToDraw.getValue());
            Config.setDaysBetweenDraws((int)days.getValue());
            Config.setSystemUI(systemUISet);

            //Check if userSelected JRadioButton is pressed
            boolean userSelectedSet;
            try { userSelectedSet = customVocabulary.getSelectedObjects()[0] != null; }
            catch (NullPointerException e) { userSelectedSet = false; }

            Config.setCustomVocabulary(userSelectedSet);
            if(userSelectedSet)
                Config.setVocabularyPath(filePath);

            Config.saveConfig();
        });
    }

    private void init() {
        setModal(true);
        setLayout(new FlowLayout());
        setSize(new Dimension(230,400));
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Settings");

        JPanel generatorSettings = generatorSettings();
        generatorSettings.setPreferredSize(new Dimension(210,130));
        add(generatorSettings);

        JPanel vocabularySettings = vocabularySettings();
        vocabularySettings.setPreferredSize(new Dimension(210, 100));
        add(vocabularySettings);

        JPanel viewSettings = viewSettings();
        viewSettings.setPreferredSize(new Dimension(210,80));
        add(viewSettings);

        JPanel buttonPanel = buttonPanel();
        buttonPanel.setPreferredSize(new Dimension(210, 80));
        add(buttonPanel);

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

    private JPanel vocabularySettings() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        TitledBorder border = new TitledBorder("VOCABULARY SETTINGS");
        border.setTitleFont(borderFont);
        panel.setBorder(border);

        filePath = Config.getVocabularyPath();
        customVocabulary = new JRadioButton("Custom vocabulary", Config.getCustomVocabulary());
        customVocabulary.addActionListener(event -> {
            boolean selected;
            try { selected = customVocabulary.getSelectedObjects()[0] != null; }
            catch (NullPointerException e) { selected = false; }
            fileChoose.setEnabled(selected);
        });
        panel.add(customVocabulary);

        JPanel subPanel = new JPanel();

        fileChoose = new JButton("Select file");
        fileChoose.setPreferredSize(new Dimension(80,25));
        fileChoose.addActionListener(new FileChooseListener());
        fileChoose.setEnabled(Config.getCustomVocabulary());
        subPanel.add(fileChoose);

        JButton clearHistory = new JButton("Clear history");
        clearHistory.setPreferredSize(new Dimension(100,25));
        clearHistory.addActionListener(new ClearHistoryListener());
        subPanel.add(clearHistory);

        panel.add(subPanel);

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
        buttonGroup.add(systemUI);
        panel.add(systemUI);

        javaUI = new JRadioButton("Java UI", !Config.getSystemUI());
        buttonGroup.add(javaUI);
        panel.add(javaUI);

        return panel;
    }

    private JPanel buttonPanel() {
        JPanel panel = new JPanel();

        JButton cancel = new JButton("Cancel");
        cancel.setPreferredSize(new Dimension(75,25));
        cancel.addActionListener(event -> dispose());
        panel.add(cancel);

        JButton ok = new JButton("OK");
        ok.setPreferredSize(new Dimension(75,25));
        ok.addActionListener(event -> {
            saveSettings();
            dispose();
        });
        panel.add(ok);

        return panel;
    }

    private Font borderFont = new Font("LetItBeDefault", Font.BOLD, 12);
    private JFormattedTextField field;
    private JSpinner wordsToDraw;
    private JSpinner days;
    private JRadioButton systemUI;
    private JRadioButton javaUI;
    private JRadioButton customVocabulary;
    private String filePath;
    private JButton fileChoose;

    private class FileChooseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(false);
            fileChooser.setApproveButtonToolTipText("Select file with vocabulary for the program");
            fileChooser.setSelectedFile(new File(filePath));

            fileChooser.showDialog(VocabularySelector.getWindow(), "Select");
            try {
                filePath = fileChooser.getSelectedFile().toString();
            } catch (NullPointerException ex) {}
        }
    }
}
