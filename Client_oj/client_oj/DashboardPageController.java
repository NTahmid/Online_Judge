/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_oj;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import myuserclasses.*;
/**
 * FXML Controller class
 *
 * @author USER
 */
public class DashboardPageController implements Initializable {
    
    
    
    public Label timeLabel;
    public Button dashBoardbutton;
    public Button submitCodebutton;
    public TableView<Problems> dashBoard;
    public TableColumn<Problems,Integer>probColumn;
    public TableColumn<Problems,String>nameColumn;
    public Button viewButton;
    public ObjectOutputStream os;
    public ObjectInputStream is;
    public TextArea codeArea;
    public Button submissionPageButton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        String request="Problemlist";
        try{
            ConnectionEstablishController.os.writeObject(request);
            ArrayList<Problems>problems=(ArrayList<Problems>)ConnectionEstablishController.is.readObject();
            probColumn.setCellValueFactory(new PropertyValueFactory<>("problemno"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("problemname"));
            ObservableList<Problems>v=FXCollections.observableArrayList(problems);
            dashBoard.setItems(v);
            String time="";
            time=(String)ConnectionEstablishController.is.readObject();
            timeLabel.setText(time);
        }catch(Exception e)
        {
        
        }
    }    
    
    public void viewProblem(ActionEvent event)
    {   
        
        Problems selected=dashBoard.getSelectionModel().getSelectedItem();
        String problemname=selected.getProblemname();
        String request="View"+problemname;
        try{
        
        ConnectionEstablishController.os.writeObject(request);
        String problemstatement=(String)ConnectionEstablishController.is.readObject();
        codeArea.clear();
        codeArea.setText(problemstatement);
        }catch(Exception e)
        {
            System.out.println("Error");
        }
        //System.out.println(selected.getName());
    }
    
    public void SubmitPageLoad(ActionEvent event)
    {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("SubmitPage.fxml"));
        
        Scene scene=viewButton.getScene();
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
    
    public void SubmissionPageLoad(ActionEvent event){
    
        FXMLLoader loader=new FXMLLoader(getClass().getResource("SubmissionPage2.fxml"));
        
        Scene scene=viewButton.getScene();
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
    
    public void StandingsPageLoad(ActionEvent event)
    {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("StandingsPage.fxml"));
        Scene scene=viewButton.getScene();
        Stage stage=(Stage)scene.getWindow();
        
            Parent root;
            try {
                root = loader.load();
                scene=new Scene(root);
            } catch (IOException ex) {
                Logger.getLogger(DashboardPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            stage.setScene(scene);
        }
    }


