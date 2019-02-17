package com.cy.bean;

import com.cy.FeatureParser.JavaStructureParser;
import com.cy.FeatureParser.WidgetsParser;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ParsedJava {
    public static enum ViewType{
        ACTIVITY("activity"),FRAGMENT("fragment");
        private String type;
        private ViewType(String type){
            this.type=type;
        }

        public String getType(){
            return type;
        }
    }
    /**
     * 如果数据集用LinkedHashMap，插入行会在最后，但是行号并不是最后一行，造成位置错乱，插入内容跑到原文最后
     */
    private TreeMap<Integer,String> mActivityContents=new TreeMap<>();
    private List<BeanWidget> mWidgetsFinal=new ArrayList<>();
    private String mPathNameActivity;
    private String mPathNameXml;
    private ViewType viewType;
    private JavaStructureParser structureParser;
    private WidgetsParser widgetsParser=new WidgetsParser();

    public TreeMap<Integer, String> getActivityContents() {
        return mActivityContents;
    }

    public List<BeanWidget> getWidgetsFinal() {
        return mWidgetsFinal;
    }

    public ParsedJava setWidgetsFinal(List<BeanWidget> widgetsFinal) {
        this.mWidgetsFinal = widgetsFinal;
        return this;
    }

    public String getPathNameActivity() {
        return mPathNameActivity;
    }

    public ParsedJava setPathNameActivity(String pathNameActivity) {
        this.mPathNameActivity = pathNameActivity;
        return this;
    }

    public String getPathNameXml() {
        return mPathNameXml;
    }

    public ParsedJava setPathNameXml(String pathNameXml) {
        this.mPathNameXml = pathNameXml;
        return this;
    }

    public ViewType getViewType() {
        return viewType;
    }

    public ParsedJava setViewType(ViewType viewType) {
        this.viewType = viewType;
        return this;
    }


    public ParsedJava setStructureParser(JavaStructureParser structureParser) {
        this.structureParser = structureParser;
        return this;
    }

    public JavaStructureParser getStructureParser() {
        return structureParser;
    }

    public WidgetsParser getWidgetsParser() {
        return widgetsParser;
    }

}
