package com.cy.util;

import java.io.File;
import java.util.ArrayList;

public class ProjectPathUtil {

    public static String findLayoutPath(String pathJava) {
        String pathLayout;
        ArrayList<String> regexes = new ArrayList<>();
        regexes.add("R.layout.([_0-9a-zA-Z]+)");
        pathLayout = URegex.dealFile(pathJava, "R.layout.", regexes, "%s");
        pathLayout = pathLayout.split("\n")[0];
        String dirMain = getParentDirByName(pathJava, "main");
        if (dirMain != null) {
            String dirLayout = getSubDirByName(dirMain, "layout");
            pathLayout = dirLayout + File.separator + pathLayout + ".xml";
        } else {
            //eclipse struct directory
            String dirSrcParent = new File(getParentDirByName(pathJava, "src")).getParent();
            String dirLayout = getSubDirByName(dirSrcParent, "layout");
            pathLayout = dirLayout + File.separator + pathLayout + ".xml";
        }

        if (!new File(pathLayout).exists()){
            //TODO res configed in gradle maybe

        }
        return pathLayout;
    }


    public static String findPathNameManifest(String projFilePathName) {
        if (getParentDirByName(projFilePathName, "main") != null) {
            return getParentDirByName(projFilePathName, "main") + "/AndroidManifest.xml";
        } else {
            //eclipse directory
            return new File(getParentDirByName(projFilePathName, "src")).getParent() + "/AndroidManifest.xml";
        }
    }


    public static String getParentDirByName(String path, String dirName) {
        File file = new File(path);
        while (file != null && file.exists()) {
            if (file.getAbsolutePath().endsWith(dirName)) {
                return file.getAbsolutePath();
            }
            file = file.getParentFile();
        }
        return null;
    }


    public static String getSubDirByName(String path, String dirName) {
        File file = new File(path);
        File[] files = file.listFiles();
        if (files != null && files.length > 0) {
            for (File file1 : files) {
                if (file1.getName().equals(dirName)) {
                    return file1.getAbsolutePath();
                } else {
                    String temp = getSubDirByName(file1.getAbsolutePath(), dirName);
                    if (temp != null) {
                        return temp;
                    }
                }
            }
        }
        return null;
    }


    public static String findPkgName(String pathManifestName) {
        ArrayList<String> regexes = new ArrayList<>();
        regexes.add("package=\"([0-9a-zA-Z.]+)\"");
        String packageName = URegex
                .dealFile(pathManifestName, "package", regexes, "%s");
        return packageName;
    }

}
