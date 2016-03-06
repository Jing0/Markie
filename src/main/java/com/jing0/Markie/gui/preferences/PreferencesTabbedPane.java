package com.jing0.Markie.gui.preferences;

import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.io.IOException;

/**
 * @author Jackie
 * @date 1/25/16
 */
public class PreferencesTabbedPane extends JTabbedPane {

	public PreferencesTabbedPane() {
        ImageIcon generalIcon = null;
        ImageIcon editorIcon = null;
        ImageIcon cssIcon = null;
        try {
            generalIcon = new ImageIcon(IOUtils.toByteArray(getClass().getResourceAsStream("/res/icon/PreferencesGeneral.png")));
            editorIcon = new ImageIcon(IOUtils.toByteArray(getClass().getResource("/res/icon/PreferencesEditor.png")));
            cssIcon = new ImageIcon(IOUtils.toByteArray(getClass().getResource("/res/icon/PreferencesRendering.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        GeneralPreferencesPanel generalPanel = new GeneralPreferencesPanel();
        EditorPreferencesPanel editorPanel = new EditorPreferencesPanel();
        CssPreferencesPanel cssPanel = new CssPreferencesPanel();

        this.addTab("General", generalIcon, generalPanel, "General");
        this.addTab("CSS", cssIcon, cssPanel, "CSS");
        this.addTab("Editor", editorIcon, editorPanel, "Editor");
    }
}
