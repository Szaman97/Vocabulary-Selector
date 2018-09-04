package xyz.codemode.vocabularyselector.gui.models;

import javax.swing.*;

public class FontSizeComboBoxModel extends DefaultComboBoxModel<Integer> {
    private int[] sizes = {12,13,14,15,16,18,20,22,24,26,28,32,36,40,44,48,54,60,66,72,80,88,96};

    @Override
    public Integer getElementAt(int index) { return sizes[index]; }

    @Override
    public int getSize() { return sizes.length; }
}
