package com.jing0.Markie.gui.preferences;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;
import java.util.Vector;

/**
 * @author Jackie
 * @date 1/26/16
 */
public class EditorPreferencesPanel extends JPanel implements PreferencesPanel {

    private final MigLayout layout = new MigLayout(
            "insets dialog, flowy, align center, aligny top",
            "[align center][align center]",
            "[][][][]"
    );
    private JComboBox font;
    private JComboBox fontSize;
    private JTextField tabSize;
    private PreferencesManager preferencesManager = new PreferencesManager(PreferencesManager.EDITOR_NODE);

    public EditorPreferencesPanel() {
        initComponents();
        loadPreferences();

        this.setLayout(layout);

        String[] labelArray = {"Font:", "Font Size:", "Tab Size:"};
        Hashtable<String, Object> optionTable = new Hashtable<String, Object>();
        optionTable.put("Font:", font);
        optionTable.put("Font Size:", fontSize);
        optionTable.put("Tab Size:", tabSize);

        for (String text : labelArray) {
            PreferencesOption option = new PreferencesOption(text, (JComponent) optionTable.get(text));
            this.add(option);
        }
    }

    public void initComponents() {
        String[] fontArray = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        font = new JComboBox<String>(fontArray);
        font.setMaximumSize(new Dimension(130, 20));
        font.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preferencesManager.putStringInNode(PreferencesManager.EDITOR_FONT,
                        (String) font.getSelectedItem());
            }
        });

        Vector<Integer> fontSizeList = new Vector<Integer>();
        for (int i = 6; i < 49; ++i) {
            fontSizeList.add(i);
        }
        fontSize = new JComboBox<Integer>(fontSizeList);
        fontSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preferencesManager.putIntInNode(PreferencesManager.EDITOR_FONT_SIZE,
                        (Integer) fontSize.getSelectedItem());
            }
        });

        tabSize = new JTextField(20);
        tabSize.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if (keyChar < KeyEvent.VK_0 || keyChar > KeyEvent.VK_9) {
                    e.consume();
                }
            }
        });
        tabSize.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                preferencesManager.putIntInNode(PreferencesManager.TAB_SIZE,
                        Integer.parseInt(tabSize.getText()));
            }
        });
    }

    public void loadPreferences() {
        String fontSetting = preferencesManager.getStringInNode(PreferencesManager.EDITOR_FONT, "Monaco");
        font.setSelectedItem(fontSetting);
        Integer fontSizeSetting = preferencesManager.getIntInNode(PreferencesManager.EDITOR_FONT_SIZE, 16);
        fontSize.setSelectedItem(fontSizeSetting);
        String tabSizeSetting = String.valueOf(preferencesManager.getIntInNode(PreferencesManager.TAB_SIZE, 4));
        tabSize.setText(tabSizeSetting);
    }

}
