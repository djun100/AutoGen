package com.cy.common;

import com.cy.bean.ParsedJava;
import com.cy.bean.ParsedProject;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class Constants {
    private static ParsedProject sParsedProject;
    private static ParsedJava sParsedJava;
    private static AnActionEvent sAnActionEvent;

    public static ParsedProject getParsedProject(){
        if (sParsedProject==null){
            sParsedProject=new ParsedProject();
        }
        return sParsedProject;
    }

    public static ParsedJava getParsedJava(){
        if (sParsedJava==null){
            sParsedJava=new ParsedJava();
        }
        return sParsedJava;
    }

    public static void clear(){
        sParsedJava=null;
        sParsedProject=null;
    }

    public static void setAnActionEvent(AnActionEvent anActionEvent){
        sAnActionEvent=anActionEvent;
    }

    public static AnActionEvent getAnActionEvent(){
        return sAnActionEvent;
    }
}
