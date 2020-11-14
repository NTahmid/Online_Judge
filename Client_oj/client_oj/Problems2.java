
package client_oj;


public class Problems2 {
    private String name;
    private int problemno;
    
    public Problems2()
    {
        name="";
        problemno=0;
    }
    
    public Problems2(String name,int problemno)
    {
        this.name=name;
        this.problemno=problemno;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProblemno() {
        return problemno;
    }

    public void setProblemno(int problemno) {
        this.problemno = problemno;
    }   
}
