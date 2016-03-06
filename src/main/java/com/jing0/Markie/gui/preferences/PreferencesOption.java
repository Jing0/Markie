package com.jing0.Markie.gui.preferences;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * @author Jackie
 * @date 1/26/16
 */
public class PreferencesOption extends JPanel {

	protected JComponent option;
    private MigLayout optionLayout = new MigLayout(
            "insets 0 0 0 0",
            "[align right, 50%]2[align left, 50%]",
            "[align center]"
    );

    public PreferencesOption(String optionName, JComponent option) {
        this.setLayout(optionLayout);
        JLabel label = new JLabel();
        label.setText(optionName);
        this.option = option;
        this.add(label);
        this.add(option);
    }
}
