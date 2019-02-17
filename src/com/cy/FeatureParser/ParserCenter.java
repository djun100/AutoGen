package com.cy.FeatureParser;

import com.cy.bean.ParsedJava;
import com.cy.common.BusEvents;
import com.cy.common.Constants;
import com.cy.data.UString;
import com.cy.util.ProjectPathUtil;
import com.cy.util.URegex;

import java.io.*;
import java.util.ArrayList;

import static com.cy.common.FinalConstants.logger;

/**
 * Created by cy on 2017/5/28.
 */
public class ParserCenter {

    public static void parse(String pathNameJava,boolean isEclipsePath){
        Constants.getParsedJava().setPathNameActivity(pathNameJava);
        String pathNameLayout= ProjectPathUtil.findLayoutPath(pathNameJava);
        Constants.getParsedJava().setPathNameXml(pathNameLayout);
        JavaStructureParser structureParser=new JavaStructureParser(new File(pathNameJava));
        Constants.getParsedJava().setStructureParser(structureParser);
        String pathNameManifest = ProjectPathUtil.findPathNameManifest(pathNameJava);
        String pkgName= ProjectPathUtil.findPkgName(pathNameManifest);

        if (!UString.isEmpty(pkgName)){
            Constants.getParsedProject().setPackageName(pkgName);
        }

        Constants.getParsedJava().getWidgetsFinal().addAll(
                Constants.getParsedJava().getWidgetsParser().parse(Constants.getParsedJava().getPathNameXml()));


        parseViewTypeAndPathName(Constants.getParsedJava().getPathNameXml(), isEclipsePath);
        visualizeActivity(Constants.getParsedJava().getPathNameActivity());
    }


    public static String visualizeActivity(String pathName){
        String dealResult=null;
        File file = new File(pathName);
        if (!file.exists()) return "activity不存在:"+pathName;
        BufferedReader reader = null;
        int line = 0;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String tempString = null;

            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                Constants.getParsedJava().getActivityContents().put(line,tempString);//文件内容到内存
                line++;
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        dealResult = "处理activity文件：" + pathName + "\n" + "读取line数：" + line + "\n";
        BusEvents.postProgress("分析activity完毕 "+dealResult);
        return dealResult;
    }

    /**分析activity路径及view类型activity or fragment
     * @param pathNameXml
     * @param isEclipsePath 是否为eclipse目录结构
     */
    public static void parseViewTypeAndPathName(String pathNameXml, boolean isEclipsePath){
//      String pathNameXml="F:\\projects\\android\\JoyApp - 副本\\JoyApp\\src\\main\\res\\layout\\activity_enter_sence.xml";
        BusEvents.postProgress("开始分析文件："+pathNameXml);
        String eclipsePath=isEclipsePath?"/src/":"/java/";
        ArrayList<String> regexesViewPathName=new ArrayList<>();
//        regexesViewPathName.add("tools:context");
        regexesViewPathName.add("tools:context=\"([_0-9a-zA-Z.]+)\"");
        String tempString= URegex.dealFile(pathNameXml,"tools:context",regexesViewPathName,"%s");
        if (UString.isEmpty(Constants.getParsedJava().getPathNameActivity())) {
            if (tempString.startsWith(".")){
                //.activity 非全包路径
                Constants.getParsedJava().setPathNameActivity(ProjectPathUtil.getParentDirByName(pathNameXml,"main")+eclipsePath
                        + Constants.getParsedProject().getPackageName().replaceAll("\\.","/")
                        + tempString.replaceAll("\\.","/")+".java");
            }else {
                Constants.getParsedJava().setPathNameActivity(ProjectPathUtil.getParentDirByName(pathNameXml,"main")+eclipsePath
                        + tempString.replaceAll("\\.","/")+".java");
            }
        }
        logger.info("activity path name:"+Constants.getParsedJava().getPathNameActivity());

        //判断视图类型，activity 还是 fragment
        String fileName=new File(Constants.getParsedJava().getPathNameActivity()).getName();
        //此处这样判断是因为自定义view的findViewById写法同activity
        //fixme judge view type
        if (fileName.split("\\.")[0].contains("Fra")){
            Constants.getParsedJava().setViewType(ParsedJava.ViewType.FRAGMENT);
        }else {
            Constants.getParsedJava().setViewType(ParsedJava.ViewType.ACTIVITY);
        }
    }
}
