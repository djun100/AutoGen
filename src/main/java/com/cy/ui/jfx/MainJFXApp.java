package com.cy.ui.jfx;

import com.alibaba.fastjson.JSON;
import com.cy.bean.BeanWidget;
import com.cy.bean.BeanWidgetForGen;
import com.cy.core.CodeWriter;
import com.cy.util.BaseJFXApplication;
import com.cy.util.UtilTemplete;
import com.jfinal.kit.Kv;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.awt.*;
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

        webEngine.setOnAlert(new EventHandler<WebEvent<String>>() {
            @Override
            public void handle(WebEvent<String> event) {
                String data = event.getData();
                mBeanWidgets = customizeBeanWidgetsByUser(data);
                CodeWriter.genSourceByDiff(mBeanWidgets);
            }
        });
        String resultPageContent = UtilTemplete.getByEnjoy("enjoy/IdsInLayout.html",Kv.by("beanWidgets", mBeanWidgets));
        System.out.println(resultPageContent);
        webEngine.loadContent(resultPageContent);

        primaryStage.setTitle("Select the widgets needed,then click 'generate'");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        primaryStage.setScene(new Scene(root, screenSize.width, screenSize.height));
        primaryStage.show();

    }

    private List<BeanWidget> customizeBeanWidgetsByUser(String data) {
        System.out.println("alert:"+data);
        List<BeanWidgetForGen> beanWidgetForGens = JSON.parseArray(data, BeanWidgetForGen.class);
        for (int i = 0; i < beanWidgetForGens.size(); i++) {
            mBeanWidgets.get(i).setEnable(beanWidgetForGens.get(i).enableWidget);
            mBeanWidgets.get(i).setClickable(beanWidgetForGens.get(i).enableClickEvent);
        }
        return mBeanWidgets;
    }

    @Override
    protected String baseSetFxmlLocation() {
        return "/sample.fxml";
    }

    public static void main(String[] args) {
        launch(MainJFXApp.class,null);
    }
}
