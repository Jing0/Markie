package com.jing0.Markie.gui;

/**
 * @author Jackie
 * @date 1/31/16
 * <p/>
 * helper class to check the operating system this Java VM runs in
 * <p/>
 * please keep the notes below as a pseudo-license
 * <p/>
 * http://stackoverflow.com/questions/228477/how-do-i-programmatically-determine-operating-system-in-java
 * compare to http://svn.terracotta.org/svn/tc/dso/tags/2.6.4/code/base/common/src/com/tc/util/runtime/Os.java
 * http://www.docjar.com/html/api/org/apache/commons/lang/SystemUtils.java.html
 */

import java.util.Locale;

public final class OsCheck {
    // cached result of OS detection
    protected static OSType detectedOS;

    /**
     * detect the operating system from the os.name System property and cache
     * the result
     *
     * @return - the operating system detected
     */
    public static OSType getOperatingSystemType() {
        if (detectedOS == null) {
            String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((OS.contains("mac")) || (OS.contains("darwin"))) {
                detectedOS = OSType.MacOS;
            } else if (OS.contains("win")) {
                detectedOS = OSType.Windows;
            } else if (OS.contains("nux")) {
                detectedOS = OSType.Linux;
            } else {
                detectedOS = OSType.Other;
            }
        }
        return detectedOS;
    }

    public static String getAppDataDirectory() {
        String appDataDirectory = null;
        if (detectedOS == OSType.Windows) {
            appDataDirectory = System.getenv("APPDATA");
        } else if (detectedOS == OSType.MacOS) {
            appDataDirectory = System.getProperty("user.home") + "/Library/Application "
                    + "Support";
        } else if (detectedOS == OSType.Linux) {
            appDataDirectory = System.getProperty("user.home");
        } else if (detectedOS == OSType.Other) {
            appDataDirectory = System.getProperty("user.dir");
        }
        return appDataDirectory;
    }

    /**
     * types of Operating Systems
     */
    public enum OSType {
        Windows, MacOS, Linux, Other
    }
}