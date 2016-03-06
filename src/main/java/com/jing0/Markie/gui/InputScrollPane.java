package com.jing0.Markie.gui;

import javax.swing.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Observable;

/**
 * @author Jackie
 * @date 1/25/16
 */
public class InputScrollPane extends Observable {

    private final JScrollPane inputScrollPane = new JScrollPane();
    private final JScrollBar verticalScrollBar = inputScrollPane.getVerticalScrollBar();

    public InputScrollPane() {
        verticalScrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                setChanged();
                double maxVal = (double) verticalScrollBar.getMaximum();
                notifyObservers(e.getValue() / maxVal);
            }
        });
    }

    public JScrollPane get() {
        return inputScrollPane;
    }
}
