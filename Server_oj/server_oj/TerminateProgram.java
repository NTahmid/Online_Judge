/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_oj;


public class TerminateProgram implements Runnable{
    private Process p;
    private long start;
    private int timeLimit;
    private boolean finish;
    public TerminateProgram(Process p,int timeLimit)
    {
        this.p=p;
        this.timeLimit=timeLimit;
        finish=false;
    }
    public boolean getState()
    {
        return finish;
    }
    @Override
    public void run() {
        start=System.currentTimeMillis();
        while(true)
        {
            if(System.currentTimeMillis()-start>timeLimit*1000)
            {
                p.destroyForcibly();
                try {
                    p.waitFor();
                    break;
                } catch (Exception ex) {
                    System.out.println("Exception in program "+ex);
                    finish=true;
                    return;
                }
                
            }
        }
        finish=true;
        
    }
}
