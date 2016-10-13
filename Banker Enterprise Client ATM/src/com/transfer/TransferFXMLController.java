/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transfer;

import com.ejb.entity.Customer;
import com.ejb.remoteInterface.CustomerRemote;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * FXML Controller class
 *
 * @author STEINACOZ-PC
 */
public class TransferFXMLController implements Initializable {

    @FXML
    private Button searchButton;
    @FXML
    private TextField pinSearchTextField;
    @FXML
    private TextField amtTextField;
    @FXML
    private Label statusLabel;
    @FXML
    private Button pinPaneBtn;
    @FXML
    private Label newBalanceLabel;
    @FXML
    private Label oldBalanceLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Button exitBtn;
    @FXML
    private Button dashBoardPaneBtn;
    @FXML
    private Button transferBtn;
    @FXML
    private TextField acctNumTextField;
    
     private  Properties props;
 private   InitialContext ctx = null;
  private   CustomerRemote customerBean;
    @FXML
    private Button withdrawPaneBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void searchActButton(ActionEvent event) {
        
        if(pinSearchTextField.getText().equals("") || pinSearchTextField.getText().isEmpty() ){
           this.statusLabel.setText("Enter Valid pin to continue");
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
    customerBean.searchByAcct(pinSearchTextField.getText().trim());
    oldBalanceLabel.setText("N" + String.valueOf(customerBean.getBal()));
        }
    }


    @FXML
    private void pinPaneActBtn(ActionEvent event)throws IOException {
        Parent root1 = FXMLLoader.load(getClass().getResource("/com/changepin/changePinFXML.fxml"));
      
        Scene scene1 = new Scene(root1);
        Stage stage1 = new Stage();
      //  scene1.getStylesheets().add(this.getClass().getResource("cool_skin.css").toExternalForm());
        stage1.setScene(scene1);
        stage1.show();
    
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
    private void transferActBtn(ActionEvent event) {
        
              if(pinSearchTextField.getText().equals("")){
           this.statusLabel.setText("Enter Valid Pin to continue");
           this.statusLabel.setStyle("-fx-text-fill: #ff0000");
        }else if(amtTextField.getText().equals("")){
           this.statusLabel.setText("Enter Valid Amount to continue");
           this.statusLabel.setStyle("-fx-text-fill: #ff0000"); 
        }else if(acctNumTextField.getText().equals("")){
           this.statusLabel.setText("Enter Valid Account Number to continue");
           this.statusLabel.setStyle("-fx-text-fill: #ff0000");   
        }else if(pinSearchTextField.getText().equals("") ||amtTextField.getText().equals("")|| acctNumTextField.getText().equals("")){
          this.statusLabel.setText("Fill the required fields");
           this.statusLabel.setStyle("-fx-text-fill: #ff0000");   
        }
        else{
       /**   try{
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
          
        }**/
          
          Customer cu = new Customer();
           
        
          
          customerBean.transferMoney(cu, pinSearchTextField.getText().trim(), acctNumTextField.getText().trim(), Double.parseDouble(amtTextField.getText()));
           this.statusLabel.setText(customerBean.getStatusMsg());
          this.newBalanceLabel.setText(String.valueOf(customerBean.getNewBalance()));
          this.amountLabel.setText(this.amtTextField.getText());
    }
    }

    @FXML
    private void withdrawPaneActBtn(ActionEvent event) throws IOException{
        
             //navigates to withdraw UI
   
   Parent root = FXMLLoader.load(getClass().getResource("/com/withdraw/withdrawFXML.fxml"));

    Scene scene  = new Scene(root);
      
      Stage stage = new Stage();
       // scene.getStylesheets().add(this.getClass().getResource("cool_skin.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    
    }
    
}
