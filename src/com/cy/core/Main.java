package com.cy.core;

import com.cy.FeatureParser.ParserCenter;
import com.cy.common.Constants;
import com.cy.common.FinalConstants;
import com.cy.util.UtilCmd;

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

        CodeWriter.insertDefine();
        CodeWriter.insertInitView();
        String tempPathName=new File("").getAbsolutePath()+
                FinalConstants.PATH_BEFORE_GEN +
                new File(Constants.getParsedJava().getPathNameActivity()).getName();
        CodeWriter.flush(tempPathName);
        // TODO_cy: 2019-02-18
        String cmd = String.format("diff %s %s -w -D %s"
                , Constants.getParsedJava().getPathNameActivity(),tempPathName, FinalConstants.DIFF_VERIFI);
        ArrayList<String> results = UtilCmd.exec(cmd);

        CodeWriter.removeLines(results,
                "#ifndef "+ FinalConstants.DIFF_VERIFI,
                "#else /* "+ FinalConstants.DIFF_VERIFI+" */",
                "#endif /* "+ FinalConstants.DIFF_VERIFI+" */",
                "#ifdef "+ FinalConstants.DIFF_VERIFI,
                "#endif /* ! "+ FinalConstants.DIFF_VERIFI+" */");

        CodeWriter.overWriteFile(Constants.getParsedJava().getPathNameActivity(),
                CodeWriter.convertContent(results));
    }

}

