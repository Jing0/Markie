package com.jing0.Markie.gui;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSeparatorUI;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Jackie
 * @date 1/25/16
 */
public class InfoPanel extends JPanel implements Observer {

    static int TIP_LONG_DELAY = 6400;
    static int TIP_SHORT_DELAY = 3200;
    static int TIP_LEAST_DELAY = 2400;

    private final MigLayout infoLayout = new MigLayout(
            "insets 0 5 0 0",
            "[20%][20%][1]5[]",
            "[align center]"
    );
    private final JLabel wordCount = new JLabel();
    private final JLabel lineCount = new JLabel();
    private final JLabel tip = new JLabel();

    public InfoPanel() {
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setBackground(Color.LIGHT_GRAY);
        separator.setUI(new BasicSeparatorUI());
        separator.setPreferredSize(new Dimension(1, 12));
        setTipText("Hello! :)", TIP_LONG_DELAY);

  /*      //Dark theme
        separator.setForeground(new Color(40, 40, 40));
        lineCount.setForeground(new Color(187, 187, 187));
        wordCount.setForeground(new Color(187, 187, 187));
        tip.setForeground(new Color(187, 187, 187));
        this.setBackground(new Color(58, 61, 63));
*/
        this.setLayout(infoLayout);
        this.add(wordCount);
        this.add(lineCount);
        this.add(separator);
        this.add(tip);
    }

    @Override
    public void update(final Observable o, final Object data) {
        if (o instanceof InputTextArea) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (data instanceof String) {
                        String raw = (String) data;
                        String trimmed = raw.trim();
                        updateWordCount(trimmed);
                        updateLineCount(raw);
                    } else {
                        updateTip(data);
                    }
                }
            });
        }
    }

    private void updateWordCount(String trimmed) {
        int numberOfWords = trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;
        String wordCountText = numberOfWords + " word";
        if (numberOfWords >= 2) {
            wordCountText = wordCountText + "s";
        }
        wordCount.setText(wordCountText);
    }

    private void updateLineCount(String raw) {
        int numberOfLines = raw.isEmpty() ? 0 : raw.split("\\n").length;
        String lineCountText = numberOfLines + " line";
        if (numberOfLines >= 2) {
            lineCountText = lineCountText + "s";
        }
        lineCount.setText(lineCountText);
    }

    private void updateTip(Object data) {
        if (data instanceof CannotUndoException) {
            setTipText("Sorry, cannot undo", TIP_SHORT_DELAY);
        } else if (data instanceof CannotRedoException) {
            setTipText("Sorry, cannot redo", TIP_SHORT_DELAY);
        }
    }

    public void setTipText(String text, int delay) {
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tip.setText(" ");
            }
        });
        timer.start();
        tip.setText(text);
    }
}
