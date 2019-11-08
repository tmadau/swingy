package com.swingy.view.gui;

import com.swingy.utilities.util.Utilities;
import javax.swing.JTextArea;
import java.awt.Color;

@SuppressWarnings("serial")
public class GUIGameLog extends JTextArea {

    public GUIGameLog() {
        setLayout(null);
        setAutoscrolls(true);
        setBackground(Color.gray);
        setForeground(new Color(124, 252, 0));
        setEditable(false);
        setFocusable(false);
        setFont(Utilities.smallFont);
    }

    @Override
    public void append(String str) {
        super.append(str);
        setRows(getRows() + 1);
    }

}
