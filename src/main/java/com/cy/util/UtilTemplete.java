package com.cy.util;

import com.jfinal.kit.Kv;
import com.jfinal.template.Engine;
import com.jfinal.template.Template;

public class UtilTemplete {
    /**usage:
     * UtilTemplete.getByEnjoy("IdsInLayout.html",Kv.by("beanWidgets", mBeanWidgets));
     * @param tlPath
     * @param kv
     * @return
     */
    public static String getByEnjoy(String tlPath, Kv kv){
        String templeteWebContent= UFile.readResourcesFileContent(tlPath);
        Engine engine = null;
        try {
            engine = Engine.create("myEngine");
        } catch (Exception e) {
            engine = Engine.use("myEngine");
        }
        engine.setDevMode(true);
        engine.setToClassPathSourceFactory();
        Template template = engine.getTemplateByString(templeteWebContent);
        String resultPageContent = template.renderToString(kv);
        return resultPageContent;
    }
}
