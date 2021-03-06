package com.cy.bean;

import com.cy.data.UtilString;
import com.google.common.base.Strings;

import java.util.ArrayList;

public class BeanWidget {

    /*** id名称*/
    private String resId;
    /*** 被include的父控件的 id名称*/
    private String includeIdName;
    /*** 变量名称*/
    private String defineName;

    /*** 节点类名*/
    private String type;
    /*** Activity中代码生成前ClickEvent事件*/
    private String content = "";

    /*** 是否有onClick属性*/
    private boolean clickable = false;
    /*** 该组件在JavaCode中的声明的注释的起始line*/
    private int docBegin = -1;
    /*** 该组件在JavaCode中的声明的注释的结束line*/
    private int docEnd = -1;
    private String docContent = "";
    private boolean enable=true;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String mId) {
        this.resId = mId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public int getDocBegin() {
        return docBegin;
    }

    public void setDocBegin(int docBegin) {
        this.docBegin = docBegin;
    }

    public int getDocEnd() {
        return docEnd;
    }

    public void setDocEnd(int docEnd) {
        this.docEnd = docEnd;
    }

    public String getDocContent() {
        return docContent;
    }

    public void setDocContent(String docContent) {
        this.docContent = docContent;
    }

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public static BeanWidget getWidgetInfoByDefineName(String defineName, ArrayList<BeanWidget> beanWidgets) {
        if (Strings.isNullOrEmpty(defineName)) {
            System.err.println("getWidgetInfoByIdName:resId is empty");
        }
        if (beanWidgets == null || beanWidgets.size() == 0) {
            System.err.println("getWidgetInfoByIdName beanWidgets is empty");
        }
        for (BeanWidget beanWidget : beanWidgets) {
            if (beanWidget.getDefineName().equals(defineName)) return beanWidget;
        }
        return null;
    }

    public static BeanWidget getWidgetInfoByIdName(String idName, ArrayList<BeanWidget> beanWidgets) {
        if (Strings.isNullOrEmpty(idName)) {
            System.err.println("getWidgetInfoByIdName:resId is empty");
        }
        if (beanWidgets == null || beanWidgets.size() == 0) {
            System.err.println("getWidgetInfoByIdName beanWidgets is empty");
        }
        for (BeanWidget beanWidget : beanWidgets) {
            if (beanWidget.getResId().equals(idName)) return beanWidget;
        }
        return null;
    }

    public String getDefineName() {
        return defineName;
    }

    public void setDefineName(String defineName) {
        this.defineName = defineName;
    }


    public String getIncludeIdName() {
        return includeIdName;
    }

    public void setIncludeIdName(String includeIdName) {
        this.includeIdName = includeIdName;
        if (!UtilString.isEmpty(includeIdName)) {
            setDefineName(includeIdName + "_" + resId);
        } else {
            setDefineName(resId);
        }
    }
}
