/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.changepin;

import com.ejb.remoteInterface.CustomerRemote;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * FXML Controller class
 *
 * @author STEINACOZ-PC
 */
public class ChangePinFXMLController implements Initializable {

    @FXML
    private TextField acctSearchTextField;
    @FXML
    private TextField oldTextField;
    @FXML
    private Button Retrieve;
    @FXML
    private Label statusLabel;
    @FXML
    private Button transferPaneBtn;
    @FXML
    private Button exitBtn;
    @FXML
    private Button dashBoardPaneBtn;
    @FXML
    private TextField newTextField;
    @FXML
    private TextField verifyTextField;
    @FXML
    private Button changeBtn;
  private  Properties props;
 private   InitialContext ctx = null;
  private   CustomerRemote customerBean;
  int check;
    @FXML
    private Label invalidLabel;
    @FXML
    private Button withdrawBtn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void retrieveActBtn(ActionEvent event) {
        if (this.acctSearchTextField.getText().isEmpty()){
             this.statusLabel.setText("Enter Valid email to continue");
           this.statusLabel.setStyle("-fx-text-fill: #ff0000");
        }else{
             try{
            props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
            props.put(Context.PROVIDER_URL, "http-remoting://localhost:8050");
            props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            props.put(Context.SECURITY_PRINCIPAL, "steinacoz");
            props.put(Context.SECURITY_CREDENTIALS, "nkenna007");
            props.put("jboss.naming.client.ejb.context", true);
           
            ctx = new InitialContext(props);
            
             
 customerBean = (CustomerRemote) ctx.lookup("Banker_Enterprise-ejb/CustomerSessionBean!com.ejb.remoteInterface.CustomerRemote");
     
        }catch(NamingException ex){
            ex.printStackTrace();
          
        }
             
             customerBean.retrievePin(this.acctSearchTextField.getText().trim());
              this.statusLabel.setText(customerBean.getStatusMsg());
        }
    }


    @FXML
    private void transferPaneActBtn(ActionEvent event) throws IOException {
         //navigates to transfer UI
   
   Parent root = FXMLLoader.load(getClass().getResource("/com/transfer/transferFXML.fxml"));

    Scene scene  = new Scene(root);
      
      Stage stage = new Stage();
       // scene.getStylesheets().add(this.getClass().getResource("cool_skin.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    
    }

    @FXML
    private void exitActBtn(ActionEvent event) {
    }

    @FXML
    private void dashboardPaneActBtn(ActionEvent event) throws IOException {
         //navigates to Dashboard UI
   
   Parent root = FXMLLoader.load(getClass().getResource("/banker/enterprise/client/atm/DashboardFXML.fxml"));

    Scene scene  = new Scene(root);
      
      Stage stage = new Stage();
       // scene.getStylesheets().add(this.getClass().getResource("cool_skin.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    

    @FXML
    private void changeActBtn(ActionEvent event) {
        if (this.newTextField.getCharacters().length() < 6){
        this.statusLabel.setText("new Pin must be greater than 6 digits");
           this.statusLabel.setStyle("-fx-text-fill: #ff0000");
        }else{
             try{
            props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
            props.put(Context.PROVIDER_URL, "http-remoting://localhost:8050");
            props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            props.put(Context.SECURITY_PRINCIPAL, "steinacoz");
            props.put(Context.SECURITY_CREDENTIALS, "nkenna007");
            props.put("jboss.naming.client.ejb.context", true);
           
            ctx = new InitialContext(props);
            
             
 customerBean = (CustomerRemote) ctx.lookup("Banker_Enterprise-ejb/CustomerSessionBean!com.ejb.remoteInterface.CustomerRemote");
     
        }catch(NamingException ex){
            ex.printStackTrace();
          
        }
        
        customerBean.changePin(oldTextField.getText().trim(), newTextField.getText().trim(), verifyTextField.getText().trim());
   statusLabel.setText(customerBean.getStatusMsg());
    }
    }

   
    @FXML
    private void withdrawPaneActBtn(ActionEvent event)throws IOException {
        
         //navigates to withdraw UI
   
   Parent root = FXMLLoader.load(getClass().getResource("/com/withdraw/withdrawFXML.fxml"));

    Scene scene  = new Scene(root);
      
      Stage stage = new Stage();
       // scene.getStylesheets().add(this.getClass().getResource("cool_skin.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    
    
    
}