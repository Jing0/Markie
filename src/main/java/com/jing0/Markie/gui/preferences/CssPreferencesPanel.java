package com.jing0.Markie.gui.preferences;

import com.jing0.Markie.gui.OsCheck;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

/**
 * @author Jackie
 * @date 1/26/16
 */
public class CssPreferencesPanel extends JPanel implements PreferencesPanel{

    private MigLayout layout = new MigLayout(
            "insets dialog, flowy, align center",
            "[align center, fill]",
            "[align center] 10 [align center, fill, grow]"
    );
    private Vector<String> cssNameList = new Vector<String>();
    private Hashtable<String, String> cssNamePath = new Hashtable<String, String>();
    private JComboBox cssComboBox;
    private JScrollPane cssScroll = new JScrollPane();
    private JTextArea cssTextArea = new JTextArea();
    private PreferencesManager preferencesManager = new PreferencesManager(PreferencesManager.CSS_NODE);

    public CssPreferencesPanel() {
        initComponents();
        loadPreferences();
        PreferencesOption cssOption = new PreferencesOption("Cascading Style Sheets:", cssComboBox);

        this.setLayout(layout);
        this.add(cssOption);
        this.add(cssScroll);
    }

    public void initComponents() {
        getCSSFiles();
        cssComboBox = new JComboBox(cssNameList);
        cssComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCSSTextArea();
                preferencesManager.putStringInNode(PreferencesManager.CSS,
                        (String) cssComboBox.getSelectedItem());
            }
        });

        //TODO custom css file
        cssTextArea.setEditable(false);
        cssTextArea.setTabSize(4);
        cssTextArea.setLineWrap(true);
        cssTextArea.setMinimumSize(new Dimension(200, 200));
        setCSSTextArea();

        cssScroll.getViewport().add(cssTextArea);
    }

    public void loadPreferences() {
        String cssSetting = preferencesManager.getStringInNode(PreferencesManager.CSS, "GitHub2");
        cssComboBox.setSelectedItem(cssSetting);
    }

    public void getCSSFiles() {
        File[] cssFiles;
        String appDataDirectory = OsCheck.getAppDataDirectory();
        cssFiles = new File(appDataDirectory + "/Markie/css/").listFiles();
        String name, path;
        assert cssFiles != null;
        for (File file : cssFiles) {
            path = file.getAbsolutePath();
            name = FilenameUtils.removeExtension(file.getName());
            cssNameList.add(name);
            cssNamePath.put(name, path);
        }
    }

    public String loadCSSContent(String name) throws IOException {
        return FileUtils.readFileToString(new File(cssNamePath.get(name)));
    }

    public void setCSSTextArea() {
        try {
            cssTextArea.setText(loadCSSContent((String) cssComboBox.getSelectedItem()));
        } catch (IOException e) {
            cssTextArea.setText("Error! Cannot load CSS");
            e.printStackTrace();
        }
        cssTextArea.setCaretPosition(0);
    }
}
