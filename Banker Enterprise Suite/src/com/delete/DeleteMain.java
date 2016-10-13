/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delete;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author STEINACOZ-PC
 */
public class DeleteMain extends Application  {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
     
        
          // Create the FXMLLoader
        FXMLLoader loader = new FXMLLoader();
         String fxmlDocPath = "src/com/delete/deleteAcctFxml.fxml";
         
         FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
          // Create the Pane and all Details
        AnchorPane root = (AnchorPane) loader.load(fxmlStream);
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Delete Account Form");
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
