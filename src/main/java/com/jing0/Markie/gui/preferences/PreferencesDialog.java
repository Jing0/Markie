package com.jing0.Markie.gui.preferences;

import com.jing0.Markie.gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Jackie
 * @date 1/24/16
 */
public class PreferencesDialog extends JDialog {

	public PreferencesDialog(final MainFrame frame) {
        this.setTitle("Preferences");
        this.setMinimumSize(new Dimension(390, 390));
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.add(new PreferencesTabbedPane());

        addEscapeListener();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                frame.loadAllConfigs();
            }

        });
        this.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                frame.loadAllConfigs();
            }
        });
    }

    public void addEscapeListener() {
        ActionListener escListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PreferencesDialog.this.dispose();
            }
        };

        this.getRootPane().registerKeyboardAction(escListener,
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
}
