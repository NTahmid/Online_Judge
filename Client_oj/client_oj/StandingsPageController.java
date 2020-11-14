/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_oj;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import myuserclasses.*;
/**
 * FXML Controller class
 *
 * @author USER
 */


class StandingsComparator implements Comparator<Standings>{

    @Override
    public int compare(Standings o1, Standings o2) {
        Integer g1=o1.getNumberSolved();
        Integer g2=o2.getNumberSolved();
        Integer p1=o1.getTotalSum();
        Integer p2=o2.getTotalSum();
        if(g1<g2)
            return 1;
        else if(g1>g2)
            return -1;
        else if(g1==g2&&p1<p2)
            return 1;
        else if(g1==g2&&p1>p2)
            return -1;
        else
            return 0;
    }
    


}

public class StandingsPageController implements Initializable {

    @FXML
    private TableView<Standings> standingsTable;
    
    @FXML
    private TableColumn<Standings,Integer>scoreColumn;
    
    @FXML
    private TableColumn<Standings,String> nameColumn;
    
    @FXML
    private TableView<?> standingsTable2;
    
    @FXML
    private TableColumn<Standings, Integer> solvedColumn;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Integer s=1;
        ArrayList<Standings>array=null;
        
        try{
            String request="Standings";
            ConnectionEstablishController.os.writeObject(request);
            s=(Integer)ConnectionEstablishController.is.readObject();
            array=(ArrayList<Standings>)ConnectionEstablishController.is.readObject();
            //System.out.println("Haha");
        }catch(Exception e)
        {
            System.out.println("Standings "+e);
        }
        for(Standings m:array)
        {
            System.out.println(m.getContestName());
            System.out.println(m.getContestant());
            System.out.println(m.getProblemAmount());
        }
        
        Collections.sort(array,new StandingsComparator());
        ObservableList<Standings>list=FXCollections.observableArrayList(array);
        TableColumn<Standings,Integer>[]columns;
        
        columns = new TableColumn[s];
         for(int i=0;i<s;i++)
         {
             columns[i]=new TableColumn(Integer.toString(i+1));
             final int element=i;
             
             columns[i].setCellValueFactory(cellData -> new SimpleIntegerProperty((cellData.getValue().getElement(element))).asObject());
             columns[i].setSortable(false);
             columns[i].setResizable(false);
             columns[i].setPrefWidth(83);
         }
         nameColumn.setCellValueFactory(new PropertyValueFactory<>("contestant"));
         scoreColumn.setCellValueFactory(new PropertyValueFactory<>("totalSum"));
         solvedColumn.setCellValueFactory(new PropertyValueFactory<>("numberSolved"));
         standingsTable.getColumns().addAll(columns);
         standingsTable.setItems(list);
         
    }    
    
    public void dashBoardPageload(ActionEvent event){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("DashboardPage2.fxml"));
        //Stage stage=(Stage)label.getScene().getWindow();
        Scene scene=standingsTable.getScene();
        Stage stage=(Stage)scene.getWindow();
        try{
        Parent root=loader.load();
        scene=new Scene(root);
        stage.setScene(scene);
        //stage.show();
        }catch(Exception e)
        {
            System.out.println("Exception 3"+e);
        }
    }
    
    public void SubmitPageLoad(ActionEvent event)
    {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("SubmitPage.fxml"));
        
        Scene scene=standingsTable.getScene();
        Stage stage=(Stage)scene.getWindow();
        try{
        Parent root=loader.load();
        scene=new Scene(root);
        stage.setScene(scene);
        //stage.show();
        }catch(Exception e)
        {
            System.out.println("Exception 2"+e);
        }
    }
    
    public void SubmissionPageLoad(ActionEvent event){
    
        FXMLLoader loader=new FXMLLoader(getClass().getResource("SubmissionPage2.fxml"));
        
        Scene scene=standingsTable.getScene();
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
    

