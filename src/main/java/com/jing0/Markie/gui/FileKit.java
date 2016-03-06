package com.jing0.Markie.gui;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/**
 * @author Jackie
 * @date 2/1/16
 */
public class FileKit {

    static FileFilter MD_FILTER = new FileNameExtensionFilter("Markdown file", "md", "markdown", "txt", "text");
    static FileFilter HTML_FILTER = new FileNameExtensionFilter("HTML file", "html", "htm", "xhtml");
    static FileFilter PDF_FILTER = new FileNameExtensionFilter("PDF file", "pdf");

    public static File getSelectedOpenFile(Component parent, FileFilter fileFilter) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(fileFilter);
        File selectedFile = null;
        int retVal = fileChooser.showOpenDialog(parent);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
        }
        return selectedFile;
    }

    public static File getSelectedSaveFile(Component parent, FileFilter fileFilter, String defaultFilename) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setSelectedFile(new File(defaultFilename));
        File selectedFile = null;
        int retVal = fileChooser.showSaveDialog(parent);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
        }
        return selectedFile;
    }

}
