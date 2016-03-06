package com.jing0.Markie;

import com.jing0.Markie.console.ArgsParser;
import com.jing0.Markie.console.ConsoleMode;
import com.jing0.Markie.console.DisplayUsageException;
import com.jing0.Markie.gui.MainFrame;
import com.jing0.Markie.gui.OsCheck;
import com.jing0.Markie.gui.preferences.PreferencesManager;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Entry point of the application.
 *
 * @author Jing0
 * @since 1.0
 */
public final class Markie {
    
    private Markie() {
    }

    /**
     * Launches the program in command line or using a GUI.
     * <p>
     * If the program is launched in GUI mode, sets some properties for a better look and feel integration.
     * </p>
     *
     * @param args a list of arguments.
     */
    public static void main(String[] args) {
        try {
            ArgsParser params = new ArgsParser();
            params.checkArgs(args);

            if (params.isConsoleMode()) {
                new ConsoleMode().process(params);
            } else {
                if (OsCheck.getOperatingSystemType() == OsCheck.OSType.MacOS) {
                    System.setProperty("apple.awt.application.name", "Markie");
                    System.setProperty("apple.laf.useScreenMenuBar", "true");
                    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Markie");
                }

                if (PreferencesManager.isFirstOpening()) {
                    try {
                        firstOpeningInit();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        UIManager.put("swing.boldMetal", Boolean.FALSE);
                        try {
                            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        } catch (Exception e) {
                            System.err.println("error: " + e.getMessage());
                            e.printStackTrace();
                        }
                        if (PreferencesManager.isFirstOpening()) {
                            System.out.println("This is the first opening");//TODO do something
                        }
                        new MainFrame();
                    }
                });
            }
        } catch (DisplayUsageException e) {
            System.err.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println(String.format("File not found: %s", e.getMessage()));
        }
    }

    public static void firstOpeningInit() throws IOException {
        String appDataDirectory = OsCheck.getAppDataDirectory();
        System.out.println(appDataDirectory);
        String[] cssFiles = {"Clear Dark", "Clear", "GitHub2", "Solarized (Dark)", "Solarized (Light)"};
        for (String cssFile : cssFiles) {
            FileUtils.copyInputStreamToFile(
                    //TODO 其他系统中系统路径规则不同,可能会出错
                    Markie.class.getResourceAsStream("/res/css/" + cssFile + ".css"),
                    new File(appDataDirectory + "/Markie/css/" + cssFile + ".css")
            );
        }
        String[] prismCssFiles = {"prism"};
        for (String prismCssFile : prismCssFiles) {
            FileUtils.copyInputStreamToFile(
                    Markie.class.getResourceAsStream("/res/prism/themes/" + prismCssFile + ".css"),
                    new File(appDataDirectory + "/Markie/prism/themes/" + prismCssFile + ".css")
            );
        }
    }
}
