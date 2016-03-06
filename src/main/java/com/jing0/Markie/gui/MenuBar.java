package com.jing0.Markie.gui;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;

/**
 * Provides the menu bar of the application.
 *
 * @author Jing0
 * @since 1.0
 */
public final class MenuBar extends Observable {

    private final JMenuBar menuBar = new JMenuBar();
    private MainFrame frame;

    /**
     * Creates the menu bar and the different menus (file / edit / help).
     */
    public MenuBar(MainFrame frame) {
        this.frame = frame;
        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        menuBar.add(createHelpMenu());
    }

    /**
     * Returns the JMenuBar object.
     *
     * @return the JMenuBar object.
     */
    public JMenuBar get() {
        return menuBar;
    }

    /**
     * Creates the file menu.
     * <p>
     * The file menu contains an "Exit" item, to quit the application.
     * </p>
     *
     * @return the newly created file menu.
     */
    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');

        JMenuItem newFile = new JMenuItem("New", KeyEvent.VK_N);
        JMenuItem open = new JMenuItem("Open...", KeyEvent.VK_O);
        JMenuItem save = new JMenuItem("Save", KeyEvent.VK_S);
        JMenu export = new JMenu("Export");
        JMenuItem exportToHTML = new JMenuItem("HTML...");
        JMenuItem exportToPDF = new JMenuItem("PDF...");
        JMenuItem preferences = new JMenuItem("preferences...");
        JMenuItem exit = new JMenuItem("Exit", KeyEvent.VK_X);

        if (OsCheck.getOperatingSystemType() == OsCheck.OSType.MacOS) {
            newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.META_MASK));
            open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.META_MASK));
            save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.META_MASK));
            preferences.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, InputEvent.META_MASK));
        } else {
            newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
            open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
            save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
            preferences.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, InputEvent.CTRL_MASK));
        }

        newFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.newFile();
            }
        });
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.open();
            }
        });
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.save();
            }
        });
        exportToHTML.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.exportToHTML();
            }
        });
        exportToPDF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.exportToPDF();
            }
        });
        preferences.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.preferences();
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        export.add(exportToHTML);
        export.add(exportToPDF);

        fileMenu.add(newFile);
        fileMenu.add(open);
        fileMenu.add(save);
        fileMenu.addSeparator();
        fileMenu.add(export);
        fileMenu.addSeparator();
        fileMenu.add(preferences);
        fileMenu.add(exit);
        return fileMenu;
    }

    private JMenu createEditMenu() {
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic('E');

        JMenuItem undo = new JMenuItem("Undo", KeyEvent.VK_Z);
        JMenuItem redo = new JMenuItem("Redo", KeyEvent.VK_R);
        JMenuItem copy = new JMenuItem(new DefaultEditorKit.CopyAction());
        JMenuItem cut = new JMenuItem(new DefaultEditorKit.CutAction());
        JMenuItem paste = new JMenuItem(new DefaultEditorKit.PasteAction());
        JMenuItem selectAll = new JMenuItem("Select All", KeyEvent.VK_A);

        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.undo();
            }
        });
        redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.redo();
            }
        });
        selectAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.selectAllText();
            }
        });

        copy.setText("Copy");
        cut.setText("Cut");
        paste.setText("Paste");
        copy.setMnemonic(KeyEvent.VK_C);
        cut.setMnemonic(KeyEvent.VK_X);
        paste.setMnemonic(KeyEvent.VK_V);

        if (OsCheck.getOperatingSystemType() == OsCheck.OSType.MacOS) {
            undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.META_MASK));
            redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.SHIFT_MASK + InputEvent.META_MASK));
            copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.META_MASK));
            cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.META_MASK));
            paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.META_MASK));
            selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.META_MASK));
        } else {
            undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
            redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.SHIFT_MASK + InputEvent.CTRL_MASK));
            copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
            cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
            paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
            selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        }

        editMenu.add(undo);
        editMenu.add(redo);
        editMenu.addSeparator();
        editMenu.add(copy);
        editMenu.add(cut);
        editMenu.add(paste);
        editMenu.addSeparator();
        editMenu.add(selectAll);
        return editMenu;
    }

    /**
     * Creates the help menu.
     * <p>
     * The help menu contains an "About" item, to display some software information.
     * </p>
     *
     * @return the newly created help menu.
     */
    private JMenu createHelpMenu() {
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');

        JMenuItem about = new JMenuItem("About", KeyEvent.VK_A);

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(menuBar.getParent(),
                        "<html>Created by Jing0<br>https://github.com/jing0<br><br>Powered by PegDown</html>",
                        "Markie: About", JOptionPane.INFORMATION_MESSAGE,
                        new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                                getClass().getResource("/res/icon/Icon-120.png"))));
            }
        });

        helpMenu.add(about);
        return helpMenu;
    }
}
