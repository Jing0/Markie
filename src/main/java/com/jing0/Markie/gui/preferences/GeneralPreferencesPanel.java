package com.jing0.Markie.gui.preferences;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Jackie
 * @date 1/25/16
 */
public class GeneralPreferencesPanel extends JPanel implements PreferencesPanel {

    private final MigLayout layout = new MigLayout(
            "insets dialog, flowy, align center, aligny top",
            "[align center, fill]",
            "[align center][align center]"
    );
    private JCheckBox infoBarCheckBox = new JCheckBox();
    private JCheckBox previewCheckBox = new JCheckBox();
    private JCheckBox syncScrollCheckBox = new JCheckBox();
    private JCheckBox mathCheckBox = new JCheckBox();
    private JCheckBox highlightCheckBox = new JCheckBox();
    private PreferencesManager preferencesManager = new PreferencesManager(PreferencesManager.GENERAL_NODE);


    public GeneralPreferencesPanel() {
        initComponents();
        loadPreferences();

        this.setLayout(layout);
        this.add(infoBarCheckBox);
        this.add(previewCheckBox);
        this.add(syncScrollCheckBox);
        this.add(mathCheckBox);
        this.add(highlightCheckBox);

    }

    public void initComponents() {
        infoBarCheckBox.setText("Show Info Bar");
        infoBarCheckBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                preferencesManager.putBooleanInNode(PreferencesManager.SHOW_INFO_BAR,
                        infoBarCheckBox.isSelected());
            }
        });
        previewCheckBox.setText("Show Preview");
        previewCheckBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                preferencesManager.putBooleanInNode(PreferencesManager.SHOW_PREVIEW,
                        previewCheckBox.isSelected());
            }
        });
        syncScrollCheckBox.setText("Enable Sync Scroll");
        syncScrollCheckBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                preferencesManager.putBooleanInNode(PreferencesManager.ENABLE_SYNC_SCROLL,
                        syncScrollCheckBox.isSelected());
            }
        });
        mathCheckBox.setText("Enable Math");
        mathCheckBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                preferencesManager.putBooleanInNode(PreferencesManager.ENABLE_MATH,
                        mathCheckBox.isSelected());
            }
        });
        highlightCheckBox.setText("Enable Syntax Highlight");
        highlightCheckBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                preferencesManager.putBooleanInNode(PreferencesManager.ENABLE_SYNTAX_HIGHLIGHT,
                        highlightCheckBox.isSelected());
            }
        });
    }

    public void loadPreferences() {
        boolean showInfoBar = preferencesManager.getBooleanInNode(PreferencesManager.SHOW_INFO_BAR, true);
        boolean showPreview = preferencesManager.getBooleanInNode(PreferencesManager.SHOW_PREVIEW, true);
        boolean syncScroll = preferencesManager.getBooleanInNode(PreferencesManager.ENABLE_SYNC_SCROLL, true);
        boolean enableMath = preferencesManager.getBooleanInNode(PreferencesManager.ENABLE_MATH, false);
        boolean highlight = preferencesManager.getBooleanInNode(PreferencesManager.ENABLE_SYNTAX_HIGHLIGHT, false);
        infoBarCheckBox.setSelected(showInfoBar);
        previewCheckBox.setSelected(showPreview);
        syncScrollCheckBox.setSelected(syncScroll);
        mathCheckBox.setSelected(enableMath);
        highlightCheckBox.setSelected(highlight);
    }
}
