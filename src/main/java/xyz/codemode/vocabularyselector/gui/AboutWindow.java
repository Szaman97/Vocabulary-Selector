package xyz.codemode.vocabularyselector.gui;

import xyz.codemode.vocabularyselector.core.VocabularySelector;

import javax.swing.*;
import java.awt.*;

public class AboutWindow extends JDialog {
    public AboutWindow() {
        super(VocabularySelector.getWindow());
        init();
        setVisible(true);
    }

    private void init() {
        setSize(new Dimension(275,220));
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("About");
        setResizable(false);
        setModal(true);

        JLabel title = new JLabel("Vocabulary Selector");
        title.setFont(new Font("LetItBeDefault",Font.BOLD,24));
        title.setBounds(20,10,230,25);
        add(title);

        String versionNumber = getClass().getPackage().getImplementationVersion();  //works fine with JAR
        JLabel version = new JLabel("v" + versionNumber);
        version.setFont(new Font("LetItBeDefault",Font.PLAIN,24));
        version.setBounds(105,40,100,25);
        add(version);

        JLabel author = new JLabel("Copyright © 2018 Michał Głogowski");
        JLabel github = new JLabel("https://github.com/Szaman97");
        JLabel license = new JLabel("License: MIT");
        author.setBounds(10,75,230,25);
        github.setBounds(10,95,200,25);
        license.setBounds(10,115,200,25);
        add(author);
        add(github);
        add(license);

        JButton ok = new JButton("OK");
        ok.setBounds(100,150,75,25);
        ok.addActionListener(event -> dispose());
        add(ok);
    }
}
