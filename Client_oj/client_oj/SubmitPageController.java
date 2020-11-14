/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_oj;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author USER
 */
public class SubmitPageController implements Initializable {
    
    
    public File file;
    public FileInputStream fis;
    public BufferedInputStream bis;
    
    @FXML
    public ComboBox<String> LanguageBox;
    public ComboBox<String> ProblemChoiceBox;
    public Button submitButton;
    public TextArea codeArea;
    public Button dashBoardbutton;
    public Button submissionPageButton;
    
    @FXML
    private Button standingsPagebutton;
    /*
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
           
        try{
            String request="Sendlist";
            ConnectionEstablishController.os.writeObject(request);
            ArrayList<String>obj=(ArrayList<String>)ConnectionEstablishController.is.readObject();
            ProblemChoiceBox.getItems().addAll(obj);
        }catch(Exception e)
        {
            System.out.println("Error");
        }
        LanguageBox.getItems().addAll("C","C++");
  
    }    
    
    public void submissionPageLoad(ActionEvent event)
    {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("SubmissionPage2.fxml"));
        Scene scene=submitButton.getScene();
        Stage stage=(Stage)scene.getWindow();
        try{
        Parent root=loader.load();
        scene=new Scene(root);
        stage.setScene(scene);
        }catch(Exception e)
        {
            System.out.println("Error1");
        }
    }
    
    public void dashBoardPageload(ActionEvent event){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("DashboardPage2.fxml"));
        //Stage stage=(Stage)label.getScene().getWindow();
        Scene scene=submitButton.getScene();
        Stage stage=(Stage)scene.getWindow();
        try{
        Parent root=loader.load();
        scene=new Scene(root);
        stage.setScene(scene);
        //stage.show();
        }catch(Exception e)
        {
            
        }
    }
    
    public void submit(ActionEvent event)
    {       
        try{
            String request="Submit"; 
            String problemname=ProblemChoiceBox.getValue();
            String language=LanguageBox.getValue();
            //System.out.println(problemname+" "+language);
            String t=codeArea.getText();
            if(t.equals("")||language==null||problemname==null)
            {
                FXMLLoader loader=new FXMLLoader(getClass().getResource("AlertScene.fxml"));
                Parent root=loader.load();
                Scene scene=new Scene(root);
                Stage stage=new Stage();
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                return;
            }
            byte[]array=t.getBytes();
            ConnectionEstablishController.os.writeObject(request);
            ConnectionEstablishController.os.writeObject(language);
            ConnectionEstablishController.os.writeObject(problemname);
            ConnectionEstablishController.os.writeObject(array);
        }catch(Exception e)
        {   
            System.out.println("Error");
        }
        
        
        /*
        String path="G:\\Java Projects2\\Client_oj\\Codes";
        File directory=new File(path);
        if(!directory.exists())
            directory.mkdir();
        FileOutputStream fos=null;
        try{
            fos=new FileOutputStream(path+"\\"+"Problem1.c");
            BufferedOutputStream bos=new BufferedOutputStream(fos);
            byte[] bytes=t.getBytes();
            bos.write(bytes);
            bos.close();
            fos.close();
            
        
        }catch(Exception e)
        {
        
        }
        //String t=codeArea.getText();
        //System.out.println(to);
        //System.out.println(t);
            /*
                try{
            socket=new Socket("localhost",12345);
            file=new File("G:\\Java Projects2\\Client_oj\\Code\\testcode3.c");
            byte[]array=new byte[(int)file.length()];
            fis=new FileInputStream(file);
            bis=new BufferedInputStream(fis);
            //ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
            bis.read(array,0,array.length);
            os=socket.getOutputStream();
            os.write(array, 0, array.length);
            os.flush();
            //bis.close();
            //os.close();
        }catch(Exception e)
        {
            System.out.println("Error");
        }
        */
    }
    
    public void standingsPageLoad(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("StandingsPage.fxml"));
        //Stage stage=(Stage)label.getScene().getWindow();
        Scene scene=submitButton.getScene();
        Stage stage=(Stage)scene.getWindow();
        try{
        Parent root=loader.load();
        scene=new Scene(root);
        stage.setScene(scene);
        //stage.show();
        }catch(Exception e)
        {
            
        }
    
    }

}


