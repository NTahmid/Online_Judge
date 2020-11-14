/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myuserclasses;
    
import java.io.Serializable;

public class Problems implements Serializable{
    private String problemname;
    private String infofilepath;
    private String inputfilepath;
    private String outputfilepath;
    private int problemno;
    
    public Problems()
    {
        problemname="";
        infofilepath="";
        inputfilepath="";
        outputfilepath="";
        problemno=0;
        
    }
    public Problems(String problemname,String infofilepath,String inputfilepath,String outputfilepath,int problemno)
    {
        this.problemname=problemname;
        this.infofilepath=infofilepath;
        this.outputfilepath=outputfilepath;
        this.inputfilepath=inputfilepath;
        this.problemno=problemno;
    }

    public int getProblemno() {
        return problemno;
    }

    public void setProblemno(int problemno) {
        this.problemno = problemno;
    }

    public String getProblemname() {
        return problemname;
    }

    public void setProblemname(String problemname) {
        this.problemname = problemname;
    }

    public String getInfofilepath() {
        return infofilepath;
    }

    public void setInfofilepath(String infofilepath) {
        this.infofilepath = infofilepath;
    }

    public String getInputfilepath() {
        return inputfilepath;
    }

    public void setInputfilepath(String inputfilepath) {
        this.inputfilepath = inputfilepath;
    }

    public String getOutputfilepath() {
        return outputfilepath;
    }

    public void setOutputfilepath(String outputfilepath) {
        this.outputfilepath = outputfilepath;
    }

    public String toString()
    {
        return problemname+"@"+infofilepath+"@"+inputfilepath+"@"+outputfilepath+"@"+problemno;
    }

}