package com.cy.util;

import com.cy.ui.jfx.Controller;
import com.sun.javafx.application.PlatformImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.net.URL;

public abstract class BaseJFXApplication<T> extends Application {
    private FXMLLoader loader;
    @Override
    public void start(Stage primaryStage) throws Exception{
        loader = new FXMLLoader();
        URL url=getClass().getResource(baseSetFxmlLocation());
        loader.setLocation(url);
        loader.setClassLoader(getClass().getClassLoader());
        Parent root = (Parent)loader.load();

        baseStart(primaryStage,root);
    }

    public FXMLLoader baseGetFxmlLoader() {
        return loader;
    }

    public <T> T baseGetController(){
        T controller = loader.getController();
        return controller;
    }

    public void baseStart() {
        PlatformImpl.startup(new Runnable() {
            @Override
            public void run() {
                initialize();
            }
        });
    }

    private Stage stage;
    private void initialize() {
        try {
            if (stage == null) {
                stage = new Stage();
            }
            start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract String baseSetFxmlLocation();
    protected abstract void baseStart(Stage primaryStage,Parent root);

    /**在开发插件项目的时候，如果使用javafx作UI，跟普通javafx一样调用该函数会报如下问题
     * java.lang.RuntimeException: java.lang.ClassNotFoundException: com.cy.ui.jfx.Main
     * 	at javafx.application.Application.launch(Application.java:260)
     * 	at com.cy.ui.jfx.Main.main(Main.java:60)
     * */
    private static void main(String[] args) {
        launch(args);
    }
}
