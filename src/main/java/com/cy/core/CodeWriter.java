package com.cy.core;

import com.cy.FeatureParser.JavaStructureParser;
import com.cy.bean.BeanWidget;
import com.cy.common.BusEvents;
import com.cy.common.Constants;
import com.cy.common.FinalConstants;
import com.cy.data.UtilString;
import com.cy.manager.FreeMarkerManager;
import com.cy.util.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by cy on 2017/3/8.
 */
public class CodeWriter {
    public static void genSourceByDiff(List<BeanWidget> beanWidgets){

        CodeWriter.insertDefine(beanWidgets);
        CodeWriter.insertInitView(beanWidgets);

        String  tempPathName= UtilPlugin.getProjectRootPath(Constants.getAnActionEvent())+"/build"+
                FinalConstants.PATH_BEFORE_GEN +
                new File(Constants.getParsedJava().getPathNameActivity()).getName();

        CodeWriter.flush(tempPathName);

        String cmd;
        if (UtilEnv.isWindows()){
            UtilPlugin.extractJar();
            String diffPath=UtilPlugin.getPluginPath("com.cy.plugin.AutoGen")+"\\AutoGen\\diff.exe";
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

    /**
     * @param beanWidgets
     */
    public static void insertDefine(List<BeanWidget> beanWidgets){
        String declare= FreeMarkerManager.filter(
                FreeMarkerManager.TEMPLATE_DEFINE_WIDGETS,new FreeMarkerManager.Builder().setWidgets(beanWidgets));
        insertToDefine(declare);
    }

    /**
     * @param beanWidgets
     */
    public static void insertInitView(List<BeanWidget> beanWidgets){

        insertInitView(
                FreeMarkerManager.filter(FreeMarkerManager.TEMPLATE_INITVIEW,new FreeMarkerManager.Builder()
                .setWidgets(beanWidgets)
                .setView_type(Constants.getParsedJava().getViewType().getType())
                )
        );
    }

    /**content:完整的initView函数*/
    public static void insertInitView(String content){
        // 先删掉该函数，然后再插入
        String methodName = JavaStructureParser.parseMethodName(content);
        JavaStructureParser.Method method=
                Constants.getParsedJava().getStructureParser().findMethodByName(methodName);
        if (method!=null) {
            deleteLines(method.lineBegin,method.lineEnd);
        }
        int lineToInsert = 0;
        if (method!=null){
            lineToInsert=method.lineBegin;
        }else {
            lineToInsert= Constants.getParsedJava().getStructureParser().getMethodsRegion()[1];
        }
        insertToContents(lineToInsert, content);
    }

    public static void insertToContents(int lineToAppend, String content){
        //如果数据集用LinkedHashMap，该行会处在最后，但是行号并不是最后一行，造成位置错乱，插入内容跑到原文最后
        Constants.getParsedJava().getActivityContents().put(lineToAppend,
                UtilStringEx.valueOf(Constants.getParsedJava().getActivityContents().get(lineToAppend))+
                        "\n"+content);
    }

    public static void deleteLines(int from, int to) {
        for (Iterator<Map.Entry<Integer, String>> it =
             Constants.getParsedJava().getActivityContents().entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Integer, String> item = it.next();
            int key = item.getKey();
            if (key >= from && key <= to) {
                it.remove();
            }
        }
    }

    public static void insertToDefine(String content){
        StringBuilder finalContent=new StringBuilder();
        for (String line:content.split("\n")){
            boolean contains=false;
            for (String actLine:Constants.getParsedJava().getActivityContents().values()){
                if (actLine.replaceAll(" ","")
                        .contains(line.trim().replaceAll(" ",""))){
                    contains=true;
                    break;
                }
            }
            if (!contains) {
                finalContent.append(line).append("\n");
            }
        }
        if (finalContent.toString().endsWith("\n")){
            finalContent.deleteCharAt(finalContent.length()-1);
        }
        int line= Constants.getParsedJava().getStructureParser().getFieldsRegion()[1]== 0 ?
                Constants.getParsedJava().getStructureParser().getmBodyBegin()-1:
                Constants.getParsedJava().getStructureParser().getFieldsRegion()[1];
        insertToContents(line,finalContent.toString());
    }

    public static void flush(String destPathName){
        //前置异常处理
        File fileDest=new File(destPathName);
        if (!fileDest.exists() || fileDest.isDirectory()){
            new File(fileDest.getParent()).mkdirs();
            try {
                fileDest.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        StringBuilder sb= new StringBuilder();

        for (String s: Constants.getParsedJava().getActivityContents().values()){
            if (UtilString.notEmpty(s)){
                sb.append(s).append("\n");
            }
        }
        overWriteFile(destPathName, sb.toString());
        BusEvents.postProgress("生成代码写入文件完毕");
    }

    public static void overWriteFile(String pathName,String content){
        new File(pathName).delete();
        UtilFile.write_UTF8(new File(pathName),content);
    }

    public static String convertContent(ArrayList<String> javaContent){
        StringBuilder sb= new StringBuilder();
        for (String s: javaContent){
//            if (UtilStringEx.notEmpty(s)){
                sb.append(s).append("\n");
//            }
        }
        return sb.toString();
    }

    public static void removeLines(ArrayList<String> results, String... feature) {
        Iterator<String> it = results.iterator();
        while(it.hasNext()){
            String ele = it.next();
            for (String fea:feature){
                if (ele.contains(fea.trim())){
                    it.remove();
                }
            }
        }
    }
}
