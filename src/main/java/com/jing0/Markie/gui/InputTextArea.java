package com.jing0.Markie.gui;

import com.jing0.Markie.gui.preferences.PreferencesManager;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.TooManyListenersException;

/**
 * @author Jackie
 * @date 1/25/16
 */
public class InputTextArea extends Observable {

    private final JTextArea textArea;
    private final UndoManager undoManager = new UndoManager();

    public InputTextArea() {
        textArea = new JTextArea();
        textArea.setMargin(new Insets(10, 10, 10, 10));
        textArea.setLineWrap(true);
        textArea.setDragEnabled(true);

        textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });

        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseReleased(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    JPopupMenu popupMenu = new JPopupMenu();

                    JMenuItem copy = new JMenuItem(new DefaultEditorKit.CopyAction());
                    JMenuItem cut = new JMenuItem(new DefaultEditorKit.CutAction());
                    JMenuItem paste = new JMenuItem(new DefaultEditorKit.PasteAction());
                    JMenuItem selectAll = new JMenuItem();
                    selectAll.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            textArea.selectAll();
                        }
                    });

                    copy.setText("Copy");
                    cut.setText("Cut");
                    paste.setText("Paste");
                    selectAll.setText("Select All");

                    popupMenu.add(copy);
                    popupMenu.add(cut);
                    popupMenu.add(paste);
                    popupMenu.addSeparator();
                    popupMenu.add(selectAll);

                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        DropTargetListener dropTargetListener = new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                    @SuppressWarnings("unchecked")
                    List<File> droppedFiles = (List<File>) dtde.getTransferable().getTransferData(
                            DataFlavor.javaFileListFlavor);

                    int caretPosition = textArea.getCaretPosition();
                    StringBuilder originalContent = new StringBuilder(textArea.getText());
                    StringBuilder insertion = new StringBuilder();
                    for (File file : droppedFiles) {
                        insertion.append(String.format("![%s](%s)\r", file.getName(), file.getAbsolutePath()));
                    }
                    textArea.setText(originalContent.insert(caretPosition, insertion.toString()).toString());
                } catch (UnsupportedFlavorException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                notifyWhenTextChanged();
            }
        };
        DropTarget dropTarget = new DropTarget();
        try {
            dropTarget.addDropTargetListener(dropTargetListener);
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        }
        textArea.setDropTarget(dropTarget);

        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                notifyWhenTextChanged();
            }
        });

        loadConfigs();
    }

    public void loadConfigs() {
        PreferencesManager preferencesManager = new PreferencesManager(PreferencesManager.EDITOR_NODE);
        String fontSetting = preferencesManager.getStringInNode(PreferencesManager.EDITOR_FONT, "Monaco");
        int fontSizeSetting = preferencesManager.getIntInNode(PreferencesManager.EDITOR_FONT_SIZE, 16);
        textArea.setFont(new Font(fontSetting, Font.PLAIN, fontSizeSetting));
        int tabSizeSetting = preferencesManager.getIntInNode(PreferencesManager.TAB_SIZE, 4);
        textArea.setTabSize(tabSizeSetting);
    }

    public JTextArea get() {
        return textArea;
    }

    public void undo() {
        try {
            undoManager.undo();
        } catch (CannotUndoException e) {
            setChanged();
            notifyObservers(e);
        }
    }

    public void redo() {
        try {
            undoManager.redo();
        } catch (CannotRedoException e) {
            setChanged();
            notifyObservers(e);
        }
    }

    protected void notifyWhenTextChanged() {
        setChanged();
        notifyObservers(textArea.getText());
    }
}
