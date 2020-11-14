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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import myuserclasses.*;
/**
 * FXML Controller class
 *
 * @author USER
 */
public class ViewContestPageController implements Initializable {
    
    
    @FXML
    private Label warningLabel;
    @FXML
    private Button enterButton;
    
    @FXML
    private TableView<ContestDetails> contestDetailsTable;

    @FXML
    private TableColumn<ContestDetails, String> contestNameColumn;

    @FXML
    private TableColumn<ContestDetails, String> startTimeColumn;

    @FXML
    private TableColumn<ContestDetails, String> endTimeColumn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String request="ContestDetails";
        ArrayList<ContestDetails>details=new ArrayList<>();
        try{
            ConnectionEstablishController.os.writeObject(request);
            details=(ArrayList<ContestDetails>)ConnectionEstablishController.is.readObject();
        }catch(Exception e)
        {
            System.out.println("Exception "+e);
        }
        contestNameColumn.setCellValueFactory(new PropertyValueFactory<>("contestName"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        ObservableList<ContestDetails>list=FXCollections.observableArrayList(details);
        contestDetailsTable.setItems(list);
    
    }    
    
    @FXML
    void enterContest(ActionEvent event) {
        ContestDetails selected=contestDetailsTable.getSelectionModel().getSelectedItem();
        String request="LoadContest";
        try{
            ConnectionEstablishController.os.writeObject(request);
            ConnectionEstablishController.os.writeObject(selected);
        }catch(Exception e)
        {
            System.out.println("Exception enterContest"+e);
        }
        String answer="";
        try{
            answer=(String)ConnectionEstablishController.is.readObject();
            System.out.println(answer);
            if(answer.equals("CanEnter"))
            {
                FXMLLoader loader=new FXMLLoader(getClass().getResource("DashboardPage2.fxml"));
            //Stage stage=(Stage)label.getScene().getWindow();
                Scene scene=enterButton.getScene();
                Stage stage=(Stage)scene.getWindow();

                Parent root=loader.load();
                scene=new Scene(root);
                stage.setScene(scene);
            //stage.show();
            
            }
            else
            {
                warningLabel.setText(answer);
            }
        }catch(Exception e)
        {
            
        }
    }
    
    
}
