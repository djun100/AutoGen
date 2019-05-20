package com.cy.ui.jfx;

import com.alibaba.fastjson.JSON;
import com.cy.bean.AndroidView;
import com.cy.bean.BeanWidget;
import com.cy.bean.BeanWidgetForGen;
import com.cy.common.Constants;
import com.cy.controller.SimpleFileController;
import com.cy.ui.SimpleFormatSelectDialog;
import com.cy.util.BaseJFXApplication;
import com.cy.util.UFile;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.util.DocumentUtil;
import com.jfinal.kit.Kv;
import com.jfinal.template.Engine;
import com.jfinal.template.Template;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.List;


public class MainJFXApp extends BaseJFXApplication {
    Controller mController;
    List<BeanWidget> mBeanWidgets;

    public MainJFXApp() {
    }

    public MainJFXApp(List<BeanWidget> mBeanWidgets) {
        this.mBeanWidgets = mBeanWidgets;
    }

    @Override
    public void baseStart(Stage primaryStage,Parent root){

        mController= (Controller) baseGetController();
        WebView webView=mController.getWebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);

//        String url = MainJFXApp.class.getResource("/IdsInLayout.html").toExternalForm();
//        webEngine.load(url);
//        webEngine.load("http://www.baidu.com");

        String templeteWebContent= UFile.readResourcesFileContent("/IdsInLayout.html");
        Engine engine = null;
        try {
            engine = Engine.create("myEngine");
        } catch (Exception e) {
            engine = Engine.use("myEngine");
        }
        engine.setDevMode(true);
        engine.setToClassPathSourceFactory();
        Template template = engine.getTemplateByString(templeteWebContent);
        String resultPageContent = template.renderToString(Kv.by("beanWidgets", mBeanWidgets));
        webEngine.setOnAlert(new EventHandler<WebEvent<String>>() {
            @Override
            public void handle(WebEvent<String> event) {
                String data = event.getData();
                System.out.println("alert:"+data);
                List<BeanWidgetForGen> beanWidgetForGens = JSON.parseArray(data, BeanWidgetForGen.class);
                for (int i = 0; i < beanWidgetForGens.size(); i++) {
                    mBeanWidgets.get(i).setEnable(beanWidgetForGens.get(i).enableWidget);
                    mBeanWidgets.get(i).setClickable(beanWidgetForGens.get(i).enableClickEvent);
                }
                List<AndroidView> androidViews=AndroidView.convert(mBeanWidgets);

                WriteCommandAction.runWriteCommandAction(Constants.getAnActionEvent().getProject(), new Runnable() {
                    @Override
                    public void run() {
                        SimpleFileController.loadFile(Constants.getAnActionEvent(), androidViews);
                    }
                });

            }
        });
        webEngine.loadContent(resultPageContent);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();

    }

    @Override
    protected String baseSetFxmlLocation() {
        return "/sample.fxml";
    }

    public static void main(String[] args) {
        launch(MainJFXApp.class,null);
    }
}
