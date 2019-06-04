package com.cy.manager;

import com.cy.bean.BeanIntent;
import com.cy.bean.BeanWidget;
import com.cy.util.UtilStringEx;
import freemarker.cache.StringTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateHashModel;
import org.apache.logging.log4j.core.util.StringBuilderWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cy on 2017/6/26.
 */
public class FreeMarkerManager {

    public static final String TEMPLATE_DEFINE_WIDGETS="/define_widgets.ftl";
    public static final String TEMPLATE_INITVIEW ="/initview.ftl";

    private volatile static Configuration cfg;
    private volatile static StringTemplateLoader stringTemplateLoader;
    private volatile static boolean hasInitIncludedFtl;

    public static String filter(String nameTemplate,Builder builder)  {

        if (!hasInitIncludedFtl){
            hasInitIncludedFtl=true;
            filter("/const.ftl",new FreeMarkerManager.Builder());
            filter("/macro_adapter_clicklistener.ftl",new FreeMarkerManager.Builder());
            filter("/macro_adapters.ftl",new FreeMarkerManager.Builder());
            filter("/macro_clicklistener.ftl",new FreeMarkerManager.Builder());
            filter("/macro_findViewByIds.ftl",new FreeMarkerManager.Builder());
        }

        String templateContent= UtilStringEx.readResourcesFileContent("/template"+nameTemplate);
        nameTemplate=nameTemplate.replaceAll("/","");
        getStringTemplateLoader().putTemplate(nameTemplate,templateContent);
        Template template = null;
        try {
            template = getConfigration().getTemplate(nameTemplate,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Map root = new HashMap();

            root.put("view_type", builder.getView_type());
            root.put("widgets", builder.getWidgets());
            root.put("intent", builder.getBeanIntent());

            BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
            TemplateHashModel staticModels = wrapper.getStaticModels();
            root.put("statics", staticModels);


            StringBuilderWriter out=new StringBuilderWriter();
            template.process(root, out);
            out.flush();
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Configuration getConfigration(){
        if (cfg==null){
            synchronized (FreeMarkerManager.class) {
                if (cfg==null) {
                    cfg = new Configuration();
                }
            }
//            //将写好的标签加入，起名叫label
////        cfg.setSharedVariable("label", new LabelDirective());
//            try {
//                System.out.println(new File("").getPath());
////                cfg.setDirectoryForTemplateLoading(new File("/template"));
//                cfg.setClassForTemplateLoading(new FreeMarkerManager().getClass(), "/templates");
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            cfg.setObjectWrapper(new DefaultObjectWrapper());
        }

        return cfg;
    }

    private static StringTemplateLoader getStringTemplateLoader(){
        if (stringTemplateLoader==null){
            synchronized (FreeMarkerManager.class) {
                if (stringTemplateLoader==null) {
                    stringTemplateLoader = new StringTemplateLoader();
                    getConfigration().setTemplateLoader(stringTemplateLoader);
                }
            }
        }
        return stringTemplateLoader;
    }

    public static class Builder{
        String     view_type;
        BeanIntent intent;
        private List<BeanWidget> widgets;

        public List<BeanWidget> getWidgets() {
            return widgets;
        }

        public Builder setWidgets(List<BeanWidget> widgets) {
            this.widgets = widgets;
            return this;
        }

        public String getView_type() {
            return view_type;
        }

        public Builder setView_type(String view_type) {
            this.view_type = view_type;
            return this;
        }

        public BeanIntent getBeanIntent() {
            return intent;
        }

        public Builder setBeanIntent(BeanIntent bean) {
            this.intent = bean;
            return this;
        }

    }

}
