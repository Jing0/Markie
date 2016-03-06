package com.jing0.Markie.gui;

import com.jing0.Markie.gui.preferences.PreferencesManager;
import com.jing0.Markie.parser.MarkieProcessor;
import org.apache.commons.io.FileUtils;
import org.pegdown.Extensions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Observable;
import java.util.Observer;


/**
 * Scrolled text area where will be displayed the HTML preview.
 *
 * @author Jing0
 * @since 1.0
 */
public final class PreviewPane extends JScrollPane implements Observer {


    private final JEditorPane preview = new JEditorPane("text/html", null);
    private final JScrollBar verticalScrollBar = this.getVerticalScrollBar();
    private final MarkieProcessor markieProcessor = new MarkieProcessor(Extensions.ALL);
    private String cssContent = "";
    private String inputContent = "";
    private int scrollBarPosition = 0;

    /**
     * Creates the HTML JLabel and sets its vertical alignment as in the top.
     */
    public PreviewPane() {
        preview.setEditable(false);
        this.getViewport().add(preview, null);

        preview.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseReleased(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    JPopupMenu popupMenu = new JPopupMenu();

                    JMenuItem reload = new JMenuItem("reload");
                    reload.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            reload();
                        }
                    });
                    popupMenu.add(reload);
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        loadConfigs();
    }

    public void loadConfigs() {
        PreferencesManager preferencesManager = new PreferencesManager(PreferencesManager.CSS_NODE);
        String cssSetting = preferencesManager.getStringInNode(PreferencesManager.CSS, "GitHub2");
        try {
            cssContent = loadCSSContent(cssSetting);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        reload();
    }

    /**
     * Updates the content of the label by converting the input data to html and setting them to the label.
     * <p>
     * This method will be called by an {@code InputTextArea} observable.
     * </p>
     *
     * @param o    the observable element which will notify this class.
     * @param data a String object containing the input data to be converted into HTML.
     */
    @Override
    public void update(final Observable o, final Object data) {
        if (o instanceof InputTextArea) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    System.out.println("\tinputTextArea changed!");
                    if (!(data instanceof String)) {
                        return;
                    }
                    inputContent = (String) data;
                    reload();
                }
            });
        } else if (o instanceof InputScrollPane) {
            System.out.println("\tinputScrollPane changed!");
            scrollBarPosition = (int) ((Double) data * verticalScrollBar.getMaximum());
            adjustScrollBar();
        }
    }

    public void reload() {
        String regex = "src=\"(?!http:|https:|ftp:|file:)";

        final String htmlContent = String.format(
                "<html><head><style>%s</style></head><body>%s</body></html>",
                cssContent,
                markieProcessor.markdownToHtml(inputContent).replaceAll(regex, "src=\"file:"));
        preview.setText(htmlContent);

        adjustScrollBar();
    }

    public void adjustScrollBar() {
        int currentValue = verticalScrollBar.getValue();
        System.out.println("current value:" + currentValue);
        System.out.println("new value:" + scrollBarPosition);
        if (currentValue != scrollBarPosition) {
            verticalScrollBar.setValue(scrollBarPosition);
        }
    }

    public String loadCSSContent(String name) throws IOException, URISyntaxException {
        String appDataDirectory = OsCheck.getAppDataDirectory();
        return FileUtils.readFileToString(new File(appDataDirectory + "/Markie/css/" + name + ".css"));
    }
}
