/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_oj;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import myuserclasses.*;

class ProgramThread extends Thread{
    private String name;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private ArrayList<Problems> problems;
    private ArrayList<Verdict> verdict;
    private ArrayList<Contest>contestList;
    
    private Standings current;
    ProgramThread(Socket socket,int num,ArrayList<Contest> contestList)
    {
        this.socket=socket;
        name="Programmer"+num;
        this.contestList=contestList;
        verdict=new ArrayList<>();
        //this.standings=standings;
        current=new Standings();
        current.setContestant(name);
        /*
        synchronized(this){
            standings.add(current);
        }
        */
    }

    void CreateContest()
    {
        String get="";
        try {
            get=(String)ois.readObject();
        } catch (Exception ex) {
            System.out.println("Exception CreateContestFolder "+ex);
            return;
        }
        String[]arr=get.split("@@");
        String path="E:\\"+arr[0];
        int problemNo=Integer.parseInt(arr[3]);
        File directory=new File(path);
        if(!directory.exists())
            directory.mkdir();
        try{
            oos.writeObject(arr[3]);
        }catch(Exception e)
        {
            System.out.println("Exception CreateContestFolder1 "+e);
            return;
        }
        ArrayList<Problems>p=new ArrayList<>();
        Problems temp;
        
        
        for(int i=0;i<problemNo;i++)
        {   
            temp=new Problems();
            temp.setProblemno(i+1);
            try{
                for(int j=0;j<3;j++)
                {
                    byte[]b1=(byte[])ois.readObject();
                    String name1=(String)ois.readObject();
                    FileOutputStream fos=null;
                    fos=new FileOutputStream(path+"\\"+name1);
                    BufferedOutputStream bos=new BufferedOutputStream(fos);
                    bos.write(b1);
                    bos.close();
                    fos.close();
                    System.out.println(name1);
                    String[]h=name1.split(".");
                    for(String m:h)
                        System.out.println(m);
                    
                    
                    if(j==0)
                    {   
                        int idx=name1.lastIndexOf(".");
                        {
                            if(idx>0)
                                temp.setProblemname(name1.substring(0, idx));
                        }
                        temp.setInfofilepath(path+"\\"+name1);
                    }
                    else if(j==1)
                        temp.setInputfilepath(path+"\\"+name1);
                    else if(j==2)
                        temp.setOutputfilepath(path+"\\"+name1);
                }    
                
            }catch(Exception e)
            {
                System.out.println("Exception CreateContestFolder2"+e);
                return;
            }
            p.add(temp);
        }
        
        
        Contest c=new Contest(arr[0],arr[1],arr[2],Integer.parseInt(arr[3]));
        c.setProblemset(p);
        synchronized(this){
            contestList.add(c);
        }

    }
    synchronized void sendContestDetails()
    {
        ArrayList<ContestDetails>list=new ArrayList<>();
     
        DateFormat format=new SimpleDateFormat("dd/MM/yyyy/HH/mm/ss",Locale.ENGLISH);
        for(Contest c:contestList)
        {   
            try{
            Date d=format.parse(c.getEndTime());
                System.out.println(c.getEndTime());
            Date serverTime=new Date();
                if(d.after(serverTime))
                {   
                    System.out.println("HAhahah");
                    list.add(new ContestDetails(c.getContestName(),c.getStartTime(),c.getEndTime()));
                }   
                
            } catch (ParseException ex) {
                Logger.getLogger(ProgramThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try{
            oos.writeObject(list);
        }catch(Exception e)
        {
            System.out.println("Exception:send ContestDetails "+e);
        }
            
    }
    
    void sendContestProblems()
    {   
        Contest m=null;
        DateFormat format=new SimpleDateFormat("dd/MM/yyyy/HH/mm/ss",Locale.ENGLISH);
        Date date=new Date();
        Date endTime=new Date();
        SimpleDateFormat df=new SimpleDateFormat("HH/mm/ss",Locale.ENGLISH);
        String sendTime="";
        synchronized(this)
        {   
            for(Contest g:contestList)
            {
                if(current.getContestName().equals(g.getContestName()))
                {
                    m=g;
                }    
            }
            String end=m.getEndTime();


            try {
                endTime = format.parse(end);
            } catch (ParseException ex) {
                Logger.getLogger(Contest.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        long duration=endTime.getTime()-date.getTime();
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        sendTime="Time: "+diffInMinutes;
        try{
            oos.writeObject(problems);
            oos.writeObject(sendTime);
        }catch(Exception e)
        {
            System.out.println("Exception 4"+e);
        }
    }
    
    void sendProblem(String problemname){
        
        
        Problems send=null;
        for(Problems p:problems)
        {
            if(p.getProblemname().equals(problemname))
            {
                send=p;
                break;
            }
        }
        String path=send.getInfofilepath();
        
        try{
        String s=new String(Files.readAllBytes(Paths.get(path)));
        oos.writeObject(s);

        }catch(Exception e)
        {
            System.out.println("Error "+e);
        }
    
    }
    
    void sendProblemChoice()
    {
        
        ArrayList<String>problemnames = new ArrayList<>();
        for(Problems p:problems)
        {
            problemnames.add(p.getProblemname());
        }
        try {
            oos.writeObject(problemnames);
        } catch (IOException ex) {
            Logger.getLogger(ProgramThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void changeName()
    {
        String newName="";
        try{
            newName=(String)ois.readObject();
            name=newName;
            synchronized(this){
                current.setContestant(newName);
            }
        }catch(Exception e)
        {
            System.out.println("Exception "+e);
        }
    }
    void SendVerdict()
    {   
        String s="";
        if(verdict.size()!=0)
        {
            for(Verdict v:verdict)
            {
                System.out.println(v.toString());
                s+=v.toString()+"/";
            }
            s=s.substring(0,s.length()-1);
        }
   
        try {      
            oos.writeObject(s);
 
        } catch (IOException ex) {
            System.out.println("Exception i"+ex);
        }
        
    }
    
    synchronized void SendStandings()
    {
        Integer num=current.getProblemAmount();
        Contest c=null;
        for(Contest temp:contestList)
        {
            if(current.getContestName().equals(temp.getContestName()))
            {
                c=temp;
                break;
            }
        }
        ArrayList<Standings>result=new ArrayList<>();
        for(Standings temp:c.getStandings())
        {   
            result.add(new Standings(temp));
            for(Integer s:temp.getProblemScores())
                System.out.println(s);
        }
        try {
            oos.writeObject(num);
            oos.writeObject(result);
            //oos.writeUnshared(c.getStandings());
        } catch (Exception ex) {
            System.out.println("Exception Standings "+ex);
        }
    }
    
    void LoadContest()
    {   
        ContestDetails received=null;
        try{
            received=(ContestDetails)ois.readObject();
        }catch(Exception e)
        {
            System.out.println("Exception LoadContest " +e);
            return;
        }
        DateFormat format=new SimpleDateFormat("dd/MM/yyyy/HH/mm/ss",Locale.ENGLISH);
        Date server=new Date();
        Date endTime=new Date();
        Date startTime=new Date();
        try {
            endTime=format.parse(received.getEndTime());
            startTime=format.parse(received.getStartTime());
        } catch (ParseException ex) {
            Logger.getLogger(ProgramThread.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        String request="";
        if(endTime.after(server))
        {   
            if(startTime.after(server))
                request="BeforeStart";
            else
                request="CanEnter";
        }
        else
            request="ContestEnded";
        if(request.equals("CanEnter"))
        {   
            synchronized(this)
            {
                for(Contest c:contestList)
                {
                    if(c.getContestName().equals(received.getContestName()))
                    {
                        problems=c.getProblemset();
                        current.setProblemAmount(problems.size());
                        current.setContestName(c.getContestName());
                        c.addContestant(current);
                        break;
                    }
                }
                
            }
            
        }
        try{
            oos.writeObject(request);
        }catch(Exception e)
        {
            System.out.println("Exception LoadContest"+e);
        }
    
    }
    
    void JudgeSendContestantInfo()
    {
        String contestSelected="";
        try{
            contestSelected=(String)ois.readObject();
            ArrayList<Standings>s=new ArrayList<>();
            synchronized(this)
            {
                for(Contest c:contestList)
                {
                    if(c.getContestName().equals(contestSelected))
                    {
                        s=c.getStandings();
                        break;
                    }
                }
                ArrayList<String>contestantList=new ArrayList<>();
                for(Standings a:s)
                {
                    contestantList.add(a.getContestant());
                }
                oos.writeObject(contestantList);
            }
        }catch(Exception e)
        {
            System.out.println("Exception JudgeSendContestantInfo "+e);
        }
    
    }
    void verifyJudge()
    {
        String username="";
        String password="";
        try{
            username=(String)ois.readObject();
            password=(String)ois.readObject();
            Scanner sc=new Scanner(new File("E:\\Project\\Server_oj\\JudgeInfo.txt"));
            while(sc.hasNextLine())
            {
                String[]input=sc.nextLine().split(",");
                if(username.equals(input[0])&&password.equals(input[1]))
                {
                    try{
                        String verdict="Found";
                        oos.writeObject(verdict);
                        sc.close();
                        return;
                        
                    }catch(Exception e)
                    {
                        System.out.println("Exception "+e);
                        sc.close();
                        return;
                    }
                }
            }
            String verdict="Not Found";
            oos.writeObject(verdict);       
        }catch(Exception e)
        {
            System.out.println("Exception "+e);
            
        }
    }
    
    void getCodeFiles()
    {
        
    
    }
    void JudgeSendContestInfo()
    {
        ArrayList<String>contests=new ArrayList<>();
        synchronized(this)
        {
             for(Contest c:contestList)
            {
                contests.add(c.getContestName());
            }
        }   
        try{
            oos.writeObject(contests);
        }catch(Exception e)
        {
            System.out.println("JudgeSendContestInfo "+e);
        }
    }
    
    void CreateFolder(String language,String problemname)
    {
        String path="E:\\Project\\Server_oj\\"+name;
        File directory=new File(path);
        if(!directory.exists())
            directory.mkdir();
        FileCreate(path,language,problemname);
           
    }
    void FileCreate(String path,String language,String problemname)
    {
        byte[]array;
        int c=0;
        for(Verdict a:verdict)
        {
            if(a.getProblemname().equals(problemname))
                c++;
        }
        String problemname2=problemname;
        if(c>0)
            problemname2=problemname+c;
        try{
            FileOutputStream fos=null;
            array=(byte[])ois.readObject();
            if(language.equals("C"))
                fos=new FileOutputStream(path+"\\"+problemname2+".c");
            else if(language.equals("C++"))
                fos=new FileOutputStream(path+"\\"+problemname2+".cpp");
            BufferedOutputStream bos=new BufferedOutputStream(fos);
            int bytesRead;
            bos.write(array);
            bos.close();
            fos.close();
            Contest m=null;
            DateFormat format=new SimpleDateFormat("dd/MM/yyyy/HH/mm/ss",Locale.ENGLISH);
            Date date=new Date();
            Date endTime=new Date();
            synchronized(this)
            {   
                for(Contest g:contestList)
                {
                    if(current.getContestName().equals(g.getContestName()))
                    {
                        m=g;
                    }    
                }
                String end=m.getEndTime();
                
                
                try {
                    endTime = format.parse(end);
                } catch (ParseException ex) {
                    Logger.getLogger(Contest.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            if(date.after(endTime))
            {
                return;
            }
            Verdict v=new Verdict(problemname,language);
            verdict.add(v);
            Problems k=new Problems();
            for(Problems j:problems)
            {
                if(j.getProblemname().equals(problemname))
                {
                    k=j;
                    break;
                }
            }
            CompileProgram cp=new CompileProgram(v,problemname2,language,path,k,current);
            Thread t=new Thread(cp);
            t.start();
           // compile(path,language,problemname);
        }catch(Exception e)
        {
            System.out.println("Error");
        }
    }
    
    public void run()
    {   
        try{
            oos=new ObjectOutputStream(socket.getOutputStream());
            ois=new ObjectInputStream(socket.getInputStream());
            while(true)
            {
                String request=(String)ois.readObject();
                
                if(request.equals("Problemlist"))
                {
                    sendContestProblems();
                }
                else if(request.equals("VerifyJudge"))
                {
                    verifyJudge();
                }
                else if(request.equals("NameChange"))
                {
                    changeName();
                }
                else if(request.equals("Send Contestants"))
                {
                    JudgeSendContestantInfo();
                }
                else if(request.equals("Send Contest List"))
                {
                    JudgeSendContestInfo();
                }
                else if(request.equals("LoadContest"))
                {
                    LoadContest();
                }
                else if(request.equals("ContestDetails"))
                {
                    sendContestDetails();
                }
                else if(request.equals("ContestCreation1"))
                {
                    CreateContest();
                }
                else if(request.equals("Sendlist"))
                {
                    sendProblemChoice();
                }
                else if(request.equals("Submit"))
                {
                    String language=(String)ois.readObject();
                    String problemname=(String)ois.readObject();
                    System.out.println(language+" "+problemname);
                    CreateFolder(language,problemname);
                }
                else if(request.startsWith("View"))
                {
                    sendProblem(request.substring(4));
                }
                else if(request.equals("Verdict"))
                {
                    SendVerdict();
                }
                else if(request.equals("Standings"))
                {   
                    SendStandings();
                }
            
            }
        
        }catch(Exception e)
        {
            System.out.println("Error");
        }
       
    }

}

public class Server_oj {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        ArrayList<Problems> problems=new ArrayList<>();
        String path1="G:\\Java_Projects\\Server_oj\\Theatre Square.txt";
        String path2="G:\\Java_Projects\\Server_oj\\Watermelon.txt";
        String path3="G:\\Java_Projects\\Server_oj\\Way Too Long Words.txt";
        String name1="Theatre Square";
        String name2="Watermelon";
        String name3="Way Too Long Words";
        ArrayList<Contest>contestList=new ArrayList<>();
        
        ArrayList<ProgramThread> a=new ArrayList<>();
        ArrayList<Standings> standings=new ArrayList<>();
        ServerSocket server=new ServerSocket(12345);
        Socket socket=null;
        int count=0;
        try{
            while(true)
            {   
                socket=server.accept();
                a.add(new ProgramThread(socket,++count,contestList));
                a.get(count-1).start();
            }
        
        }catch(Exception e)
        {
            System.out.println("Error");
        }
        
    }
    
}

