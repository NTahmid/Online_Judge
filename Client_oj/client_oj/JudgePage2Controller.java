/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_oj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class JudgePage2Controller implements Initializable {

    private int problemNo;
    private int step;
    @FXML
    private Button fileChooseButton;
    @FXML
    private Button finishButton;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        step=0;
        try{
            String k=(String)ConnectionEstablishController.is.readObject();
            problemNo=Integer.parseInt(k);
            System.out.println(problemNo);
        }catch(Exception e)
        {
            
        }
    }    
    public void openFiles(ActionEvent event) throws FileNotFoundException
    {
        FileChooser filechooser=new FileChooser();
        Scene scene=fileChooseButton.getScene();
        Stage stage=(Stage)scene.getWindow();
        File chosenFile=filechooser.showOpenDialog(stage);
        FileReader fileReader=new FileReader(chosenFile);
        Scanner sc=new Scanner(fileReader);
        String fileContent="";
        while(sc.hasNextLine())
        {
            fileContent+=sc.nextLine()+"\n";
        }
        byte[]b=fileContent.getBytes();
        try{
            ConnectionEstablishController.os.writeObject(b);
            ConnectionEstablishController.os.writeObject(chosenFile.getName());
        }catch(Exception e)
        {
            System.out.println("Exception "+e);
        }
        if(problemNo!=0)
        {
            if(fileChooseButton.getText().equals("Choose ProblemFile"))
            {
                fileChooseButton.setText("Choose InputFile");
                step++;
            }
            else if(fileChooseButton.getText().equals("Choose InputFile"))
            {
                fileChooseButton.setText("Choose OutputFile");
                step++;
            }   
            else if(fileChooseButton.getText().equals("Choose OutputFile"))
            {
                fileChooseButton.setText("Choose ProblemFile");
                step=0;
                problemNo--;
            }
        }
        if(problemNo==0)
        {
            finishButton.setVisible(true);
            finishButton.setDisable(false);
            fileChooseButton.setVisible(false);
            fileChooseButton.setVisible(false);
        }  
    }
    
    public void closeAction(ActionEvent event){
        Stage stage=(Stage)fileChooseButton.getScene().getWindow();
        stage.close();
    }
    
    
    
    public void loginLoad(ActionEvent event)
    {
            
            FXMLLoader loader=new FXMLLoader(getClass().getResource("LoginPage.fxml"));
            //Stage stage=(Stage)label.getScene().getWindow();
            Scene scene=fileChooseButton.getScene();
            Stage stage=(Stage)scene.getWindow();
            try{
            Parent root=loader.load();
            scene=new Scene(root);
            stage.setScene(scene);
            }catch(Exception e)
            {
                System.out.println("Exception "+e);
            }
    }
}
