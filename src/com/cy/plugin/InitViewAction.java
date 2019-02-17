package com.cy.plugin;

import com.cy.MainForm;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class InitViewAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        MainForm.main(null);
    }
}
