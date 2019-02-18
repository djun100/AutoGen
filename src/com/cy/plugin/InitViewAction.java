package com.cy.plugin;

import com.cy.MainForm;
import com.cy.common.Constants;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class InitViewAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Constants.setAnActionEvent(e);
        MainForm.main(null);
    }
}
