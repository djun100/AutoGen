package com.cy.ui.jfx;

import com.cy.bean.BeanWidget;
import com.cy.util.BaseJFXApplication;
import com.cy.util.UFile;
import com.jfinal.kit.Base64Kit;
import com.jfinal.kit.Kv;
import com.jfinal.template.Engine;
import com.jfinal.template.Template;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

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
        Engine engine = Engine.create("myEngine");
        engine.setDevMode(true);
        engine.setToClassPathSourceFactory();
        Template template = engine.getTemplateByString(templeteWebContent);
        String resultPageContent = template.renderToString(Kv.by("beanWidgets", mBeanWidgets));
        webEngine.setOnAlert(new EventHandler<WebEvent<String>>() {
            @Override
            public void handle(WebEvent<String> event) {
                String data = event.getData();
                System.out.println("alert:"+data);
                System.out.println("base64解码："+Base64Kit.decodeToStr(data));
            }
        });
        webEngine.loadContent(resultPageContent);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 600));
        primaryStage.show();

    }

    @Override
    protected String baseSetFxmlLocation() {
        return "/sample.fxml";
    }

}
