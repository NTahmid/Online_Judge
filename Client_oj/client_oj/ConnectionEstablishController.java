/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_oj;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class ConnectionEstablishController implements Initializable {
    
    public static Socket socket;
    public static ObjectOutputStream os;
    public static ObjectInputStream is;
    @FXML
    private TextField IPfield;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    
    void Connect(ActionEvent event) {
        
        String IP=IPfield.getText();
        try{
                if(IP.equals(""))
                    throw new Exception();
                socket=new Socket();
                socket.connect(new InetSocketAddress(IP,12345), 5000);
                os=new ObjectOutputStream(socket.getOutputStream());
                is=new ObjectInputStream(socket.getInputStream());
                FXMLLoader loader=new FXMLLoader(getClass().getResource("LoginPage.fxml"));
            //Stage stage=(Stage)label.getScene().getWindow();
                Scene scene=IPfield.getScene();
                Stage stage=(Stage)scene.getWindow();
                try{
                Parent root=loader.load();
                scene=new Scene(root);
                stage.setScene(scene);
                }catch(Exception e)
                {
                    System.out.println("Exception "+e);
                }
            }catch(Exception e)
            {
                System.out.println("Exception "+e);
                try{
                FXMLLoader loader=new FXMLLoader(getClass().getResource("ConnectionProblemAlert.fxml"));
                Parent root=loader.load();
                Scene scene=new Scene(root);
                Stage stage=new Stage();
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                IPfield.clear();
                IPfield.setPromptText("Enter IP address");
                }catch(Exception j)
                {
                    System.out.println("Exception "+j);
                }
                return;
            }
    }
}
