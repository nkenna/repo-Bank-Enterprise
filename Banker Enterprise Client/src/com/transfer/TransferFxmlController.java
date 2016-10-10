/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transfer;

import com.ejb.entity.Customer;
import com.ejb.remoteInterface.CustomerRemote;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * FXML Controller class
 *
 * @author STEINACOZ-PC
 */
public class TransferFxmlController implements Initializable {

    @FXML
    private Label acctBalance;
    @FXML
    private TextField searchTextField;
    @FXML
    private TextField amountTextField;
    @FXML
    private TextField recieverTextField;
    @FXML
    private Label newBalance;
    @FXML
    private CheckBox successCheckBox;
    @FXML
    private CheckBox failureCheckBox;
    @FXML
    private Button searchButton;
    @FXML
    private Button transferButton;
    
    
    private  Properties props;
 private   InitialContext ctx = null;
  private   CustomerRemote customerBean;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void searchAcctButton(ActionEvent event) {
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
        String getAccNum = searchTextField.getText();
        
        
        Customer customer = new Customer();
        
         customerBean.searchByAcct(customer, getAccNum);
        
     
         acctBalance.setText(String.valueOf(customerBean.getBal()));
        BufferedImage bi = null;
      try{
        
          // byte[] by = new sun.misc.BASE64Decoder().decodeBuffer(customerBean.getStringIM());
          byte[] by = javax.xml.bind.DatatypeConverter.parseHexBinary(customerBean.getStringIM());
           ByteArrayInputStream in = new ByteArrayInputStream(by);
          
           bi = ImageIO.read(in);
          
          in.close();
      }catch(IOException e){
           e.getMessage();
       }
         Image im = SwingFXUtils.toFXImage(bi, null);
//        imView.setImage(im);
    }

    @FXML
    private void transferAcctButton(ActionEvent event) {
        
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
        
         
        
        
        Customer customer = new Customer();
        
         customerBean.searchByAcct(customer, recieverTextField.getText());
         
         
           
               
         
           customerBean.transferMoney(searchTextField.getText(), recieverTextField.getText(), Double.parseDouble(amountTextField.getText()));
           newBalance.setText(String.valueOf(customerBean.getNewBalance()));
           String stat = customerBean.getMsg();
           
           if (stat.equals("success")){
               this.successCheckBox.setSelected(true);
                 this.failureCheckBox.setSelected(false);
           }else if(stat.equals("failure")){
               this.failureCheckBox.setSelected(true);
               this.successCheckBox.setSelected(false);
           }
           
        
    }
    
    }
