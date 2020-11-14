/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myuserclasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;


public class Standings implements Serializable{
    private String contestName;
    private String contestant;
    private int problemAmount;
    private Integer totalSum;
    private Integer[]problemScores;
    private int[]attempts;
    private boolean[]solved;
    private Integer numberSolved;
    public Standings()
    {
        contestant="";
        problemAmount=0;
        totalSum=0;
        numberSolved=0;
    }

    public Standings(String contestant,int problemAmount)
    {
        this.contestant=contestant;
        this.problemAmount=problemAmount;
        //Random rand=new Random();
        problemScores=new Integer[problemAmount] ;
        for(int i=0;i<problemAmount;i++)
        {
            problemScores[i]=0;
        }
        
        attempts=new int[problemAmount];
        solved=new boolean[problemAmount];
        /*
        for(int i=0;i<problemAmount;i++)
        {
            problemScores.add(rand.nextInt(5000)+200);
        }
       */ 
       //totalSum=rand.nextInt(4000)+1000;
    }
    public Standings(Standings clone)
    {
        contestName=clone.getContestName();    
        contestant=clone.getContestant();
        problemAmount=clone.getProblemAmount();
        totalSum=clone.getTotalSum();
        problemScores=new Integer[clone.getProblemAmount()];
        int i=0;
        for(Integer g:clone.getProblemScores())
        {
            problemScores[i]=g;
            i++;
        }
        problemScores=clone.getProblemScores();
        attempts=new int[clone.getProblemAmount()];
        i=0;
        for(Integer g:clone.getAttempts())
        {
            attempts[i]=g;
            i++;
        }
        attempts=clone.getAttempts();
        solved=new boolean[clone.getProblemAmount()];
        i=0;
        for(boolean b:clone.getSolved())
        {
            solved[i]=b;
            i++;
        }
        numberSolved=clone.getNumberSolved();
        
    }
    
    
    public void updateSolved(int idx)
    {
        solved[idx]=true;
        if(2550-50*attempts[idx]<50)
            problemScores[idx]=50;
        else
            problemScores[idx]=2550-50*attempts[idx];
        int sum=0;
        for(int i=0;i<problemScores.length;i++)
        {
            sum+=problemScores[i];
        }
        totalSum=sum;
        numberSolved++;
    }

    public int getNumberSolved() {
        return numberSolved;
    }

    public void setNumberSolved(int numberSolved) {
        this.numberSolved = numberSolved;
    }
    public void updateAttempts(int idx)
    {
        if(!solved[idx])
            attempts[idx]++;

    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }
    
    public Integer[] getProblemScores() {
        return problemScores;
    }

    public void setProblemScores(Integer[] problemScores) {
        this.problemScores = problemScores;
    }

    public int[] getAttempts() {
        return attempts;
    }

    public void setAttempts(int[] attempts) {
        this.attempts = attempts;
    }

    public boolean[] getSolved() {
        return solved;
    }

    public void setSolved(boolean[] solved) {
        this.solved = solved;
    }
    
    
    public String getContestant() {
        return contestant;
    }

    public void setContestant(String contestant) {
        this.contestant = contestant;
    }

    public Integer getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }

    public Integer getProblemAmount() {
        return problemAmount;
    }

    public void setProblemAmount(int problemAmount) {
        this.problemAmount = problemAmount;
        attempts=new int[problemAmount];
        solved=new boolean[problemAmount];
        problemScores=new Integer[problemAmount];
        for(int i=0;i<problemAmount;i++)
            problemScores[i]=0;
    }
    
    public Integer getElement(int index)
    {
        return problemScores[index];
    }
   
 }