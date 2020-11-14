/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myuserclasses;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Contest extends ContestDetails{
    //private String contestName;
    private ArrayList<Problems>problemset;
    private String startTime;
    private String timeLength;
    //private String endTime;
    private int problemnos;
    private ArrayList<Standings> standings;
  
    public Contest(String contestName,String startTime,String timeLength,int problemnos)
    {   
        super(contestName,startTime);
        //this.contestName=contestName;
        //this.startTime=startTime;
        this.timeLength=timeLength;
        this.problemnos=problemnos;
        problemset=new ArrayList<>();
        standings=new ArrayList<>();
        DateFormat format=new SimpleDateFormat("dd/MM/yyyy/HH/mm/ss",Locale.ENGLISH);
        Date date=null;
        try {
            date = format.parse(startTime);
        } catch (ParseException ex) {
            Logger.getLogger(Contest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE,Integer.parseInt(timeLength));
        SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy/HH/mm/ss",Locale.ENGLISH);
        super.setEndTime(df.format(cal.getTime()));
        
        
    }
    public void addContestant(Standings s)
    {
        standings.add(s);   
    }
    
    public void addProblem(Problems problem)
    {
        problemset.add(problem);
    }

    public ArrayList<Problems> getProblemset() {
        return problemset;
    }

    public void setProblemset(ArrayList<Problems> problemset) {
        this.problemset = problemset;
    }

    public String getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(String timeLength) {
        this.timeLength = timeLength;
    }

    public int getProblemnos() {
        return problemnos;
    }

    public void setProblemnos(int problemnos) {
        this.problemnos = problemnos;
    }

    public ArrayList<Standings> getStandings() {
        return standings;
    }

    public void setStandings(ArrayList<Standings> standings) {
        this.standings = standings;
    }

}
