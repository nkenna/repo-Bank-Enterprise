/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cc;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author STEINACOZ-PC
 */
public class CustomerCareFXMLController implements Initializable {

    @FXML
    private Button createActButton;
    @FXML
    private Button updateActButton;
    @FXML
    private Button accountDetailsActButon;
    @FXML
    private Button mainMenuButton;
    @FXML
    private Button deleteButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void createActAction(ActionEvent event) throws IOException{
  Parent root1 = FXMLLoader.load(getClass().getResource("/com/newacct/createNewFXML.fxml"));
      
        Scene scene1 = new Scene(root1);
        Stage stage1 = new Stage();
      //  scene1.getStylesheets().add(this.getClass().getResource("cool_skin.css").toExternalForm());
        stage1.setScene(scene1);
        stage1.show();
    }

    @FXML
    private void updateActionBtn(ActionEvent event) throws IOException{
        Parent root1 = FXMLLoader.load(getClass().getResource("/com/update/updateFxml.fxml"));
      
        Scene scene1 = new Scene(root1);
        Stage stage1 = new Stage();
      //  scene1.getStylesheets().add(this.getClass().getResource("cool_skin.css").toExternalForm());
        stage1.setScene(scene1);
        stage1.show();
    }

    @FXML
    private void accountDetailsAction(ActionEvent event) throws IOException {
        Parent root1 = FXMLLoader.load(getClass().getResource("/com/details/DetailsAcctFxml.fxml"));
      
        Scene scene1 = new Scene(root1);
        Stage stage1 = new Stage();
      //  scene1.getStylesheets().add(this.getClass().getResource("cool_skin.css").toExternalForm());
        stage1.setScene(scene1);
        stage1.show();
    }

    @FXML
    private void mainMenuActBtn(ActionEvent event) throws IOException {
        Parent root1 = FXMLLoader.load(getClass().getResource("/banker/enterprise/suite/SuiteFXML.fxml"));
      
        Scene scene1 = new Scene(root1);
        Stage stage1 = new Stage();
      //  scene1.getStylesheets().add(this.getClass().getResource("cool_skin.css").toExternalForm());
        stage1.setScene(scene1);
        stage1.show();
    }

    @FXML
    private void deleteActBtn(ActionEvent event) throws IOException{
         Parent root1 = FXMLLoader.load(getClass().getResource("/com/delete/deleteAcctFxml.fxml"));
      
        Scene scene1 = new Scene(root1);
        Stage stage1 = new Stage();
      //  scene1.getStylesheets().add(this.getClass().getResource("cool_skin.css").toExternalForm());
        stage1.setScene(scene1);
        stage1.show();
    }
    
}
