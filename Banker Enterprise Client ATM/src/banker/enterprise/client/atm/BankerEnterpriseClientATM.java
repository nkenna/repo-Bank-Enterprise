/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banker.enterprise.client.atm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author STEINACOZ-PC
 */
public class BankerEnterpriseClientATM extends Application {
    private Stage st;
    private Scene sc;
    private Parent par;
    
    @Override
    public void start(Stage stage) throws Exception {
        st = stage;
        
        Parent root = FXMLLoader.load(getClass().getResource("DashboardFXML.fxml"));
      
        sc = new Scene(root);
        sc.getStylesheets().add(this.getClass().getResource("cool_skin.css").toExternalForm());
        st.setScene(sc);
        stage.show();
        setStag (st);
        setSce (sc);
        setPar(root);
    }
    
    public void setStag(Stage sta){
        st = sta;
    }
    
    public void setSce (Scene scc){
        sc = scc;
    }
    
    public void setPar (Parent pa){
        par = pa;
    }
    
    public Parent getPa(){
        return par;
    }
    
    public Stage getSt(){
        return st;
    }
    
    public Scene getSc(){
        return sc;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
    }
    
}
