/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_oj;

import java.util.ArrayList;
import myuserclasses.*;
/**
 *
 * @author USER
 */
public class JudgeProgram implements Runnable{
    
    private ArrayList<Contest>contestList;
    
    JudgeProgram(ArrayList<Contest>contestList)
    {
        this.contestList=contestList;
    }
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
