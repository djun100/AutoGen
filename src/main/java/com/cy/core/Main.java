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

        if (true ) return;
        CodeWriter.insertDefine();
        CodeWriter.insertInitView();
//        String tempPathName=new File("").getAbsolutePath()+
//                FinalConstants.PATH_BEFORE_GEN +
//                new File(Constants.getParsedJava().getPathNameActivity()).getName();

        String  tempPathName= UtilPlugin.getProjectRootPath(Constants.getAnActionEvent())+"/build"+
                FinalConstants.PATH_BEFORE_GEN +
                new File(Constants.getParsedJava().getPathNameActivity()).getName();

        CodeWriter.flush(tempPathName);

        String cmd;
        if (UtilEnv.isWindows()){
            String diffPath=UtilPlugin.getPluginPath("com.cy.plugin.AutoGen")+"/classes/diff.exe";
            cmd = String.format("%s %s %s -w -D %s",
                    diffPath,
                    Constants.getParsedJava().getPathNameActivity(),
                    tempPathName,
                    FinalConstants.DIFF_VERIFI);

        }else {
            cmd = String.format("diff %s %s -w -D %s"
                    , Constants.getParsedJava().getPathNameActivity(),tempPathName, FinalConstants.DIFF_VERIFI);
        }

        ArrayList<String> results = UtilCmd.exec(cmd);

        CodeWriter.removeLines(results,
                "#ifndef "+ FinalConstants.DIFF_VERIFI,
                "#else /* "+ FinalConstants.DIFF_VERIFI+" */",
                "#endif /* "+ FinalConstants.DIFF_VERIFI+" */",
                "#ifdef "+ FinalConstants.DIFF_VERIFI,
                "#endif /* ! "+ FinalConstants.DIFF_VERIFI+" */");

        CodeWriter.overWriteFile(Constants.getParsedJava().getPathNameActivity(),
                CodeWriter.convertContent(results));

        UtilPlugin.refreshFileSystem(Constants.getAnActionEvent());
    }

}

