package com.jing0.Markie.gui.preferences;

import com.jing0.Markie.Markie;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * @author Jackie
 * @date 2/3/16
 */
public final class PreferencesManager {

    public static String GENERAL_NODE = "general";
    public static String CSS_NODE = "css";
    public static String EDITOR_NODE = "editor";
    public static String FIRST_OPENING = "FirstOpening";
    public static String SHOW_INFO_BAR = "ShowInfoBar";
    public static String SHOW_PREVIEW = "ShowPreview";
    public static String ENABLE_SYNC_SCROLL = "EnableSyncScroll";
    public static String ENABLE_MATH = "EnableMath";
    public static String ENABLE_SYNTAX_HIGHLIGHT = "EnableSyntaxHighlight";
    public static String CSS = "Css";
    public static String EDITOR_FONT = "EditorFont";
    public static String EDITOR_FONT_SIZE = "EditorFontSize";
    public static String TAB_SIZE = "TabSize";
    private Preferences rootPreferences = Preferences.userNodeForPackage(Markie.class);
    private String defaultPreferencesPath = "/res/xml/DefaultPreferences.xml";
    private Preferences nodePreferences;

    public PreferencesManager(String node) {
        nodePreferences = rootPreferences.node(node);
    }

    public static boolean isFirstOpening() {
        Preferences rootPreferences = Preferences.userNodeForPackage(Markie.class);
        boolean isFirstOpening = rootPreferences.getBoolean(FIRST_OPENING, true);
        rootPreferences.putBoolean(FIRST_OPENING, false);
        return isFirstOpening;
    }

    public boolean getBooleanInNode(String key, Boolean defaultValue) {
        return nodePreferences.getBoolean(key, defaultValue);
    }

    public byte[] getByteArrayInNode(String key, byte[] defaultValue) {
        return nodePreferences.getByteArray(key, defaultValue);
    }

    public double getDoubleInNode(String key, double defaultValue) {
        return nodePreferences.getDouble(key, defaultValue);
    }

    public float getFloatInNode(String key, float defaultValue) {
        return nodePreferences.getFloat(key, defaultValue);
    }

    public int getIntInNode(String key, int defaultValue) {
        return nodePreferences.getInt(key, defaultValue);
    }

    public long getLongInNode(String key, long defaultValue) {
        return nodePreferences.getLong(key, defaultValue);
    }

    public String getStringInNode(String key, String defaultValue) {
        return nodePreferences.get(key, defaultValue);
    }

    public void putBooleanInNode(String key, Boolean value) {
        nodePreferences.putBoolean(key, value);
    }

    public void putByteArrayInNode(String key, byte[] value) {
        nodePreferences.putByteArray(key, value);
    }

    public void putDoubleInNode(String key, double value) {
        nodePreferences.putDouble(key, value);
    }

    public void putFloatInNode(String key, float value) {
        nodePreferences.putFloat(key, value);
    }

    public void putIntInNode(String key, int value) {
        nodePreferences.putInt(key, value);
    }

    public void putLongInNode(String key, long value) {
        nodePreferences.putLong(key, value);
    }

    public void putStringInNode(String key, String value) {
        nodePreferences.put(key, value);
    }

    public static void exportDefaultPreferences() {
        Preferences rootPreferences = Preferences.userNodeForPackage(Markie.class);

        rootPreferences.putBoolean(FIRST_OPENING, true);

        Preferences generalPreferences = rootPreferences.node(GENERAL_NODE);
        generalPreferences.putBoolean(SHOW_INFO_BAR, true);
        generalPreferences.putBoolean(SHOW_PREVIEW, true);

        Preferences cssPreferences = rootPreferences.node(CSS_NODE);
        cssPreferences.put(CSS, "GitHub2");

        Preferences editorPreferences = rootPreferences.node(EDITOR_NODE);
        editorPreferences.put(EDITOR_FONT, "Monaco");
        editorPreferences.putInt(EDITOR_FONT_SIZE, 16);
        editorPreferences.putInt(TAB_SIZE, 4);
        try {
            // TODO export JFileChooser
            rootPreferences.exportSubtree(new FileOutputStream("/users/Jackie/preferences.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }


}
