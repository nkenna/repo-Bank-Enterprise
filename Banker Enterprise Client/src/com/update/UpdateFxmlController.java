/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.update;

import com.ejb.entity.Customer;
import com.ejb.remoteInterface.CustomerRemote;
import java.awt.Desktop;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JFileChooser;

/**
 * FXML Controller class
 *
 * @author STEINACOZ-PC
 */
public class UpdateFxmlController implements Initializable {
    

    @FXML
    private TextField searchTextField;
    @FXML
    private Button searchButton;
    @FXML
    private Label getfname;
    @FXML
    private Label getmname;
    @FXML
    private Label getlname;
    @FXML
    private Label getdod;
    @FXML
    private Label getemail;
    @FXML
    private Label getcountry;
    @FXML
    private Label gettype;
    @FXML
    private Label getphone;
    @FXML
    private TextArea getaddress;
    @FXML
    private Label getsex;
   // private ImageView imv;
    
      Properties props;
    InitialContext ctx = null;
     CustomerRemote customerBean;

    public UpdateFxmlController() {
          
     
    }
     
     

    /**
     * Initializes the controller class.
     */
    
    UpdateMainAcct uma = new UpdateMainAcct();
    @FXML
    private Label imgLabel;
    @FXML
    private TextField update_firstname;
    @FXML
    private TextField updateMiddlename;
    @FXML
    private TextField updateLastname;
    @FXML
    private DatePicker updateDod;
    @FXML
    private TextField updateEmail;
    @FXML
    private TextArea updateAddress;
    @FXML
    private TextField updateNationality;
    @FXML
    private TextField updatePhone;
    @FXML
    private TextField UpdateImage;
    @FXML
    private CheckBox updateMale;
    @FXML
    private CheckBox updateFemale;
    @FXML
    private Button updateBrowse;
    @FXML
    private Button updateButton;
    @FXML
    private CheckBox updateSaving;
    @FXML
    private CheckBox updateCurrent;
    @FXML
    private CheckBox updateFixedDeposit;
    
    String updateSex = null;
    String updateAcctType = null;
    File file;
    String fname;
    String path;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
    }    

    @FXML
    private void whenClicked(ActionEvent event) {
    }

    @FXML
    private void searchAcctBtn(ActionEvent event) {
        
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
     
        
        
         getfname.setText(customerBean.getFirstN());
         this.getmname.setText(customerBean.getMiddleN());
         this.getlname.setText(customerBean.getLastN());
         this.getaddress.setText(customerBean.getAD());
         this.getcountry.setText(customerBean.getNA());
         this.getdod.setText(customerBean.getD().toString());
         this.getemail.setText(customerBean.getEM());
         this.gettype.setText(customerBean.getTY());
         this.getphone.setText(customerBean.getPH());
         this.getsex.setText(customerBean.getGender());
         
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
        // imv.setImage(im);
        imgLabel.setGraphic(new ImageView(im));
         
    }

    @FXML
    private void maleAcctCheck(ActionEvent event) {
       
            updateFemale.setSelected(false);
          
    }

    @FXML
    private void femaleAcctCheck(ActionEvent event) {
            updateMale.setSelected(false);
    }

    @FXML
    private void updateAcctBtn(ActionEvent event) {
        
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
        LocalDate ld = updateDod.getValue();
       String dd= ld.toString();
               Date d ;//= new Date(Date.valueOf(ld));
    d = Date.valueOf(dd);
                  
                    
                    if(this.updateMale.isSelected()){
                        updateSex = "male";
                    }else if (this.updateFemale.isSelected()){
                        updateSex = "female";
                    }
                           
                    if(this.updateSaving.isSelected()){
                        updateAcctType = "saving";
                    }else if (this.updateCurrent.isSelected()){
                        updateAcctType = "current";
                    }else if (this.updateFixedDeposit.isSelected()){
                        updateAcctType = "fixed deposit";                 
    }
                    System.out.println(update_firstname.getText());
             System.out.println(getFile());
             System.out.println(d);     
customerBean.updateAcct(customer, searchTextField.getText(), update_firstname.getText(), updateMiddlename.getText(),updateLastname.getText(),updateAcctType, updateSex,updateNationality.getText(),updateAddress.getText(),updateEmail.getText(),updatePhone.getText(), d ,getFile());
    }

    @FXML
    private void savingAcctcheck(ActionEvent event) {
        updateCurrent.setSelected(false);
        updateFixedDeposit.setSelected(false);
    }

    @FXML
    private void currentAcctCheck(ActionEvent event) {
        updateSaving.setSelected(false);
        updateFixedDeposit.setSelected(false);
    }

    @FXML
    private void fixedAcctCheck(ActionEvent event) {
        updateSaving.setSelected(false);
        updateCurrent.setSelected(false);
    }

    @FXML
    private void browseUpdateAcctButton(ActionEvent event) {
        
        //gets an Image file
        JFileChooser jfc = new JFileChooser();
        jfc.createImage(250, 250);
        jfc.setCurrentDirectory(new java.io.File("."));
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jfc.setAcceptAllFileFilterUsed(false);
        
        if(jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            File f = jfc.getSelectedFile();
            fname = f.getName();
            path = f.getAbsolutePath();
            UpdateImage.setText(fname);
            setFile(f);
            
        }
    }
    
    private void setFile (File f){
        this.file = f;
    } 
    
    private File getFile (){
       
        return file;
    }
    
}
