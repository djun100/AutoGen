package com.cy.bean;

import com.cy.data.UString;

import java.util.ArrayList;

/**列表型控件
 * Created by cy on 2017/4/28.
 */
public class BeanList extends BeanWidget {

    /**单或多type列表布局**/
    private ArrayList<String> layoutTags =new ArrayList<>();
    private String findViews;
    private String bean;
    private ArrayList<BeanWidget> subWidgets=new ArrayList<>();
    private ArrayList<String> layoutPathNames =new ArrayList<>();


    public String getFindViews() {
        return UString.isEmpty(findViews)?"":findViews;
    }

    public BeanList setFindViews(String findViews) {
        this.findViews = findViews;
        return this;
    }

    public ArrayList<String> getLayoutTags() {
        return layoutTags;
    }

    public BeanList setLayoutTags(ArrayList<String> layoutTags) {
        this.layoutTags = layoutTags;
        return this;
    }

    public String getBean() {
        return bean;
    }

    public BeanList setBean(String bean) {
        this.bean = bean;
        return this;
    }

    public ArrayList<BeanWidget> getSubWidgets() {
        return subWidgets;
    }

    public BeanList setSubWidgets(ArrayList<BeanWidget> subWidgets) {
        this.subWidgets = subWidgets;
        return this;
    }

    public ArrayList<String> getLayoutPathNames() {
        return layoutPathNames;
    }

    public BeanList setLayoutPathNames(ArrayList<String> layoutPathNames) {
        this.layoutPathNames = layoutPathNames;
        return this;
    }
}
