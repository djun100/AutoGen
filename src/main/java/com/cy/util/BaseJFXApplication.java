package com.cy.util;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
/**
 * 开发过程中的坑：
 * launch(class,args);//launch只能调用一次
 * */
public abstract class BaseJFXApplication<T> extends Application {
    private FXMLLoader loader;
    @Override
    public void start(Stage primaryStage) throws Exception{
        // the wumpus doesn't leave when the last stage is hidden.
        Platform.setImplicitExit(false);

        loader = new FXMLLoader();
        URL url=getClass().getResource(baseSetFxmlLocation());
        loader.setLocation(url);
        loader.setClassLoader(getClass().getClassLoader());
        Parent root = (Parent)loader.load();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("trace -- > 监听到窗口关闭");
            }
        });
        baseStart(primaryStage,root);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("trace -- > stop()");
    }

    public FXMLLoader baseGetFxmlLoader() {
        return loader;
    }

    public <T> T baseGetController(){
        T controller = loader.getController();
        return controller;
    }

    public void baseStart() {
        try {
            // Because we need to init the JavaFX toolkit - which usually Application.launch does
            // I'm not sure if this way of launching has any effect on anything
            new JFXPanel();

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // Your class that extends Application
                    initialize();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
