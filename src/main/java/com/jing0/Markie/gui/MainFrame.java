package com.jing0.Markie.gui;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.jing0.Markie.gui.preferences.PreferencesDialog;
import com.jing0.Markie.gui.preferences.PreferencesManager;
import com.jing0.Markie.parser.MarkieProcessor;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.pegdown.Extensions;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;

/**
 * Provides the main window of the application.
 *
 * @author Jing0
 * @since 1.0
 */
public final class MainFrame extends JFrame {

    private final MigLayout defaultLayout = new MigLayout(
            "insets 0 0 0 0", // Layout constraints
            "[fill, 50%] 0 [fill, 50%]", // Column constraints
            "[grow, fill]" // Row constraints
    );
    private final MigLayout inputLayoutWithInfo = new MigLayout(
            "insets 0 0 0 0",
            "[grow, fill]",
            "[fill, 100%] 0 []"
    );
    private final MigLayout inputLayoutWithoutInfo = new MigLayout(
            "insets 0 0 0 0",
            "[grow, fill]",
            "[grow, fill]"
    );
    private final MigLayout onePanelLayout = new MigLayout(
            "insets 0 0 0 0",
            "[fill, 100%]",
            "[grow, fill]"
    );
    private final JPanel mainPanel = new JPanel();
    private FXPreviewPane previewPanel = new FXPreviewPane();
    private final JPanel inputPanel = new JPanel();
    private final InputScrollPane inputScrollPane = new InputScrollPane();
    private final JScrollPane inputPane = inputScrollPane.get();
    private final InputTextArea inputTextArea = new InputTextArea();
    private final JTextArea textArea = inputTextArea.get();
    private final InfoPanel infoPanel = new InfoPanel();
    private File currentFile;

    /**
     * Creates the main window and makes it visible.
     */
    public MainFrame() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(new Dimension(1024, 633));
        this.setMinimumSize(new Dimension(780, 482));
        this.setTitle("untitled.md - Markie");

        MenuBar menu = new MenuBar(this);
        this.setJMenuBar(menu.get());
        this.getContentPane().add(mainPanel);
        this.setLocationRelativeTo(null); // Center main frame
        this.setVisible(true);

        inputPane.getViewport().add(textArea, null);

        /*PreferencesManager.exportDefaultPreferences();*/

        loadConfigs();
    }

    public void loadAllConfigs() {
        loadConfigs();
        previewPanel.loadConfigs();
        inputTextArea.loadConfigs();
    }

    public void loadConfigs() {
        PreferencesManager preferencesManager = new PreferencesManager(PreferencesManager.GENERAL_NODE);
        boolean infoBarSetting = preferencesManager.getBooleanInNode(PreferencesManager.SHOW_INFO_BAR, true);
        setInfoBar(infoBarSetting);
        boolean previewSetting = preferencesManager.getBooleanInNode(PreferencesManager.SHOW_PREVIEW, true);
        boolean syncScrollSetting = preferencesManager.getBooleanInNode(PreferencesManager.ENABLE_SYNC_SCROLL, true);
        showPreview(previewSetting, syncScrollSetting);
    }

    public void showPreview(boolean showPreview, boolean syncScroll) {
        previewPanel.setVisible(showPreview);
        mainPanel.removeAll();
        if (showPreview) {
            previewPanel = new FXPreviewPane(); // only for fxpreviewPane
            inputTextArea.addObserver(previewPanel);
            if (syncScroll) {
                inputScrollPane.addObserver(previewPanel);
            } else {
                inputScrollPane.deleteObserver(previewPanel);
            }
            mainPanel.setLayout(defaultLayout);
            mainPanel.add(inputPanel);
            mainPanel.add(previewPanel);
        } else {
            inputTextArea.deleteObserver(previewPanel);
            inputScrollPane.deleteObserver(previewPanel);
            mainPanel.setLayout(onePanelLayout);
            mainPanel.add(inputPanel);
        }
        mainPanel.updateUI();
    }

    public void setInfoBar(Boolean withInfo) {
        infoPanel.setVisible(withInfo);
        inputPanel.removeAll();
        if (withInfo) {
            inputTextArea.addObserver(infoPanel);
            inputPanel.setLayout(inputLayoutWithInfo);
            inputPanel.add(inputPane);
            inputPanel.add(infoPanel, "dock south");
        } else {
            inputTextArea.deleteObserver(infoPanel);
            inputPanel.setLayout(inputLayoutWithoutInfo);
            inputPanel.add(inputPane);
        }
        inputPanel.updateUI();
    }

    public void preferences() {
        new PreferencesDialog(this);
    }

    public void newFile() {
        new MainFrame();
    }

    public void open() {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        File selectedFile = FileKit.getSelectedOpenFile(this, FileKit.MD_FILTER);
        if (selectedFile != null) {
            try {
                String fileContent = FileUtils.readFileToString(new File(selectedFile.getAbsolutePath()));
                this.setInputText(fileContent);
                this.setTitle(selectedFile.getName() + " - Markie");
                currentFile = selectedFile;
                inputTextArea.notifyWhenTextChanged();
                setTipText("File loaded", InfoPanel.TIP_LEAST_DELAY);
            } catch (IOException e) {
                e.printStackTrace();
                setTipText("Open failed!", InfoPanel.TIP_SHORT_DELAY);
            }
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void save() {
        setInputCursor(new Cursor(Cursor.WAIT_CURSOR));
        if (currentFile == null) {
            currentFile = FileKit.getSelectedSaveFile(this, FileKit.MD_FILTER, "untitled.md");
            if (currentFile != null) {
                try {
                    FileUtils.writeStringToFile(currentFile, getInputText());
                    this.setTitle(currentFile.getName() + " - Markie");
                    setTipText("File saved", InfoPanel.TIP_LEAST_DELAY);
                } catch (IOException e) {
                    e.printStackTrace();
                    setTipText("Save failed!", InfoPanel.TIP_SHORT_DELAY);
                }
            }
        } else {
            try {
                FileUtils.writeStringToFile(currentFile, getInputText());
                setTipText("File saved", InfoPanel.TIP_LEAST_DELAY);
            } catch (IOException e) {
                e.printStackTrace();
                setTipText("Save failed!", InfoPanel.TIP_SHORT_DELAY);
            }
        }
        setInputCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void exportToHTML() {
        setInputCursor(new Cursor(Cursor.WAIT_CURSOR));
        String defaultFilename;
        if (currentFile == null) {
            defaultFilename = "untitled.html";
        } else {
            defaultFilename = FilenameUtils.removeExtension(currentFile.getAbsolutePath()) + ".html";
        }
        File htmlFile = FileKit.getSelectedSaveFile(this, FileKit.HTML_FILTER, defaultFilename);
        if (htmlFile != null) {
            MarkieProcessor markieProcessor = new MarkieProcessor(Extensions.ALL);

            String htmlContent = String.format(
                    "<html><head><meta charset=\"utf-8\"><style>%s</style></head><body>%s</body></html>",
                    getCSSContent(),
                    markieProcessor.markdownToHtml(getInputText()));
            try {
                FileUtils.writeStringToFile(htmlFile, htmlContent);
                setTipText("HTML file exported", InfoPanel.TIP_LEAST_DELAY);
            } catch (IOException e) {
                setTipText("HTML file export failed", InfoPanel.TIP_SHORT_DELAY);
                e.printStackTrace();
            }
        }
        setInputCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void exportToDOC() {
        setInputCursor(new Cursor(Cursor.WAIT_CURSOR));
        String defaultFilename;
        if (currentFile == null) {
            defaultFilename = "untitled.doc";
        } else {
            defaultFilename = FilenameUtils.removeExtension(currentFile.getAbsolutePath()) + ".doc";
        }
        File htmlFile = FileKit.getSelectedSaveFile(this, FileKit.DOC_FILTER, defaultFilename);
        if (htmlFile != null) {
            MarkieProcessor markieProcessor = new MarkieProcessor(Extensions.ALL);

            String htmlContent = String.format(
                    "<html><head><meta charset=\"utf-8\"><style>%s</style></head><body>%s</body></html>",
                    getCSSContent(),
                    markieProcessor.markdownToHtml(getInputText()));
            try {
                FileUtils.writeStringToFile(htmlFile, htmlContent);
                setTipText("DOC file exported", InfoPanel.TIP_LEAST_DELAY);
            } catch (IOException e) {
                setTipText("DOC file export failed", InfoPanel.TIP_SHORT_DELAY);
                e.printStackTrace();
            }
        }
        setInputCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void exportToPDF() {
        setInputCursor(new Cursor(Cursor.WAIT_CURSOR));
        String defaultFilename;
        if (currentFile == null) {
            defaultFilename = "untitled.pdf";
        } else {
            defaultFilename = FilenameUtils.removeExtension(currentFile.getAbsolutePath()) + ".pdf";
        }
        File pdfFile = FileKit.getSelectedSaveFile(this, FileKit.PDF_FILTER, defaultFilename);
        if (pdfFile != null) {
            Document document = new Document();
            document.setPageSize(PageSize.A4);
            PdfWriter writer;
            try {
                writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
                document.open();
                MarkieProcessor markieProcessor = new MarkieProcessor(Extensions.ALL);
                String htmlContent = String.format(
                        "<html><body>%s</body></html>",
                        /*getCSSContent(),*/
                        markieProcessor.markdownToHtml(getInputText()));
                XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(htmlContent));
                document.close();
                setTipText("PDF file exported", InfoPanel.TIP_LEAST_DELAY);
            } catch (DocumentException e) {
                setTipText("PDF file export failed", InfoPanel.TIP_SHORT_DELAY);
                e.printStackTrace();
            } catch (IOException e) {
                setTipText("PDF file export failed", InfoPanel.TIP_SHORT_DELAY);
                e.printStackTrace();
            }
        }
        setInputCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void undo() {
        inputTextArea.undo();
    }

    public void redo() {
        inputTextArea.redo();
    }

    public void selectAllText() {
        textArea.selectAll();
    }

    public String getInputText() {
        return textArea.getText();
    }

    public void setInputText(String text) {
        textArea.setText(text);
        previewPanel.update(inputTextArea, text);
    }

    public void setInputCursor(Cursor cursor) {
        textArea.setCursor(cursor);
    }

    public void setTipText(String text, int delay) {
        infoPanel.setTipText(text, delay);
    }

    public String getCSSContent() {
        String cssContent = null;
        PreferencesManager preferencesManager = new PreferencesManager(PreferencesManager.CSS_NODE);
        String cssSetting = preferencesManager.getStringInNode(PreferencesManager.CSS, "GitHub2");
        try {
            cssContent = loadCSSContent(cssSetting);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cssContent;
    }

    public String loadCSSContent(String name) throws IOException, URISyntaxException {
        String appDataDirectory = OsCheck.getAppDataDirectory();
        return FileUtils.readFileToString(new File(appDataDirectory + "/Markie/css/" + name + ".css"));
    }

}
