/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_oj;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class LoginPageController implements Initializable {
    
    
    
    public String username;
    public String password;
    public String user;
    public TextField usernameField;
    public PasswordField passwordField;
    public Button loginButton;
    public ComboBox<String> userType;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label warningLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userType.getItems().addAll("User","Judge");
    }    
    
    @FXML
    void comboBoxAction(ActionEvent event) {
        user=userType.getValue();
        if(user.equals("User"))
        {
            passwordField.setDisable(true);
            passwordField.setVisible(false);
            passwordLabel.setText("\0");
        }
        else if(user.equals("Judge"))
        {
            passwordField.setDisable(false);
            passwordField.setVisible(true);
            passwordLabel.setText("Enter Password:");
        }
    }
    
    public void Login(ActionEvent event)
    {
        password=passwordField.getText();
        username=usernameField.getText();
        user=userType.getValue();
        if(user.equals("")||(user.equals("User")&&username.equals(""))||(user.equals("Judge")&&(username.equals("")||password.equals(""))))
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
        else
        {
            FXMLLoader loader=null;
                if(user.equals("User"))
                {
                    try{
                        String request="NameChange";
                        ConnectionEstablishController.os.writeObject(request);
                        ConnectionEstablishController.os.writeObject(username);
                    }catch(Exception e)
                    {
                        System.out.println("Exception "+e);
                        return;
                    }
                
                    loader=new FXMLLoader(getClass().getResource("ViewContestPage.fxml"));
                }    //loader=new FXMLLoader(getClass().getResource("DashboardPage2.fxml"));
                else if(user.equals("Judge"))
                {
                    try{
                        String request="VerifyJudge";
                        ConnectionEstablishController.os.writeObject(request);
                        ConnectionEstablishController.os.writeObject(username);
                        ConnectionEstablishController.os.writeObject(password);
                        String verdict=(String)ConnectionEstablishController.is.readObject();
                        if(verdict.equals("Not Found"))
                        {
                            warningLabel.setText("Judge Not Found");
                            return;
                        }
                        else
                            loader=new FXMLLoader(getClass().getResource("JudgePage1.fxml"));
                    }catch(Exception e)
                    {
                        System.out.println("Exception "+e);
                        return;
                    }

                }
                    
                    
                //loader=new FXMLLoader(getClass().getResource("JudgePage1.fxml"));
                //Stage stage=(Stage)label.getScene().getWindow();
                Scene scene=usernameField.getScene();
                Stage stage=(Stage)scene.getWindow();
                Parent root=null;
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
                scene=new Scene(root);
                stage.setScene(scene);
        }


        //System.out.println(password+" "+username+" "+user);
    }
    
    
}
