/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/**
 *
 * @author 09073553
 */
public class Pilha 
{
    private String []     pilha;
    private int         topo;
    
    public Pilha()
    {
        pilha = new String[100];
        topo = 0;
    }
    
    public void push(String p)
    { 
        pilha[topo] = p;
        topo++;
    }
    
    public String pop()
    {        
        topo--;
        return pilha[topo];
    }
    
    public String peek()
    {
        if(topo > 0)
        return pilha[topo-1];
        return "";
    }
    
    boolean isEmpty() 
    {
        if (topo == 0)
            return true;
        else return false;
    }
}
