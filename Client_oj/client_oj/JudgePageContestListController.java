/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_oj;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class JudgePageContestListController implements Initializable {

    @FXML
    private ListView<String> contestList;
    @FXML
    private Button selectContestButton;

    @FXML
    private Label header;

    @FXML
    private Button selectContestantButton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contestList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        String request="Send Contest List";
        try{
            ConnectionEstablishController.os.writeObject(request);
        }catch(Exception e)
        {
            System.out.println("Exception "+e);
        }
        ArrayList<String>list=new ArrayList<>();
        try{
            list=(ArrayList<String>)ConnectionEstablishController.is.readObject();
            ObservableList<String>oblist=FXCollections.observableArrayList(list);
            contestList.setItems(oblist);
        }catch(Exception e)
        {
            System.out.println("Exception "+e);
        }
    }    
    @FXML
    void getContestants(ActionEvent event) {
        String contest=contestList.getSelectionModel().getSelectedItem();
        System.out.println(contest);
        String request="Send Contestants";
        try{
            ConnectionEstablishController.os.writeObject(request);
            ConnectionEstablishController.os.writeObject(contest);
        }catch(Exception e)
        {
            System.out.println("Exception "+e);
            return;
        }
        ArrayList<String>contestantList=new ArrayList<>();
        try{
            contestantList=(ArrayList<String>)ConnectionEstablishController.is.readObject();
            ObservableList<String>oblist=FXCollections.observableArrayList(contestantList);
            contestList.setItems(oblist);
            header.setText("List of Contestants");
            selectContestButton.setVisible(false);
            selectContestButton.setDisable(true);
            selectContestantButton.setVisible(false);
            selectContestantButton.setDisable(true);
        }catch(Exception e)
        {
            System.out.println("Exception "+e);
        }
    }
    @FXML
    void getCode(ActionEvent event) {
        String request="PrepareCode";
        String contestant=contestList.getSelectionModel().getSelectedItem();
        try{
            ConnectionEstablishController.os.writeObject(request);
            ConnectionEstablishController.os.writeObject(contestant);
        }catch(Exception e)
        {
            System.out.println("Exception "+e);
            return;
        }
        
        FXMLLoader loader=new FXMLLoader(getClass().getResource("SubmissionPage2.fxml"));
        
        Scene scene=contestList.getScene();
        Stage stage=(Stage)scene.getWindow();
        try{
        Parent root=loader.load();
        scene=new Scene(root);
        stage.setScene(scene);
        //stage.show();
        }catch(Exception e)
        {
            System.out.println("Exception "+e);
        }
    }

}
