/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deposit;

import com.ejb.entity.Customer;
import com.ejb.remoteInterface.CustomerRemote;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
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
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * FXML Controller class
 *
 * @author STEINACOZ-PC
 */
public class DepositAcctFxmlController implements Initializable {

    @FXML
    private TextField searchTextField;
    @FXML
    private Button searchAcctButton;
    @FXML
    private TextField amountTextField;
    @FXML
    private Button depositAcctButton;
    @FXML
    private CheckBox successCheckBox;
    @FXML
    private CheckBox failureCheckBox;
    @FXML
    private Label newBalance;
    @FXML
    private Label balanceLabel;
    
     private  Properties props;
 private   InitialContext ctx = null;
  private   CustomerRemote customerBean;
    private ImageView imView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void successAcctBox(ActionEvent event) {
    }

    @FXML
    private void failureAcctBox(ActionEvent event) {
    }

    @FXML
    private void searchActButton(ActionEvent event) {
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
        
     
         this.balanceLabel.setText(String.valueOf(customerBean.getBal()));
        
            int type = BufferedImage.TYPE_INT_ARGB;
        BufferedImage bi = new BufferedImage(150,150, type);
         // BufferedImage resizedImage = new BufferedImage(150, 150, type);
        Graphics2D g = bi.createGraphics();
        g.drawImage(bi, 0, 0, 150, 150, null);
        g.dispose();
        
      try{
        
             bi = ImageIO.read(new File(customerBean.getStringIM()));
      }catch(IOException e){
           e.getMessage();
       }
         Image im = SwingFXUtils.toFXImage(bi, null);
     //   imView.setImage(im);
    }

    @FXML
    private void depositActButton(ActionEvent event) {
        
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
           
           Customer cu = new Customer();
           String accountNumber = searchTextField.getText();
           int depositAmt = Integer.parseInt(amountTextField.getText());
           customerBean.depositMoney(accountNumber, depositAmt);
           this.newBalance.setText(String.valueOf(customerBean.getNewBalance()));
           String stat = customerBean.getMsg();
           
           if (stat.equals("success")){
               this.successCheckBox.setSelected(true);
           }else{
               this.failureCheckBox.setSelected(true);
           }
           
        
        
    }
    
    

    
    
}
