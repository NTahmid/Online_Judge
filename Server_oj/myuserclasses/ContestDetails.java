/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myuserclasses;

import java.io.Serializable;


public class ContestDetails implements Serializable{
    public String contestName;
    public String startTime;
    public String endTime;
    
    public ContestDetails(String contestName,String startTime)
    {
        this.contestName=contestName;
        this.startTime=startTime;
        
    }
    public ContestDetails(String contestName,String startTime,String endTime)
    {
        this.startTime=startTime;
        this.contestName=contestName;
        this.endTime=endTime;
    }
    public ContestDetails(ContestDetails clone)
    {
        startTime=clone.getStartTime();
        contestName=clone.getContestName();
        endTime=clone.getEndTime();
    }
    
    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
}
