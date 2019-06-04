package com.cy.FeatureParser;

import com.cy.bean.BeanIncludeXml;
import com.cy.bean.BeanList;
import com.cy.bean.BeanWidget;
import com.cy.common.Constants;
import com.cy.util.ProjectPathUtil;
import com.cy.util.UtilCommonCLI;
import com.cy.util.UtilString;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class XmlParser extends DefaultHandler {
    private String mIncludeId;

    public XmlParser(String includeId) {
        mIncludeId = includeId;
    }

    private List<BeanWidget> mBeanWidgets;

    @Override
    public void startDocument() {
        mIncludeId = mIncludeId == null ? "" : mIncludeId;
        mBeanWidgets = new ArrayList<>();
    }

    /**
     * uri - The Namespace URI, or the empty string if the element has no Namespace URI or
     * if Namespace processing is not being performed.
     * localName - The local name (without prefix), or the empty string if Namespace processing
     * is not being performed.
     * qName - The qualified name (with prefix), or the empty string if qualified names are not available.
     * attributes - The attributes attached to the element. If there are no attributes,
     * it shall be an empty Attributes object.
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        createBeanWidgetsByXmlNodes(qName, attributes);
    }

    /**
     * <include
     * android:id="@+id/include1"
     * layout="@layout/common_title" />
     *
     * @param widgetType 元素
     * @param attributes 元素的所有属性
     */
    private void createBeanWidgetsByXmlNodes(String widgetType, Attributes attributes) {
//      parse include widget
        parseIncludeWidget(widgetType, attributes);

        BeanWidget beanWidget = findBeanWidgetById(widgetType, attributes);
        if (beanWidget != null) {
            //处理组件的非id属性
            for (int i = 0, len = attributes.getLength(); i < len; i++) {
                //只处理该组件的tag属性
                if (attributes.getQName(i).equals("android:tag")) {
                    Options options = new Options()
                            .addOption(OptionBuilder.withLongOpt("bean").hasArg().create())
                            .addOption(OptionBuilder.withLongOpt("layout").hasArgs().create())
                            .addOption(OptionBuilder.withLongOpt("click").create());

                    LinkedHashMap<String,String[]> parsedCmd=UtilCommonCLI.parseCmd(options,attributes.getValue(i));

                    if (parsedCmd.containsKey("layout")||parsedCmd.containsKey("bean")){
                        beanWidget = initBeanListIfNeed(beanWidget, widgetType, beanWidget.getResId());
                    }

                    if (parsedCmd.containsKey("layout")){
                        String[] values=parsedCmd.get("layout");
                        for (int j = 0; j < values.length; j++) {
                            String pathXml = Constants.getParsedJava().getPathNameXml();
                            String pathLayout = ProjectPathUtil.getParentDirByName(pathXml, "layout");
                            //去掉.xml
                            ((BeanList) beanWidget).getLayoutTags().add(
                                    UtilString.removeTailIfHas(values[j],".xml"));
                            ((BeanList) beanWidget).getLayoutPathNames().add(
                                    pathLayout + "/" + UtilString.addTailIfNotHas(values[j],".xml"));
                        }
                    }
                    if (parsedCmd.containsKey("bean")) {
                        String[] values=parsedCmd.get("bean");
                        ((BeanList) beanWidget).setBean(values[0]);
                    }
                    if (parsedCmd.containsKey("click")) {
                        beanWidget.setClickable(true);
                    }

                    if (beanWidget instanceof BeanList) {
                        ArrayList<BeanWidget> subBeanWidgets = (ArrayList<BeanWidget>) new WidgetsParser()
                                        .parse(((BeanList) beanWidget).getLayoutPathNames().get(0));

                        ((BeanList) beanWidget).getSubWidgets().addAll(subBeanWidgets);

                    }
                    break;
                }

            }
            mBeanWidgets.add(beanWidget);
        }
    }

    private BeanWidget findBeanWidgetById(String widgetType, Attributes attributes) {
        BeanWidget beanWidget = null;
        for (int i = 0, len = attributes.getLength(); i < len; i++) {
            String str = attributes.getValue(i);
            try {
                if (attributes.getQName(i).equals("android:id")
                        && attributes.getValue(i).startsWith("@+id/")) {

                    String resId = str.substring(str.indexOf('/') + 1);
                    beanWidget = new BeanWidget();
                    beanWidget.setType(widgetType);
                    beanWidget.setResId(resId);
                    beanWidget.setIncludeIdName(mIncludeId);
                    break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return beanWidget;
    }

    private void parseIncludeWidget(String widgetType, Attributes attributes) {
        if (widgetType.equals("include")) {
            BeanIncludeXml beanIncludeXml = new BeanIncludeXml();
            for (int i = 0, len = attributes.getLength(); i < len; i++) {
                String str = attributes.getValue(i).toString();
                if (attributes.getQName(i).equals("layout")) {
                    String includeXmlName = attributes.getValue(i).toString().split("/")[1];
                    beanIncludeXml.path = includeXmlName;
                }
                if (attributes.getQName(i).equals("android:id")) {
                    String includeId = attributes.getValue(i).toString().split("/")[1];
                    beanIncludeXml.id = includeId;
                }
            }
            Constants.getParsedJava().getWidgetsParser().getIncludeXmlPathNames().add(beanIncludeXml);
        }
    }

    private BeanWidget initBeanListIfNeed(BeanWidget beanWidget, String widgetType, String idName) {

        if (!(beanWidget instanceof BeanList)) {
            beanWidget = new BeanList();
            beanWidget.setType(widgetType);
            beanWidget.setResId(idName);
            beanWidget.setIncludeIdName(mIncludeId);
        }
        return beanWidget;
    }

    /**
     * 带id属性的组件会被添加到这个集合
     */
    public List<BeanWidget> getBeanWidgets() {
        return mBeanWidgets;
    }

}
