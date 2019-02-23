package com.cy.ui.jfx;

import com.cy.util.BaseJFXApplication;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainJFXApp extends BaseJFXApplication {

    @Override
    public void baseStart(Stage primaryStage,Parent root){

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    @Override
    protected String baseSetFxmlLocation() {
        return "/sample.fxml";
    }
}
