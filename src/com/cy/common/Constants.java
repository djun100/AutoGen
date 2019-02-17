package com.cy.common;

import com.cy.bean.ParsedJava;
import com.cy.bean.ParsedProject;

public class Constants {
    private static ParsedProject sParsedProject;
    private static ParsedJava sParsedJava;

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
}
