/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.update;

import com.ejb.remoteInterface.CustomerRemote;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author STEINACOZ-PC
 */
public class UpdateMainAcct extends Application {
    
     Properties props;
    InitialContext ctx = null;
     CustomerRemote customerBean;
     
     Stage stage;

    public UpdateMainAcct() {
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
       
    }
    
    
    
    @Override
    public void start(Stage primaryStage) throws IOException {
      
        
        
        
          // Create the FXMLLoader
        FXMLLoader loader = new FXMLLoader();
         String fxmlDocPath = "src/com/update/UpdateFxml.fxml";
         
         FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
          // Create the Pane and all Details
        AnchorPane root = (AnchorPane) loader.load(fxmlStream);
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Update Account Form");
      primaryStage.setScene(scene);
       // primaryStage.setFullScreen(true);
      //  primaryStage.setMaximized(true);
        primaryStage.show();
        setStage(primaryStage);
        
    }
    
   public  CustomerRemote getRemote(){
        return customerBean;
    }
   
   public void setStage(Stage s){
       this.stage = s;
   }
   
   public Stage getStage(){
       return stage;
   }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        UpdateMainAcct uma = new UpdateMainAcct();
        uma.getRemote();
    }
    
}
