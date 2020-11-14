/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myuserclasses;

import java.io.Serializable;
import java.util.Date;



public class Verdict implements Serializable{
    private String date;
    private String answer;
    
    private String problemname;
    private String language;
    public Verdict(String problemname,String language)
    {
        answer="In queue";
        
        this.problemname=problemname;
        Date date=new Date();
        this.date=date.toString();
        this.language=language;
    }
    public Verdict()
    {
        answer="";
        problemname="";
        date="";
        language="";
    }
    public Verdict(String answer,String problemname,String language)
    {
        this.answer=answer;
        
        this.problemname=problemname;
        Date date=new Date();
        this.date=date.toString();
        this.language=language;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getProblemname() {
        return problemname;
    }

    public void setProblemname(String problemname) {
        this.problemname = problemname;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String toString()
    {
        return answer+","+date+","+language+","+problemname;
    }
    public void setValues(String s)
    {
        String []val=s.split(",");
        answer=val[0];
        date=val[1];
        language=val[2];
        problemname=val[3];
    }

}

