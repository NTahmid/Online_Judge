/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_oj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import myuserclasses.Problems;
import myuserclasses.Standings;
import myuserclasses.Verdict;

/**
 *
 * @author USER
 */
public class CompileProgram implements Runnable{
    private Verdict v;
    private String problemname;
    private String language;
    private String path;
    private Problems problem;
    private Standings standings;
    public CompileProgram(Verdict v,String problemname,String language,String path,Problems problem,Standings standings)
    {
        this.v=v;
        this.problemname=problemname;
        this.language=language;
        this.path=path;
        this.problem=problem;
        this.standings=standings;
    }
    
    public void compile()
    {   
        ProcessBuilder pb=null;
        if(language.equals("C"))
        {
            pb=new ProcessBuilder("gcc",problemname+".c","-o",problemname);
            System.out.println("Haha C");
        }
        else if(language.equals("C++"))
        {
            pb=new ProcessBuilder("g++",problemname+".cpp","-o",problemname);
            System.out.println("Haha C++");
        }
        
        pb.directory(new File(path));
        try{
        boolean f=true;
        Process p=pb.start();
        InputStream error=p.getErrorStream();
        InputStreamReader errorReader=new InputStreamReader(error);
        BufferedReader bre=new BufferedReader(errorReader);
        String line=null;
        
        while(((line=bre.readLine()))!=null)
        {
            System.out.println(line);
            f=false;
        }


        if(f)
            runcode();
        else if(!f)
        {
            synchronized(this){
                v.setAnswer("Compilation Error");
                standings.updateAttempts(problem.getProblemno()-1);
            }
            return;
        }
        }catch(IOException e)
        {
            System.out.println("Exception 2"+e);
        }
        
    }
    
    public void runcode(){
        
            
        Scanner scin=null;
        Scanner scout=null;
        try{
            scin=new Scanner(new File(problem.getInputfilepath()));
            scout=new Scanner(new File(problem.getOutputfilepath()));

            while(scin.hasNextLine())
            {   
                boolean check=false;
                Process p=null;

                p=new ProcessBuilder(path+"\\"+problemname+".exe").start();
                OutputStream os=p.getOutputStream();
                OutputStreamWriter osw=new OutputStreamWriter(os);
                BufferedWriter bw=new BufferedWriter(osw);
                InputStream in=p.getInputStream();
                InputStreamReader isr=new InputStreamReader(in);
                BufferedReader br=new BufferedReader(isr);

                bw.write(scin.nextLine());
                bw.flush();
                bw.close();
                String line="";
                TerminateProgram tp=new TerminateProgram(p,2);
                Thread t=new Thread(tp);
                String result=scout.nextLine();
                t.start();

                while((line=br.readLine())!=null)
                {
                    check=true;
                    t.join();
                    if(!line.equals(result))
                    {
                        v.setAnswer("Wrong Answer");
                        synchronized(this){
                            standings.updateAttempts(problem.getProblemno()-1);
                        }
                        return;
                    }
                }
                t.join();
                if(!check&&tp.getState())
                {
                    v.setAnswer("Time Limit Exceeded");
                    synchronized(this){
                        standings.updateAttempts(problem.getProblemno()-1);
                    }
                    return;
                }        
            }
            v.setAnswer("Accepted");
            synchronized(this){
                        standings.updateAttempts(problem.getProblemno()-1);
                        standings.updateSolved(problem.getProblemno()-1);

            }
        }catch(Exception e)
        {
                System.out.println("Exception "+e);
        }
    
    }
    
    
    @Override
    public void run() {
        compile();
    }
}
