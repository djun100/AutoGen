package com.cy.plugin;

import com.cy.common.Constants;
import com.cy.core.Main;
import com.cy.util.UtilPlugin;
import com.cy.util.UtilProperty;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.vfs.VirtualFileManager;

import java.io.File;

public class InitViewAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Constants.setAnActionEvent(e);

        Main.doWork(UtilPlugin.getCurrFilePath(Constants.getAnActionEvent()), false);
//        SimpleFileController.loadFileByDialog(e);

        VirtualFileManager.getInstance().syncRefresh();

    }
}
