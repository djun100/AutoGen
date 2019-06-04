package com.cy.core;

import com.cy.FeatureParser.ParserCenter;
import com.cy.common.Constants;
import com.cy.common.FinalConstants;
import com.cy.ui.jfx.MainJFXApp;
import com.cy.util.UtilCmd;
import com.cy.util.UtilEnv;
import com.cy.util.UtilPlugin;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by cy on 2017/3/8.
 */
public class Main {

    /**目录分析基于Android studio 项目结构*/
    public static void doWork(String pathNameJava,boolean isEclipsePath){
        pathNameJava=pathNameJava.replaceAll("\\\\","/");
        Constants.clear();
        ParserCenter.parse(pathNameJava,isEclipsePath);

        new MainJFXApp(Constants.getParsedJava().getWidgetsFinal()).baseStart();
    }

}

