package com.cy.plugin;

import com.cy.common.Constants;
import com.cy.core.Main;
import com.cy.util.UtilPlugin;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class InitViewAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Constants.setAnActionEvent(e);

        Main.doWork(UtilPlugin.getCurrFilePath(Constants.getAnActionEvent()), false);
//        SimpleFileController.loadFileByDialog(e);

    }
}
