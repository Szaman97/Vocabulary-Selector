package xyz.codemode.vocabularyselector.gui.models;

import javax.swing.*;

public class FontColorComboBoxModel extends DefaultComboBoxModel<String> {
    private String[] colors = {"BLACK", "BLUE", "CYAN", "DARK GRAY", "GRAY", "GREEN", "LIGHT_GRAY", "MAGENTA", "ORANGE", "PINK", "RED", "WHITE", "YELLOW"};

    @Override
    public String getElementAt(int index) { return colors[index]; }

    @Override
    public int getSize() { return colors.length; }
}
