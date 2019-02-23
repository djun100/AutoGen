package com.cy.ui.jfx;

import com.cy.util.BaseJFXApplication;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MainJFXApp extends BaseJFXApplication {
    Controller mController;
    @Override
    public void baseStart(Stage primaryStage,Parent root){

        mController= (Controller) baseGetController();
        WebView webView=mController.getWebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load("http://www.baidu.com");

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 600));
        primaryStage.show();

    }

    @Override
    protected String baseSetFxmlLocation() {
        return "/sample.fxml";
    }
}
