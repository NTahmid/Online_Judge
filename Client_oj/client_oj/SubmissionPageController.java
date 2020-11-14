/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_oj;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

class VerdictThread implements Runnable{
    private ArrayList<String>k;
    VerdictThread(ArrayList<String>m)
    {
        k=m;
    }
    public void run(){
        for(String s:k)
        {
            System.out.println(s);
        }
    
    }

}

public class SubmissionPageController implements Initializable {

    
    @FXML
    private Button dashBoardbutton;

    @FXML
    private Button submitPageButton;

    @FXML
    private TableView<Verdict> VerdictTable;

    @FXML
    private TableColumn<Verdict, String> time;

    @FXML
    private TableColumn<Verdict, String> statement;

    @FXML
    private TableColumn<Verdict, String> language;

    @FXML
    private TableColumn<Verdict, String> answer;

    @FXML
    private Button refreshButton;
    
    @FXML
    private Button standingsButton;
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        String request="Verdict";
        ArrayList<Verdict>verdict=new ArrayList<>();
        Verdict v;
        try {
            ConnectionEstablishController.os.writeObject(request);
        } catch (Exception ex) {
            System.out.println("Exception1 "+ex);
        }
        
        time.setCellValueFactory(new PropertyValueFactory<>("date"));
        statement.setCellValueFactory(new PropertyValueFactory<>("problemname"));
        language.setCellValueFactory(new PropertyValueFactory<>("language"));
        answer.setCellValueFactory(new PropertyValueFactory<>("answer"));
        try {
            String g=(String)ConnectionEstablishController.is.readObject();
            if(!g.equals(""))
            {    //System.out.println(g);
            String[]f=g.split("/");
                for(String m:f)
                {
                    v=new Verdict();
                    v.setValues(m);
                    verdict.add(v);
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(SubmissionPageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SubmissionPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList<Verdict>list=FXCollections.observableArrayList(verdict);
        VerdictTable.setItems(list);
        /*
            ArrayList<Verdict>g=null;
            try {
            g=(ArrayList<Verdict>)LoginPageController.is.readObject();
            } catch (Exception ex) {
            System.out.println("Exception 2 "+ex);
            }
            for(Verdict f:g)
            System.out.println(f.toString());
            
            ObservableList<Verdict> list=FXCollections.observableArrayList(g);
            /*
            try {
            list = (ObservableList<Verdict>) LoginPageController.is.readObject();
            } catch (IOException ex) {
            Logger.getLogger(SubmissionPageController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(SubmissionPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
            */
            //VerdictTable.setItems(list);
            
            //try {
            // verdict = (ArrayList<String>)SubmitPageController.is.readObject();
            //}catch(Exception ex)
            ///{
            //    System.out.println("Exception2 "+ex);
            // }
            //System.out.println("Hehe");
            //Verdict q;
            //Verdict q=new Verdict("Accepted", "Haha", "Hoho");
//g.add(q);
//Verdict m=new Verdict("Acce", "Haha", "Hoho");
//g.add(q);
//g.add(m);
//VerdictThread l=new VerdictThread(verdict);
// Thread z=new Thread(l,"jj");
// z.start();
//try {
//z.join();
//for(String v:verdict)
// {
//   System.out.println(v);
//StringTokenizer s=new StringTokenizer(v);
//Verdict a=new Verdict();
//a.setAnswer(s.nextToken());
//a.setDate(s.nextToken());
//a.setLanguage(s.nextToken());
//a.setProblemname(s.nextToken());
//g.add(a);
//}
//for(Verdict v:g)
//System.out.println(v.toString());

//list.add(q);

//} catch (InterruptedException ex) {
//  Logger.getLogger(SubmissionPageController.class.getName()).log(Level.SEVERE, null, ex);
//}
    
    
    
    }    
    
    public void SubmitPageLoad(ActionEvent event)
    {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("SubmitPage.fxml"));
        
        Scene scene=VerdictTable.getScene();
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
    
    public void dashBoardPageload(ActionEvent event){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("DashboardPage2.fxml"));
        //Stage stage=(Stage)label.getScene().getWindow();
        Scene scene=VerdictTable.getScene();
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
    
    public void standingsPageLoad(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("StandingsPage.fxml"));
        //Stage stage=(Stage)label.getScene().getWindow();
        Scene scene=standingsButton.getScene();
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
