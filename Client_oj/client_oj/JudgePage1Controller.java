/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_oj;

import java.awt.event.MouseEvent;
import java.net.URL;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class JudgePage1Controller implements Initializable {

    @FXML
    private TextField contestNamefield;

    @FXML
    private TextField startTimefield;

    @FXML
    private TextField contestDurationfield;

    @FXML
    private Button nextButton;
    
    @FXML
    private TextField problemNumberfield;

    @FXML
    private Button codeViewButton;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void nextAction(ActionEvent event)
    {   
        
        
        if(contestNamefield.getText().equals("")||startTimefield.getText().equals("")||contestDurationfield.getText().equals("")||problemNumberfield.getText().equals(""))
        {
            try{
                FXMLLoader loader=new FXMLLoader(getClass().getResource("AlertScene.fxml"));
                Parent root=loader.load();
                Scene scene=new Scene(root);
                Stage stage=new Stage();
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                }catch(Exception e)
                {
                
                }
                return;
    
        }
        /*
        if(!startTimefield.getText().matches("[1-31]:[1-12]:dddd:[0-24]:[0-60]:[0-60]"))
        {
            startTimefield.clear();
            return;
        }
        if(!contestDurationfield.getText().matches("ddd"))
        {
            contestDurationfield.clear();
            return;
        }
        */
        String request="ContestCreation1";
        String s=contestNamefield.getText()+"@@"+startTimefield.getText()+"@@"+contestDurationfield.getText()+"@@"+problemNumberfield.getText();
        try{
            ConnectionEstablishController.os.writeObject(request);
            ConnectionEstablishController.os.writeObject(s);
            FXMLLoader loader=new FXMLLoader(getClass().getResource("JudgePage2.fxml"));
            //Stage stage=(Stage)label.getScene().getWindow();
            Scene scene=contestNamefield.getScene();
            Stage stage=(Stage)scene.getWindow();
            
            Parent root=loader.load();
            scene=new Scene(root);
            stage.setScene(scene);
        //stage.show();
        }catch(Exception e)
        {
            System.out.println("Exception "+e);
        }
    }
    
    @FXML
    void viewAction(ActionEvent event) {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("JudgePageContestList.fxml"));
        //Stage stage=(Stage)label.getScene().getWindow();
        Scene scene=contestNamefield.getScene();
        Stage stage=(Stage)scene.getWindow();
        try{
        Parent root=loader.load();
        scene=new Scene(root);
        stage.setScene(scene);
        }catch(Exception e)
        {
            System.out.println("View Action "+e);
        }
    }
    
}
