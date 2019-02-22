package com.cy.FeatureParser;

import com.cy.bean.BeanIncludeXml;
import com.cy.bean.BeanWidget;
import com.cy.common.BusEvents;
import com.cy.common.Constants;
import com.cy.data.UList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * */
public class WidgetsParser {


    /**    /**
     * eg:
     * <include
     * android:id="@+id/include"
     * layout="@layout/title_include">
     * </include>
     * then return title_include
     * 父xml中include节点的layout字段的值中layout引用路径
     * */
    private ArrayList<BeanIncludeXml> mIncludeXmlPathNames=new ArrayList<>() ;

    public List<BeanWidget> parse(String pathNameXml) {
        List<BeanWidget> result=new ArrayList<>();

        BusEvents.postProgress("xml文件分析中:"+pathNameXml);
        File pathNameXmlFile=new File(pathNameXml);

        List<BeanWidget> list=parse(null,pathNameXml);
        if (UList.notEmpty(list)) {
            result.addAll(list);
        }
        // parse includes
        if (UList.notEmpty(mIncludeXmlPathNames)){
            String pathLayout=pathNameXmlFile.getParent();
            for (BeanIncludeXml beanIncludeXml: mIncludeXmlPathNames){
                List<BeanWidget> beanWidgets =parse(beanIncludeXml.id,pathLayout+"/"+beanIncludeXml.path+".xml");
                if (beanWidgets !=null){
                    result.addAll(beanWidgets);
                }

            }
        }
        BusEvents.postProgress("xml文件分析完毕:"+pathNameXml);
        return result;
    }

    /**返回xml文件内所有带id字段且名称以m开头的组件*/
    private List<BeanWidget> parse(String preId, String pathNameXml){
        SAXParserFactory saxfa = SAXParserFactory.newInstance();
        XmlParser xmlParser = new XmlParser(preId);
        try {
            SAXParser saxparser = saxfa.newSAXParser();
            InputStream is = new FileInputStream(pathNameXml);
            saxparser.parse(is, xmlParser);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        return xmlParser.getmBeanWidgets();
    }

    public ArrayList<BeanIncludeXml> getIncludeXmlPathNames() {
        return mIncludeXmlPathNames;
    }

}
